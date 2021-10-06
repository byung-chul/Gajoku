package com.sw.capstone.gajoku;

import java.util.ArrayList;

public class Order {
    public String c_id;
    public int r_id;
    public int o_cost = 0;
    public int o_surplus = 0;
    public String c_address;
    public ArrayList<Bucket> o_content;
    public int time;

    Order(ArrayList<Bucket> bucketList, int r_id, String c_id, String address, int least, int time){
        this.c_id = c_id;
        this.r_id = r_id;
        o_content = bucketList;
        c_address = address;

        for(Bucket bucket: bucketList) {
            o_cost += bucket.cost * bucket.quantity;
        }

        o_surplus = least - o_cost;

        this.time = time;
    }
}
