package com.example.demo.consumer;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.consumer.utils.Game;
import com.example.demo.utils.IdandName;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/9
 */
@Component
@ServerEndpoint("/GuessWebsocket/{name}/{token}")  // 注意不要以'/'结尾
public class GuessServer {

    private String name;
    private Game game = null;
    private Session session = null;
    //  线程安全的哈希表
    final public static ConcurrentHashMap<String, GuessServer> user = new ConcurrentHashMap<String, GuessServer>();
    final private static CopyOnWriteArraySet<String> matchpool = new CopyOnWriteArraySet<>();
    final private  static ConcurrentHashMap<String,String> nameMap = new ConcurrentHashMap<>();
//    对手的game
    final private static ConcurrentHashMap<String,Game> userGame = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name,
                       @PathParam("token") String token) throws Exception {
        if(!new IdandName().user(token,name)){
            this.onClose();
            return ;
        }
        this.session = session;
        this.name = name;
        user.put(name,this);
    }

    @OnClose
    public void onClose() {
        if(name!=null) {
            user.remove(this.name);
            matchpool.remove(this.name);
        }
    }


    @OnMessage
    public void onMessage(String message) {
        JSONObject data = JSONObject.parseObject(message);
        String f = data.getString("event");
        if("start".equals(f)){
            startMatching();
        }else if("stop".equals(f)){
            stopMatching();
        }else if("match".equals(f)){
            startGame(data.getString("match"));
        }else if("error".equals(f)){
//            实在抱歉我异常了 对手--name
//            JSONObject res = new JSONObject();
//            res.put("status","error");
//            sendScoreByName(data.getString("name"),res);
        }
    }

    public static void sendScoreByName(String name, JSONObject resp){
        Session session1 = user.get(name).session;
        synchronized (session1){
            try {
                session1.getBasicRemote().sendText(String.valueOf(resp));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  void sendMessage(String name, String message) {
//
        JSONObject resp = new JSONObject();
        Session newSession = null;
        resp.put("status","duishou");
        resp.put("ta_name",message);
        newSession = user.get(name).session;
        synchronized (newSession){
            try{
                newSession.getBasicRemote().sendText(String.valueOf(resp));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startMatching(){
        System.out.println("start...");
        System.out.println(this.name);
        matchpool.add(this.name);
        while(matchpool.size()>=2){
            Iterator<String> i = matchpool.iterator();
            String a = i.next(),b = i.next();
            matchpool.remove(a);
            matchpool.remove(b);
            Game game = new Game(a,b);
            user.get(a).game = game;
            user.get(b).game = game;
            nameMap.put(a,b);
            nameMap.put(b,a);
            userGame.put(a,game);
            userGame.put(b,game);
            sendMessage(a,b);
            sendMessage(b,a);
            game.start();
        }
    }

    public void startGame(String choice){
        System.out.println(choice + "????????????????????????????????????????");
        try{
            if(game.getA().equals(this.name)){
                game.setaChoice(choice);
            }else if(game.getB().equals(this.name)){
                game.setbChoice(choice);
            }
        }catch(NullPointerException e){

            System.out.println("异常了");
//            只有名字了
            System.out.println(this.name);
            System.out.println(this.game);
            userGame.get(nameMap.get(this.name)).setStatus("stop");
//            user.get(nameMap.get(this.name)).game.setStatus("stop");
        }
    }

    public void stopMatching(){
        System.out.println("stop...");
        matchpool.remove(this.name);
    }


    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
