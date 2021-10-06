package com.sw.capstone.gajoku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    private ArrayList<Menu> mDataSet;

    private Context mContext;
    private OnMenuSelectedListener mSelectedListener;

    public MenuRecyclerAdapter(ArrayList<Menu> dataSet, Context context) {
        mContext = context;
        mDataSet = dataSet;
    }

    public interface OnMenuSelectedListener {
        void onMenuSelected(int position);
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener listener){
        mSelectedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_menu, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if(viewHolder == null){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
            viewHolder = new MenuRecyclerAdapter.ViewHolder(view);
        }
        Log.i("RecyclerView", "Element " + position + " set.");
        viewHolder.getNameTextView().setText(mDataSet.get(position).name);
        viewHolder.getPriceTextView().setText(mDataSet.get(position).price + " 원");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView menuName;
        private final TextView menuPrice;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mSelectedListener.onMenuSelected(getAdapterPosition());
                }
            });

            menuName = (TextView) v.findViewById(R.id.textView_menu_name);
            menuPrice = (TextView) v.findViewById(R.id.textView_menu_price);
        }

        public TextView getNameTextView() {
            return menuName;
        }
        public TextView getPriceTextView() {
            return menuPrice;
        }
    }
}
