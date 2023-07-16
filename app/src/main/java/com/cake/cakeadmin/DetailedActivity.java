package com.cake.cakeadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cake.cakeadmin.Models.DisplayAllProductsModel;
import com.cake.cakeadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    int totalQty=1;
    int totalPrice=0;
    ImageView detailedImg,addItem,RemoveItem;
    TextView   price,rating,description,quantity;
    Button addTocart;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    DisplayAllProductsModel viewAllModel = null;
    FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof DisplayAllProductsModel) {
            viewAllModel = (DisplayAllProductsModel) object;
        }
        detailedImg = findViewById(R.id.detailed_img);

        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_desc);



        if (viewAllModel != null) {
            Toast.makeText(this, viewAllModel.getPrice(), Toast.LENGTH_SHORT).show();
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            price.setText(viewAllModel.getPrice());
            description.setText(viewAllModel.getDescription());
            toolbar.setTitle(viewAllModel.getName());

            totalPrice = Integer.parseInt(viewAllModel.getPrice()) * totalQty;

        }
        addTocart = findViewById(R.id.add_to_cart);


    }


}