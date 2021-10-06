package com.sw.capstone.gajoku;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder> {

    private List<Restaurant> mDataSet;
    private OnRestaurantSelectedListener mSelectedListener;

    public interface OnRestaurantSelectedListener {
        void onRestaurantSelected(int position);
    }

    public void setOnRestaurantSelectedListener(OnRestaurantSelectedListener listener){
        mSelectedListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView distance;
        private final TextView least;
        private final RelativeLayout layout;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedListener.onRestaurantSelected(getAdapterPosition());
                }
            });

            layout = (RelativeLayout) v.findViewById(R.id.item_restaurant);
            name = (TextView) v.findViewById(R.id.restaurant_recyclerView_name);
            distance = (TextView) v.findViewById(R.id.restaurant_recyclerView_address);
            least = (TextView) v.findViewById(R.id.restaurant_recyclerView_least);
        }

        public TextView getNameTextView() {
            return name;
        }

        public TextView getDistanceTextView() {
            return distance;
        }

        public TextView getLeastTextView() {
            return least;
        }
    }

    public RestaurantRecyclerAdapter(List<Restaurant> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_restaurant, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.i("RecyclerView", "Element " + position + " set.");
        if(position % 2 == 1) {
            viewHolder.layout.setBackgroundColor(Color.rgb(0xDE, 0xFD, 0xF8));
        }
        viewHolder.getNameTextView().setText(mDataSet.get(position).name);
        viewHolder.getDistanceTextView().setText("거리 : " + mDataSet.get(position).distance + "km");
        viewHolder.getLeastTextView().setText("최소 금액 : " + mDataSet.get(position).least + " 원");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
