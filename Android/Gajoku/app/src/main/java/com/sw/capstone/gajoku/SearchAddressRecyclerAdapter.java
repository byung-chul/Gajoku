package com.sw.capstone.gajoku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchAddressRecyclerAdapter extends RecyclerView.Adapter<SearchAddressRecyclerAdapter.ViewHolder> {

    private List<Address> mDataSet;

    private Context mContext;
    private OnAddressSelectedListener mSelectedListener;

    public SearchAddressRecyclerAdapter(List<Address> dataSet, Context context) {
        mContext = context;
        mDataSet = dataSet;
    }

    public interface OnAddressSelectedListener {
        void onAddressSelected(int position);
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener){
        mSelectedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_juso, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if(viewHolder == null){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_juso, null);
            viewHolder = new SearchAddressRecyclerAdapter.ViewHolder(view);
        }
        Log.i("RecyclerView", "Element " + position + " set.");
        viewHolder.getBuildingNameTextView().setText(mDataSet.get(position).buildingName);
        viewHolder.getJibunAddressTextView().setText(mDataSet.get(position).jibunAddress);
        viewHolder.getRoadAddressTextView().setText(mDataSet.get(position).roadAddress);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView buildingName;
        private final TextView jibunAddress;
        private final TextView roadAddress;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mSelectedListener.onAddressSelected(getAdapterPosition());
                }
            });

            buildingName = (TextView) v.findViewById(R.id.textView_addressName);
            jibunAddress = (TextView) v.findViewById(R.id.textView_jibunAddress);
            roadAddress = (TextView) v.findViewById(R.id.textView_roadAddress);
        }

        public TextView getBuildingNameTextView() {
            return buildingName;
        }
        public TextView getJibunAddressTextView() {
            return jibunAddress;
        }
        public TextView getRoadAddressTextView() {
            return roadAddress;
        }
    }
}
