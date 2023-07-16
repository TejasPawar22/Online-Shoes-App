package com.cake.cakeadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cake.cakeadmin.DetailedActivity;
import com.cake.cakeadmin.Models.DisplayAllProductsModel;
import com.cake.cakeadmin.Models.OrderDataModel;
import com.cake.cakeadmin.OrderDetailsActivity;
import com.cake.cakeadmin.R;

import java.util.List;



public class OrderDataAdapter extends RecyclerView.Adapter<OrderDataAdapter.ViewHolder> {

    Context context;
    List<OrderDataModel> list;

    public OrderDataAdapter(Context context, List<OrderDataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_data_list,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getProductImg()).into(holder.imageView);
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getProductPrice());
        holder.rating.setText(list.get(position).getProductQty());
        holder.description.setText(list.get(position).getOrderid());




        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,rating,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);

            description = itemView.findViewById(R.id.view_desc);

            rating = itemView.findViewById(R.id.view_rating);
            price = itemView.findViewById(R.id.view_price);
        }
    }
}
