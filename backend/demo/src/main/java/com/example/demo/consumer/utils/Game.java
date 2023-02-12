package com.example.demo.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.consumer.GuessServer;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/10
 */
public class Game extends Thread{

    private  String a;
    private  String b;
    private String aChoice;
    private String bChoice;
    private String loser = "";
    private Integer aScore = 0;
    private Integer bScore = 0;
    private String status = "stop";
    private ReentrantLock lock = new ReentrantLock();

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public void setaChoice(String aChoice) {
        lock.lock();
        try{
            this.aChoice = aChoice;
        } finally {
            lock.unlock();
        }

    }

    public void setbChoice(String bChoice) {
        lock.lock();
        try{
            this.bChoice = bChoice;
        }finally {
            lock.unlock();
        }

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Game(String a, String b) {
        this.a = a;
        this.b = b;
    }

//    双方是否都选择了
    public Boolean judge(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        10s
        for(int i=0;i<100;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(aChoice!=null&&bChoice!=null){
                return true;
            }
        }
        return false;
    }

//    比赛结束
    public void sendResult(){
        JSONObject js = new JSONObject();
        js.put("status","end");
        GuessServer.sendScoreByName(b,js);
        GuessServer.sendScoreByName(a,js);
    }

    public String judgeLoser(){
        if(aChoice==null&&bChoice==null){
            return "all";
        }else if(aChoice==null){
            return a;
        }else if(bChoice==null){
            return b;
        }
        if(aChoice.equals(bChoice))return "all";
        if(aChoice.equals("j")){
            if(bChoice.equals("b"))return b;
            return a;
        }
        if(aChoice.equals("b")){
            if(bChoice.equals("j"))return a;
            return b;
        }
        if(aChoice.equals("s")){
            if(bChoice.equals("j"))return  b;
            return a;
        }
        return "all";
    }

    public void sendScore(){
        loser = judgeLoser();
        lock.lock();
        try{
            if("all".equals(loser)){
                System.out.println("平局");
                aScore++;
                bScore++;
            }else if(a.equals(loser)){
                bScore++;
            }else {
                aScore++;
            }
        }finally {
            lock.unlock();
        }
        JSONObject resp = new JSONObject();
        resp.put("status","matching");
        if(loser.equals(a)){
            resp.put("my_score",aScore);
            resp.put("ta_score",bScore);
            resp.put("ta_choice",bChoice);
            resp.put("my_choice",aChoice);
            GuessServer.sendScoreByName(a,resp);
            resp.put("my_score",bScore);
            resp.put("ta_score",aScore);
            resp.put("ta_choice",aChoice);
            resp.put("my_choice",bChoice);
            GuessServer.sendScoreByName(b,resp);
        } else if(loser.equals(b)){
            resp.put("my_score",bScore);
            resp.put("ta_score",aScore);
            resp.put("ta_choice",aChoice);
            resp.put("my_choice",bChoice);
            GuessServer.sendScoreByName(b,resp);
            resp.put("my_score",aScore);
            resp.put("ta_score",bScore);
            resp.put("ta_choice",bChoice);
            resp.put("my_choice",aChoice);
            GuessServer.sendScoreByName(a,resp);
        }else{
            resp.put("my_score",bScore);
            resp.put("ta_score",aScore);
            resp.put("ta_choice",aChoice);
            resp.put("my_choice",bChoice);
            GuessServer.sendScoreByName(b,resp);
            resp.put("my_score",aScore);
            resp.put("ta_score",bScore);
            resp.put("ta_choice",bChoice);
            resp.put("my_choice",aChoice);
            GuessServer.sendScoreByName(a,resp);
        }
        aChoice = null;bChoice = null;
    }

    @Override
    public void run() {
        status = "start";
        for (int i=0;i<20;i++){
            if(status.equals("stop")){
//                有人异常了
                sendResult();
                break;
            }
            if(aScore>=3||bScore>=3){
                sendResult();//比赛结束
                break;
            }else{
                if (judge()){//双方正常比赛
                    System.out.println("双方正常比赛");
                    sendScore();
                    continue;
                }else{
                    /**
                     * 有一方没有没有做出选择 直接结束游戏不惯着
                      */
                    sendScore();
                    sendResult();
                    break;
                }
            }
        }
    }
}
