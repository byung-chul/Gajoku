package com.sw.capstone.gajoku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BucketListRecyclerAdapter extends RecyclerView.Adapter<BucketListRecyclerAdapter.ViewHolder> {

    private ArrayList<Bucket> mDataSet;
    private Context mContext;
    private OnBucketIconClickedListener mIconClickedListener;

    public BucketListRecyclerAdapter(ArrayList<Bucket> dataSet, Context context) {
        mContext = context;
        mDataSet = dataSet;
    }

    public interface OnBucketIconClickedListener {
        void onBucketDeleted(int position);
        void onQuantityChanged();
    }

    public void setBucketIconClickedListener(OnBucketIconClickedListener listener){
        mIconClickedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_bucket_restaurant, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if(viewHolder == null){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_bucket_restaurant, null);
            viewHolder = new BucketListRecyclerAdapter.ViewHolder(view);
        }

        viewHolder.menuName.setText(mDataSet.get(position).menu);
        viewHolder.price.setText(mDataSet.get(position).cost + " 원");
        viewHolder.quantity.setText(Integer.toString(mDataSet.get(position).quantity));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView menuName;
        public final TextView price;
        public final TextView quantity;
        public final ImageView deleteBtn;
        public final ImageView q_up;
        public final ImageView q_down;

        public ViewHolder(View v) {
            super(v);

            menuName = (TextView) v.findViewById(R.id.textView_bucket_menuName);
            price = (TextView) v.findViewById(R.id.textView_bucket_price);
            quantity = (TextView) v.findViewById(R.id.textView_bucket_quantity);
            deleteBtn = (ImageView) v.findViewById(R.id.imageView_bucket_cancelBucket);
            q_up = (ImageView) v.findViewById(R.id.imageView_bucket_quantityUp);
            q_down = (ImageView) v.findViewById(R.id.imageView_bucket_quantityDown);

            deleteBtn.setOnClickListener(this);
            q_up.setOnClickListener(this);
            q_down.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            StoredBucket storedBucket = new StoredBucket(v.getContext());
            Bucket selectedBucket = mDataSet.get(getAdapterPosition());

            switch(v.getId()) {
                case R.id.imageView_bucket_cancelBucket:
                    mIconClickedListener.onBucketDeleted(getAdapterPosition());
                    break;

                case R.id.imageView_bucket_quantityUp:
                    storedBucket.setQuantity(selectedBucket, selectedBucket.quantity + 1);
                    selectedBucket.quantity += 1;
                    quantity.setText(Integer.toString(selectedBucket.quantity));
                    mIconClickedListener.onQuantityChanged();
                    break;

                case R.id.imageView_bucket_quantityDown:
                    if(selectedBucket.quantity > 1) {
                        storedBucket.setQuantity(selectedBucket, selectedBucket.quantity - 1);
                        selectedBucket.quantity -= 1;
                        quantity.setText(Integer.toString(selectedBucket.quantity));
                        mIconClickedListener.onQuantityChanged();
                    }
                    break;
            }
        }
    }
}
