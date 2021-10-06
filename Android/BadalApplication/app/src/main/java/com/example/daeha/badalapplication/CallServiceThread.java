package com.example.daeha.badalapplication;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.daeha.badalapplication.http.RequestHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CallServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;
    String id;

    public  CallServiceThread(Handler handler){
        this.handler = handler;
    }

    public  void stopForever(){
        synchronized (this){
            this.isRun = false;
        }
    }

    public void run(){

        while(isRun){
            try {
                Url_Call callUrl = new Url_Call();

                RequestHttp requestHttp = new RequestHttp();
                String result = requestHttp.requestGet(callUrl.getApiUrl());
                System.out.println(result);

                Message message = new Message();
                message.obj = result;
                handler.sendMessage(message);

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
