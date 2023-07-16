package com.cake.cakeadmin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cake.cakeadmin.Models.DisplayAllProductsModel;
import com.cake.cakeadmin.Models.OrderDataModel;
import com.cake.cakeadmin.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {
    int totalQty=1;
    int totalPrice=0;
    ImageView detailedImg,addItem,RemoveItem;
    TextView price,rating,description,quantity,CustomerName,CustomerMobile,CustomerEmail,CustomerAddress,orderState,TotalAmount;
    Button Accept,Reject,Delivered;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    OrderDataModel viewAllModel = null;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        CustomerName=findViewById(R.id.CustomerName);
        CustomerMobile=findViewById(R.id.CustomerMobile);
        CustomerEmail=findViewById(R.id.CustomerEmail);
        CustomerAddress=findViewById(R.id.CustomerAddress);
        orderState=findViewById(R.id.orderState);
        TotalAmount=findViewById(R.id.TotalAmount);

        //buttons

        Accept=findViewById(R.id.Accept);
        Reject=findViewById(R.id.Reject);
        Delivered=findViewById(R.id.Delivered);



        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof OrderDataModel) {
            viewAllModel = (OrderDataModel) object;
        }
        detailedImg = findViewById(R.id.detailed_img);

        price = findViewById(R.id.detailed_price);
        quantity = findViewById(R.id.detailed_rating);
       // quantity = findViewById(R.id.quantity);

        userModel userModel=new userModel();
        if (viewAllModel != null) {
            Toast.makeText(this, viewAllModel.getProductPrice(), Toast.LENGTH_SHORT).show();
            Glide.with(getApplicationContext()).load(viewAllModel.getProductImg()).into(detailedImg);
            price.setText(viewAllModel.getProductPrice());
            toolbar.setTitle(viewAllModel.getOrderid());
            quantity.setText("Qty:"+viewAllModel.getProductQty());
            //customer details

            CustomerName.setText(viewAllModel.getUserName());
            CustomerMobile.setText(viewAllModel.getUserMobile());
            CustomerEmail.setText(viewAllModel.getUserEmail());
            CustomerAddress.setText(viewAllModel.getUserAddress());
            orderState.setText(viewAllModel.getOrderStatus());
            TotalAmount.setText(viewAllModel.getTotalPrice().toString());




        }

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("OrderStatus", "Order in Process");
                data.put("OrderStatusAdmin", "Accepted");

                firestore.collection("MyOrder").document(viewAllModel.getOrderid())
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                FcmNotificationsSender notificationsSender=new FcmNotificationsSender(viewAllModel.getToken().toString(),
                                        "Your Order"+viewAllModel.getProductName().toString()+"in Process",
                                        viewAllModel.getOrderid(),getApplicationContext(),OrderDetailsActivity.this);
                                notificationsSender.SendNotifications();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });

        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();

                data.put("OrderStatus", "Sorry Unfortunetly We Are Not Delivered This Order");
                data.put("OrderStatusAdmin", "Rejected");



                firestore.collection("MyOrder").document(viewAllModel.getOrderid())
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");

                                FcmNotificationsSender notificationsSender=new FcmNotificationsSender(viewAllModel.getToken().toString(),
                                        "Your Order"+viewAllModel.getProductName().toString()+"Sorry Unfortunetly We Are Not Delivered This Order",
                                        viewAllModel.getOrderid(),getApplicationContext(),OrderDetailsActivity.this);
                                notificationsSender.SendNotifications();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });


            }
        });

        Delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();
                data.put("OrderStatus", "Your Order Has been Delivered"+viewAllModel.getOrderid());
                data.put("OrderStatusAdmin", "Delivered");

                firestore.collection("MyOrder").document(viewAllModel.getOrderid())
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");

                                FcmNotificationsSender notificationsSender=new FcmNotificationsSender(viewAllModel.getToken().toString(),
                                        "Your Order"+viewAllModel.getProductName().toString()+"Has been Delivered",
                                        viewAllModel.getOrderid(),getApplicationContext(),OrderDetailsActivity.this);
                                notificationsSender.SendNotifications();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });


            }
        });


    }
}