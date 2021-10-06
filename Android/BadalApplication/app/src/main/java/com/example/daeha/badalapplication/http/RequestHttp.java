package com.example.daeha.badalapplication.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHttp {

    private String apiUrl;

    public String requestGet(String apiUrl){
        String result = "";
        this.apiUrl = apiUrl;

        try {
            result = new Request().execute("GET").get();
        } catch (Exception e){
            Log.i("HTTP", "Failed to execute HTTP request");
        }
        return result;
    }

    class Request extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            final int TIMEOUT_VALUE = 5000; // 5 seconds
            HttpURLConnection connection = null;
            String response = "";

            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(params[0]);

                connection.setReadTimeout(TIMEOUT_VALUE);
                connection.setConnectTimeout(TIMEOUT_VALUE);

                //connection.setRequestProperty("Accept", "application/json");
                //connection.setRequestProperty("Content-type", "application/json");

                InputStream inputStream = url.openStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);

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
}
