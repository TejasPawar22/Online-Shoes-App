package com.cake.cakeadmin;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cake.cakeadmin.Adapter.DisplayAllProductsAdapter;
import com.cake.cakeadmin.Models.DisplayAllProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    DisplayAllProductsAdapter displayAllProductsAdapter;
    List<DisplayAllProductsModel> displayAllProductsModelsList;
    /*Search*/
    EditText search_box;
    ImageView editButton;
    private RecyclerView recyclerViewSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);

        editButton= findViewById(R.id.AddProduct);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayAllActivity.this,AddProductActivity.class));

            }
        });
        //connect
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;


        }
        else {
            connected = false;
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText(String.valueOf("No Internet Connection!")).show();

        }


        firestore = FirebaseFirestore.getInstance();



        recyclerView=findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayAllProductsModelsList = new ArrayList<>();
        displayAllProductsAdapter = new DisplayAllProductsAdapter(this,displayAllProductsModelsList);

        recyclerView.setAdapter(displayAllProductsAdapter);
        //////geting Fruits

            firestore.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {


                    // Get the new document Id

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        DisplayAllProductsModel viewAllModel =documentSnapshot.toObject(DisplayAllProductsModel.class);
                        displayAllProductsModelsList.add(viewAllModel);
                        displayAllProductsAdapter.notifyDataSetChanged();

                    }
                }
            });


        //////geting Fruits
      /*  if(type != null && type.equalsIgnoreCase("jarcakes")){
            firestore.collection("AllProducts").whereEqualTo("type","jarcakes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);

                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });

        }*/

        recyclerViewSearch= findViewById(R.id.view_all_rec);
        search_box= findViewById(R.id.search_box);
        displayAllProductsModelsList = new ArrayList<>();
        displayAllProductsAdapter = new DisplayAllProductsAdapter(this,displayAllProductsModelsList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearch.setAdapter(displayAllProductsAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    firestore.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                                DisplayAllProductsModel viewAllModel =documentSnapshot.toObject(DisplayAllProductsModel.class);
                                displayAllProductsModelsList.add(viewAllModel);
                                displayAllProductsAdapter.notifyDataSetChanged();
                            }
                        }
                    });


                }else{
                    searchProduct(editable.toString());

                }
            }

            private void searchProduct(String type) {
                if(!type.isEmpty()){

                    firestore.collection("AllProducts").whereGreaterThanOrEqualTo("name",type).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful() && task.getResult() !=null){

                                        displayAllProductsModelsList.clear();
                                        displayAllProductsAdapter.notifyDataSetChanged();

                                        for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                            DisplayAllProductsModel viewAllModel = documentSnapshot.toObject(DisplayAllProductsModel.class);
                                            displayAllProductsModelsList.add(viewAllModel);
                                            displayAllProductsAdapter.notifyDataSetChanged();
                                        }



                                    }
                                }
                            });
                }

            }


        });



    }
}