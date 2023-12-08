package com.example.user.controller.consumer;

import com.alibaba.fastjson.JSONObject;
import com.example.common.utils.redisUtil;
import com.example.user.domain.Chat;
import com.example.user.mapper.ChatMapper;
import com.example.user.utils.IdandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.example.common.constants.radis.redisConstants.REDIS_CHAT;
import static com.example.common.constants.radis.redisConstants.REDIS_LIMIT;


/**
 * @author 睡醒继续做梦
 * @date 2023/1/8
 */
@Component
@ServerEndpoint("/websocket/{id}/{name}/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    private String name;
    private Integer id;

    public static ChatMapper chatMapper;

    public static redisUtil redisUtil;

    @Autowired
    public void setChatMapper(ChatMapper chatMapper){
        WebSocketServer.chatMapper = chatMapper;
    }

    @Autowired
    public  void setRedisUtil(redisUtil redisUtil) {
        WebSocketServer.redisUtil = redisUtil;
    }

    //  一个连接就是一个session
    private Session session = null;
    //  线程安全的哈希表
    private static ConcurrentHashMap<Integer,WebSocketServer> user = new ConcurrentHashMap<>();

    //群聊
    private static ConcurrentHashMap<Integer, String> groupChat = new ConcurrentHashMap<>();

    //群聊中的用户
    private static ConcurrentSkipListSet<Integer> groupChatUser = new ConcurrentSkipListSet<>();

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("id") String id,
                       @PathParam("name") String name,
                       @PathParam("token") String token) throws Exception {
        if(!new IdandName().user(token,name)){
            this.onClose();
            System.out.println("给你关掉");
            return ;
        }
        this.session = session;
        Integer userId = Integer.parseInt(id);
        this.id = userId;
        this.name = name;
        user.put(userId,this);
        groupChatUser.add(userId);
        System.out.println(user.size() + "??????????????????????");
        sendAllMessage();
    }

    @OnClose
    public void onClose() {
        if(id!=null) {
            user.remove(this.id);
            groupChatUser.remove(this.id);
        }
        sendAllMessage();
    }

    @OnMessage
    public void onMessage(String message, Session session){

        Boolean hget = (Boolean) redisUtil.hget(REDIS_LIMIT, REDIS_CHAT);
        if(!hget){
            this.sendMessageSo(this.id, "功能已停用");
            return ;
        }

        System.out.println("message = " + message);
        if(message.equals("all")){
            System.out.println(message);
            sendAllMessage();
            System.out.println(message);
            return  ;
        }
        JSONObject data = JSONObject.parseObject(message);

        //TODO       groupchat
        if(data.getString("about").equals("group")){
            sendMessageToGroup(data.getString("message"),name);
            System.out.println("群聊");
            return ;
        }

//        广播
        if(data.getString("about").equals("all")&&"admin".equals(this.name)){
            sendAllMessage(data.getString("msg"));
            return ;
        }
        sendMessageByUser(data.getInteger("id"),data.getString("message"));
        // 从Client接收消息
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    /**
     * 发送给指定id
     * @param userId
     * @param message
     */
    public  void sendMessageByUser(Integer userId, String message) {
//
        JSONObject resp = new JSONObject();
        Session newSession = null;
//        用户已下线
        if(user.get(userId)==null){
            System.out.println("用户不存在");
            userId = this.id;
            message = "目标用户已下线";
            resp.put("author","oneself");
            resp.put("message",message);
            newSession = this.session;
            synchronized (newSession){
                try{
                    newSession.getBasicRemote().sendText(String.valueOf(resp));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
//            不在线就不要发送信息了
            return ;
        }
        String from = this.name;
        String to = user.get(userId).name;
        resp.put("author",this.name);
        resp.put("message",message);
        resp.put("to",this.id);
        newSession = user.get(userId).session;
        synchronized (newSession){
            try{
                newSession.getBasicRemote().sendText(String.valueOf(resp));
                System.out.println(from  + "to ..." +  to +"    " + message);
//                加入数据库
                chatMapper.insert(new Chat(5,from,to,message,new Date()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageSo(Integer userId, String message){
        JSONObject resp = new JSONObject();
        Session newSession = null;
        resp.put("author","stop");
        resp.put("message",message);
        newSession = user.get(userId).session;
        synchronized (newSession){
            try{
                newSession.getBasicRemote().sendText(String.valueOf(resp));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 把在线人数发给连接的人
     */
    public static void sendAllMessage() {
        JSONObject resp = new JSONObject();
        JSONObject res = new JSONObject();
        resp.put("author","All");
        user.forEach((key,value)->{
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(value.id));
            map.put("name",value.name);
            map.put("status","false");
            res.put(String.valueOf(value.id),map);
        });
        resp.put("message",res);
        System.out.println(user.size() + "///////////////////////////////");
//        System.out.println(this.name);
        System.out.println("到这了吗");
        user.forEach((key,value)->{
            System.out.println(value.session + "..................");
            synchronized (value.session){
                try{
                    value.session.getBasicRemote().sendText(String.valueOf(resp));
                    System.out.println("发送信息了");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    /**
     * 群聊消息发送
     * @param message
     */
    public static void sendMessageToGroup(String message, String name){
        JSONObject resp = new JSONObject();
        resp.put("author","group");
        resp.put("who", name);
        resp.put("message",message);
        user.forEach((key,value)->{
            synchronized (value.session){
                try{
                    value.session.getBasicRemote().sendText(String.valueOf(resp));
                    System.out.println("发送信息了");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void sendAllMessage(String message) {
        JSONObject resp = new JSONObject();
        resp.put("author","about");
        resp.put("message",message);
        System.out.println(user.size() + "///////////////////////////////");
//        System.out.println(this.name);
        System.out.println("到这了吗");
        user.forEach((key,value)->{
            System.out.println(value.session + "..................");
            synchronized (value.session){
                try{
                    value.session.getBasicRemote().sendText(String.valueOf(resp));
                    System.out.println("发送信息了");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}

