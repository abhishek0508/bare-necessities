package com.se.cores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private List<Shop> dataset;
    private OnItemClickListener mListener;
    StorageReference storageReference;

    public interface OnItemClickListener{
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView textViewShopName;
        TextView textViewOpenTime;
        TextView textViewCloseTime;
        ImageView imageViewIcon;

        public ShopViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.textViewShopName = (TextView) itemView.findViewById(R.id.shopName);
            this.textViewOpenTime = (TextView) itemView.findViewById(R.id.shopOpenTime);
            this.textViewCloseTime = (TextView) itemView.findViewById(R.id.shopCloseTime);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position,v);
                        }
                    }
                }
            });
        }
    }
    public ShopAdapter(List<Shop> data) {
        this.dataset = data;
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cores-13fdb.appspot.com/child.jpg");
    }


    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop, parent, false);
        ShopViewHolder shopViewHolder = new ShopViewHolder(view,mListener);
        return shopViewHolder;
    }

    @Override
    public void onBindViewHolder(final ShopViewHolder holder, final int listPosition) {
        Shop shop = dataset.get(listPosition);
        holder.textViewCloseTime.setText(shop.getCloseTime());
        holder.textViewOpenTime.setText(shop.getOpenTime());
        holder.textViewShopName.setText(shop.getShopName());
        ImageView imageView = holder.imageViewIcon;
//        Glide.with(imageView.getContext()).load(storageReference).override(120, 120).into(imageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
