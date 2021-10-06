package com.sw.capstone.gajoku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StoredAddressRecyclerAdapter extends RecyclerView.Adapter<StoredAddressRecyclerAdapter.ViewHolder> {

    private List<String> mDataSet;

    private Context mContext;
    private OnStoredAddressSelectedListener mSelectedListener;

    public StoredAddressRecyclerAdapter(List<String> dataSet, Context context) {
        mContext = context;
        mDataSet = dataSet;
    }

    public interface OnStoredAddressSelectedListener {
        void onStoredAddressSelected(int position);
        void onStoredAddressDeleted(int position);
    }

    public void setOnStoredAddressSelectedListener(OnStoredAddressSelectedListener listener){
        mSelectedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_stored_juso, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if(viewHolder == null){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_stored_juso, null);
            viewHolder = new StoredAddressRecyclerAdapter.ViewHolder(view);
        }
        Log.i("RecyclerView", "Element " + position + " set.");
        viewHolder.getStoredAddressTextView().setText(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView storedAddress;
        private final ImageView deleteButton;

        public ViewHolder(View v) {
            super(v);

            storedAddress = (TextView) v.findViewById(R.id.textView_addressName_stored);
            storedAddress.setOnClickListener(this);
            deleteButton = (ImageView) v.findViewById(R.id.imageView_stored_delete);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            switch(v.getId()) {
                case R.id.imageView_stored_delete:
                    mSelectedListener.onStoredAddressDeleted(getAdapterPosition());
                    break;
                default:
                    mSelectedListener.onStoredAddressSelected(getAdapterPosition());
                    break;
            }
        }

        public TextView getStoredAddressTextView() {
            return storedAddress;
        }
    }
}
