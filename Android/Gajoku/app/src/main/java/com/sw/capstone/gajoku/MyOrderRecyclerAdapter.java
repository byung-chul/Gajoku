package com.sw.capstone.gajoku;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyOrderRecyclerAdapter extends RecyclerView.Adapter<MyOrderRecyclerAdapter.ViewHolder> {
    private List<MyOrder> mDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final RecyclerView recyclerView;
        private final Context context;
        private final TextView address;
        private final TextView timestamp;
        private final TextView totalCost;

        public ViewHolder(View v) {
            super(v);

            name = (TextView) v.findViewById(R.id.textView_myOrder_item_restaurant);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_myorder_orderContent);
            address = (TextView) v.findViewById(R.id.textView_myOrder_address);
            timestamp = (TextView) v.findViewById(R.id.textView_myOrder_timeStamp);
            totalCost = (TextView) v.findViewById(R.id.textView_myOrder_totalCost);
            context = v.getContext();
        }

        public TextView getNameTextView() {
            return name;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public Context getThisContext(){
            return context;
        }

        public TextView getAddressTextView(){
            return address;
        }

        public TextView getTimestampTextView(){
            return timestamp;
        }

        public TextView getTotalCostTextView(){
            return totalCost;
        }
    }

    public MyOrderRecyclerAdapter(List<MyOrder> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_myorder, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        MyOrder myOrder = mDataSet.get(position);

        viewHolder.getNameTextView().setText(myOrder.orderContent.get(0).restaurantName);
        viewHolder.getAddressTextView().setText(myOrder.customerAddress);
        String[] split = myOrder.timestamp.split("[\\-:T.]");
        String time =   split[1] + "월 " +
                        split[2] + "일 " +
                        split[3] + "시 " +
                        split[4] + "분 " +
                        split[5] + "초";
        viewHolder.getTimestampTextView().setText(time);
        viewHolder.getTotalCostTextView().setText(myOrder.orderCost + " 원");


        RecyclerView contentRecyclerView;
        MyOrderContentRecyclerAdapter contentRecyclerAdapter;
        RecyclerView.LayoutManager layoutManager;

        contentRecyclerView = viewHolder.getRecyclerView();
        layoutManager = new LinearLayoutManager(viewHolder.getThisContext());
        contentRecyclerView.setLayoutManager(layoutManager);

        contentRecyclerAdapter = new MyOrderContentRecyclerAdapter(mDataSet.get(position).orderContent);
        contentRecyclerView.setAdapter(contentRecyclerAdapter);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
