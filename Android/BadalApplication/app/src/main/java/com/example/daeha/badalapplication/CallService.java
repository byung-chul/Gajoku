package com.example.daeha.badalapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telecom.Call;

public class CallService extends Service {
    CallServiceThread thread;
    String id;
    Intent intent = null;
    Intent intent2 = null;

    public CallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        callServiceHandler handler = new callServiceHandler();
        thread = new CallServiceThread(handler);
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
    }

    class callServiceHandler extends Handler{

        public void handleMessage(Message msg){
            String result = (String) msg.obj;
            System.out.println(result);


            if(result.equals("[]")){
                intent2 = new Intent(CallService.this, EmptyBadalActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
            else{

                intent = new Intent(CallService.this, BadalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.putExtra("result", result);

                startActivity(intent);

            }
        }
    }

}
