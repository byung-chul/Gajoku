package com.sw.capstone.gajoku;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Url_Order {
    public final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/order";

    String c_id; // User
    int r_id; // 가게 Admin
    String o_content; // JSON Stringify of Bucket Array
    int o_cost;
    int o_surplus;
    String c_address;

    Url_Order(String c_id, int r_id, ArrayList<Bucket> bucketList,
              int o_cost, int o_surplus, String c_address){
        Gson gson = new Gson();

        this.c_id = c_id;
        this.r_id = r_id;
        this.o_content = gson.toJson(bucketList);
        this.o_cost = o_cost;
        this.o_surplus = o_surplus;
        this.c_address = c_address;
    }

    public String getPostDataParameter(){
        String parameter = "";
        parameter += "c_id=" + c_id;
        parameter += "&r_id=" + r_id;
        parameter += "&o_content=" + o_content;
        parameter += "&o_cost=" + o_cost;
        parameter += "&o_surplus=" + o_surplus;
        parameter += "&c_address=" + c_address;

        return parameter;
    }
}
