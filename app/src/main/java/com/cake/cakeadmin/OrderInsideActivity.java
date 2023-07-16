package com.cake.cakeadmin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderInsideActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseDatabase realDb;
    TextView totalPendingOrder,totalCancelOrder,OrdersDelivered,OrdersInprocess,OrdersRejected;
    CardView PenOrders,DelOrders,CanOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_inside);


        db = FirebaseFirestore.getInstance();
        realDb= FirebaseDatabase.getInstance();

        totalPendingOrder=findViewById(R.id.totalPendingOrder);
        totalCancelOrder=findViewById(R.id.totalCancelOrder);
        OrdersDelivered=findViewById(R.id.OrdersDelivered);
        PenOrders=findViewById(R.id.PenOrders);
        DelOrders=findViewById(R.id.DelOrders);
        CanOrder=findViewById(R.id.CanOrder);
        OrdersInprocess=findViewById(R.id.OrdersInprocess);
        OrdersRejected=findViewById(R.id.OrdersRejected);

        PenOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInsideActivity.this,ViewOrderDataActivity.class);
                intent.putExtra("OrderStatus","Pending");
                startActivity(intent);
            }
        });


        DelOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInsideActivity.this,ViewOrderDataActivity.class);
                intent.putExtra("OrderStatus","Delivered");
                startActivity(intent);
            }
        });
        CanOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInsideActivity.this,ViewOrderDataActivity.class);
                intent.putExtra("OrderStatus","Cancel By Customer");
                startActivity(intent);
            }
        });

        OrdersInprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInsideActivity.this,ViewOrderDataActivity.class);
                intent.putExtra("OrderStatus","Order in Process");
                startActivity(intent);
            }
        });





        //OrdersAccepted
        db.collection("MyOrder").whereEqualTo("OrderStatusAdmin","Accepted")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            OrdersInprocess.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });









        //OrdersDelivered
        db.collection("MyOrder").whereEqualTo("OrderStatusAdmin","Delivered")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            OrdersDelivered.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        //pending

        db.collection("MyOrder").whereEqualTo("OrderStatus","Pending")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            totalPendingOrder.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    //Cancel Orders
    db.collection("MyOrder").whereEqualTo("OrderStatus","Cancel By Customer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            totalCancelOrder.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        //OrdersRejected
        db.collection("MyOrder").whereEqualTo("OrderStatusAdmin","Rejected")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            OrdersRejected.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}