package com.sw.capstone.gajoku.http;

import android.content.Context;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.sw.capstone.gajoku.Alarming;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHttp{

    private String apiUrl;
    private String postParameter;
    private int time;
    private Context context;

    public String requestGet(String apiUrl){
        String result = "";
        this.apiUrl = apiUrl;

        try {
            result = new Request().execute("GET").get();
        } catch (Exception e){
            Log.i("HTTP", "Failed to execute HTTP request / GET");
        }
        return result;
    }

    public String delayedRequestGet(String apiUrl, int time, Context context){
        String result = "";
        this.apiUrl = apiUrl;
        this.time = time;
        this.context = context;

        try {
            Runnable r = new DelayedRequestGet();
            new Thread(r).start();
        } catch (Exception e){
            Log.i("HTTP", "Failed to execute HTTP request / GET");
        }
        return result;
    }

    public String delayedRequestPost(String apiUrl, String postParameter, int time, Context context){
        String result = "";
        this.postParameter = postParameter;
        this.apiUrl = apiUrl;
        this.time = time;
        this.context = context;

        try {
            Runnable r = new DelayedRequestPost();
            Thread t = new Thread(r);
            t.start();
        } catch (Exception e){
            Log.i("HTTP", "Failed to execute HTTP request / GET");
        }

        return result;
    }

    public String requestPost(String apiUrl, String dataParameter){
        String result = "";
        this.apiUrl = apiUrl;

        try {
            result = new Request().execute("POST", dataParameter).get();
        } catch (Exception e){
            Log.i("HTTP", "Failed to execute HTTP request / POST");
        }

        return result;
    }

    class DelayedRequestGet implements Runnable{
        @Override
        public void run(){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run(){
                    new AlarmedRequest().execute("GET");
                }
            }, time*60000);
        }
    }

    class DelayedRequestPost implements Runnable{
        @Override
        public void run(){

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                String result = "";
                public void run(){
                    try{
                        result = new AlarmedRequest().execute("POST", postParameter).get();
                        System.out.println(result);
                    } catch (Exception e){
                        Log.i("HTTP", "Failed to execute HTTP delayed request / POST");
                    }
                }
            }, time*60000);
        }
    }

    class Request extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            return myHttpRequest(params);
        }

    }

    class AlarmedRequest extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            return myHttpRequest(params);
        }

        @Override
        protected void onPostExecute(String string){
            Alarming a = new Alarming(context);
            a.Alarm();
        }
    }

    public String myHttpRequest(String... params){
        final int TIMEOUT_VALUE = 5000; // 5 seconds
        HttpURLConnection connection = null;
        String response = "";

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(params[0]);

            BufferedReader reader = null;
            if(params[0].equals("GET")) {
                connection.setReadTimeout(TIMEOUT_VALUE);
                connection.setConnectTimeout(TIMEOUT_VALUE);

                InputStream inputStream = url.openStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                reader = new BufferedReader(inputStreamReader);
                //connection.setRequestProperty("Accept", "application/json");
                //connection.setRequestProperty("Content-type", "application/json");
            } else if(params[0].equals("POST")){
                System.out.println("Request Post.");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setDefaultUseCaches(false);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params[1].getBytes());
                outputStream.flush();
                outputStream.close();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }

            StringBuilder buffer = new StringBuilder();
            String readStr;
            while ((readStr = reader.readLine()) != null) {
                buffer.append(readStr);
            }
            reader.close();
            System.out.println(buffer.toString());
            response =  buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
    }
}
