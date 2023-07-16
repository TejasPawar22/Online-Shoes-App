package com.cake.cakeadmin.ui.home;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cake.cakeadmin.DisplayAllActivity;
import com.cake.cakeadmin.OrderInsideActivity;
import com.cake.cakeadmin.R;
import com.cake.cakeadmin.databinding.FragmentHomeBinding;
import com.cake.cakeadmin.userActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore db;
    FirebaseDatabase realDb;

    TextView totalProduct,totalOrders,totalUsers,totalCategories;
    CardView Products,Orders,userData;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        realDb=FirebaseDatabase.getInstance();

        totalProduct=root.findViewById(R.id.totalProduct);
        totalOrders=root.findViewById(R.id.totalOrders);
        totalUsers=root.findViewById(R.id.totalUsers);
        totalCategories=root.findViewById(R.id.totalCategories);
        Orders=root.findViewById(R.id.Orders);
        userData=root.findViewById(R.id.userData);

        /*All Cards SElecteion*/
        Products=root.findViewById(R.id.Products);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            totalProduct.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        db.collection("MyOrder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;




                            }

                            totalOrders.setText(String.valueOf(count));
                            Log.d(TAG, "total data"+String.valueOf(count), task.getException());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        /*Category Count*/
        CollectionReference collection = db.collection("CakesCategory");
        AggregateQuery countQuery = collection.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AggregateQuerySnapshot snapshot = task.getResult();
                Log.d(TAG, "Count: " + snapshot.getCount());
                totalCategories.setText(String.valueOf(snapshot.getCount()));
            } else {
                Log.d(TAG, "Count failed: ", task.getException());
            }
        });






        /*Users Count*/



        realDb.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {

                   int mCartItemCount = (int) snapshot.getChildrenCount();
                    Log.d(TAG, "total data"+mCartItemCount);
                    totalUsers.setText(String.valueOf(mCartItemCount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DisplayAllActivity.class));
            }
        });

        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrderInsideActivity.class));
            }
        });

        userData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), userActivity.class));
            }
        });

        return  root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}