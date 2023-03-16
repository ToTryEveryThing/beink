package com.example.demo.consumer;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.pojo.Chat;
import com.example.demo.utils.IdandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    public void setChatMapper(ChatMapper chatMapper){
        WebSocketServer.chatMapper = chatMapper;
    }

    //  一个连接就是一个session
    private Session session = null;
    //  线程安全的哈希表
    private static ConcurrentHashMap<Integer,WebSocketServer> user = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("id") String id,
                       @PathParam("name") String name,
                       @PathParam("token") String token) throws Exception {
        if(!new IdandName().user(token,name)){
            this.onClose();
            return ;
        }
        this.session = session;
        Integer userId = Integer.parseInt(id);
        this.id = userId;
        this.name = name;
        user.put(userId,this);
        sendAllMessage();
    }

    @OnClose
    public void onClose() {
        if(id!=null) {
            user.remove(this.id);
        }
        sendAllMessage();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject data = JSONObject.parseObject(message);
        System.out.println(data);
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
        user.forEach((key,value)->{
            synchronized (value.session){
                try{
                    value.session.getBasicRemote().sendText(String.valueOf(resp));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}

