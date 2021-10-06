package com.sw.capstone.gajoku;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrderContentRecyclerAdapter extends RecyclerView.Adapter<MyOrderContentRecyclerAdapter.ViewHolder> {
    private ArrayList<Bucket> mDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView menu;
        private final TextView cost;
        private final TextView quantity;

        public ViewHolder(View v) {
            super(v);

            menu = (TextView) v.findViewById(R.id.textView_orderContent_name);
            cost = (TextView) v.findViewById(R.id.textView_orderContent_price);
            quantity = (TextView) v.findViewById(R.id.textView_orderContent_quantity);
        }

        public TextView getMenuTextView() {
            return menu;
        }

        public TextView getCostTextView() {
            return cost;
        }

        public TextView getQuantityTextView() {
            return quantity;
        }
    }

    public MyOrderContentRecyclerAdapter(ArrayList<Bucket> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_myorder_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getMenuTextView().setText(mDataSet.get(position).menu);
        viewHolder.getCostTextView().setText(mDataSet.get(position).cost + " 원");
        viewHolder.getQuantityTextView().setText(mDataSet.get(position).quantity + " 개");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
