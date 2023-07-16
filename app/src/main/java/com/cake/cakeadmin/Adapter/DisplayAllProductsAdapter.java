package com.cake.cakeadmin.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cake.cakeadmin.AddProductActivity;
import com.cake.cakeadmin.DetailedActivity;
import com.cake.cakeadmin.Models.OrderDataModel;
import com.cake.cakeadmin.R;
import com.cake.cakeadmin.Models.DisplayAllProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.io.Serializable;
import java.util.List;

public class DisplayAllProductsAdapter extends RecyclerView.Adapter<DisplayAllProductsAdapter.ViewHolder> {

    Context context;
    List<DisplayAllProductsModel> list;
    DisplayAllProductsModel displayAllProductsModel;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    public DisplayAllProductsAdapter(Context context, List<DisplayAllProductsModel> list) {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.rating.setText(list.get(position).getRating());
        holder.description.setText(list.get(position).getDescription());




        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailedActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);
            }
        });

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProductActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CollectionReference itemsRef = rootRef.collection("AllProducts");
                Query query = itemsRef.whereEqualTo("name", list.get(position).getName());
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                itemsRef.document(document.getId()).delete();
                                Toast.makeText(context,  itemsRef.document(document.getId()).toString(), Toast.LENGTH_SHORT).show();

                                }
                            list.remove(query);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
               // String docid=list.get(position).getDocumentId();
                //Toast.makeText(context, "data"+docid, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,deleteProduct,editProduct;
        TextView name,description,rating,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);

            description = itemView.findViewById(R.id.view_desc);
            deleteProduct=itemView.findViewById(R.id.deleteProduct);
            editProduct=itemView.findViewById(R.id.editProduct);
            rating = itemView.findViewById(R.id.view_rating);
            price = itemView.findViewById(R.id.view_price);
        }
    }
}
