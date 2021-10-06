package com.sw.capstone.gajoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.capstone.gajoku.http.RequestHttp;

import java.util.ArrayList;

public class BucketListActivity extends AppCompatActivity implements
    BucketListRecyclerAdapter.OnBucketIconClickedListener,
    View.OnClickListener{

    ArrayList<Bucket> bucketList;

    RecyclerView recyclerView;
    BucketListRecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    String restaurantName;
    int restaurantId, least;
    TextView totalCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);
        restaurantId = getIntent().getIntExtra("restaurantId", 0);
        least = getIntent().getIntExtra("restaurantLeast", 0);
        restaurantName = getIntent().getStringExtra("restaurantName");

        TextView nameTitle = findViewById(R.id.textView_bucketList_restaurantTitle);
        nameTitle.setText(restaurantName);

        TextView orderButton = findViewById(R.id.textView_bucket_order);
        orderButton.setOnClickListener(this);

        StoredBucket storedBucket = new StoredBucket(this);
        bucketList = storedBucket.getStoredBucketList();

        totalCostTextView = findViewById(R.id.textView_bucket_totalPrice);
        int total = totalCost();
        totalCostTextView.setText("합계: " + total + " 원");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_bucket);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setRecyclerViewAdapter();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.textView_bucket_order:
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra("restaurantName", restaurantName);
                intent.putExtra("restaurantLeast", least);
                intent.putExtra("Total", totalCost());
                startActivityForResult(intent, 1);

                /*

                */
                break;
        }
    }

    @Override
    public void onBucketDeleted(int position){
        bucketList.remove(position);

        StoredBucket storedBucket = new StoredBucket(this);
        storedBucket.resetBucket(bucketList);

        onQuantityChanged();
        setRecyclerViewAdapter();
    }

    @Override
    public void onQuantityChanged(){
        int total = totalCost();
        totalCostTextView.setText("합계: " + total + " 원");
    }

    public void setRecyclerViewAdapter(){
        recyclerAdapter = new BucketListRecyclerAdapter(bucketList, this);
        recyclerAdapter.setBucketIconClickedListener(BucketListActivity.this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public int totalCost(){
        int total = 0;
        for(Bucket bucket : bucketList){
            total += bucket.cost * bucket.quantity;
        }

        return total;
    }

    public void onActivityResult(int requestCode, int resultcode, Intent data){
        if(requestCode == 1){
            if(resultcode == RESULT_OK){
                int time = data.getIntExtra("Time", 0);
                StoredUserSession storedUserSession = new StoredUserSession(this);
                String c_id = storedUserSession.getUserSession();

                StoredAddress storedAddress = new StoredAddress();
                String address = storedAddress.getCurrentAddress(this);

                Order order = new Order(bucketList, restaurantId, c_id, address, least, time);

                Url_Order url_order = new Url_Order(
                        order.c_id, restaurantId, bucketList,
                        order.o_cost, order.o_surplus, order.c_address);

                RequestHttp requestHttp = new RequestHttp();
                String result = requestHttp.requestPost(url_order.url, url_order.getPostDataParameter());
                //requestHttp.delayedRequestPost(url_order.url, url_order.getPostDataParameter(), time, this.getApplicationContext());

                System.out.println(result);

                Toast.makeText(this, "주문이 완료되었습니다!\n주문 내역을 통해 내 주문을 확인할 수 있습니다.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}
