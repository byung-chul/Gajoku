package com.sw.capstone.gajoku;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Url_DeleteOrder {
    public final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/order";

    int o_id;

    Url_DeleteOrder(int o_id){
        this.o_id = o_id;
    }

    public String getPostDataParameter(){
        String parameter = "";
        parameter += "o_id=" + o_id;

        return parameter;
    }
}
