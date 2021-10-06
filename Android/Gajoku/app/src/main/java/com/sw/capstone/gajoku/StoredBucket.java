package com.sw.capstone.gajoku;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class StoredBucket {

    Context context;

    StoredBucket(Context context){
        this.context = context;
    }

    public ArrayList<Bucket> getStoredBucketList(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Bucket", MODE_PRIVATE);
        String bucketList_json = sharedPreferences.getString("List", null);

        ArrayList<Bucket> bucketList;
        Gson gson = new Gson();

        if(bucketList_json != null){
            Type type = new TypeToken<ArrayList<Bucket>>(){}.getType();
            bucketList = gson.fromJson(bucketList_json, type);
        } else {
            bucketList = new ArrayList<>();
        }

        return bucketList;
    }

    public void storeBucket(Bucket bucket){
        ArrayList<Bucket> bucketList = getStoredBucketList();

        for(Bucket b: bucketList){
            if(b.menu.equals(bucket.menu)){
                b.quantity++;
                storeBucketList(bucketList);
                return;
            }
        }

        bucketList.add(bucket);
        storeBucketList(bucketList);
    }

    public void resetBucket(ArrayList<Bucket> bucketList){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Bucket", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        storeBucketList(bucketList);
    }

    public void setQuantity(Bucket bucket, int quantity){
        ArrayList<Bucket> bucketList = getStoredBucketList();

        if(quantity < 1){
            quantity = 1;
        }

        for(Bucket b: bucketList){
            if(b.menu.equals(bucket.menu)){
                b.quantity = quantity;
                storeBucketList(bucketList);
                return;
            }
        }

        bucketList.add(bucket);
        storeBucketList(bucketList);
    }

    private void storeBucketList(ArrayList<Bucket> bucketList){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Bucket", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String bucketList_json = gson.toJson(bucketList);

        editor.putString("List", bucketList_json);
        editor.apply();
    }
}
