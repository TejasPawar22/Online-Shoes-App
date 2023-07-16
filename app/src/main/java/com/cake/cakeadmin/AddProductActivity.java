package com.cake.cakeadmin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cake.cakeadmin.Models.CategoriesModel;
import com.cake.cakeadmin.Models.DisplayAllProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductActivity extends AppCompatActivity {
    CircleImageView productImg;
    EditText productName,productPrice,productDesc;
    Button update,btn_add_update;
    FirebaseFirestore firestore;
    String spinnerdata;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DisplayAllProductsModel viewAllModel = null;

    ArrayList<String> dataModalArrayList;
    com.cake.cakeadmin.Models.userModel userModel;
    Random r = new Random();
    int number = 100000 + (int)(r.nextFloat() * 899900);
    String productId= number+"product";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        dataModalArrayList = new ArrayList<String>();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();


        productImg = findViewById(R.id.product_img);
        productName= findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDesc = findViewById(R.id.productDesc);
        update = findViewById(R.id.btn_update);
        btn_add_update=findViewById(R.id.btn_add_update);

        btn_add_update.setVisibility(View.GONE);
        update.setText("Add Product");

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        ArrayList<String> arrayList1 = new ArrayList<String>();
        firestore.collection("Categories").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                String dataModal = d.toObject(CategoriesModel.class).getCat().toString();

                                // after getting data from Firebase we are
                                // storing that data in our array list


                                    dataModalArrayList.add(dataModal);

                            }
                            // after that we are passing our array list to our adapter class.
                            ArrayAdapter adapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            dropdown.setAdapter(adapter);

                            Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
                            spinnerdata = mySpinner.getSelectedItem().toString();


                        } else {
                            arrayList1.add("test");
                            ArrayAdapter adapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList1);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            dropdown.setAdapter(adapter);
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(AddProductActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(AddProductActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });





        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });







        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Map<String, Object> productMap = new HashMap<>();
                productMap.put("name", productName.getText().toString());
                productMap.put("price",productPrice.getText().toString() );
                productMap.put("rating", "5.0");
                productMap.put("description", productDesc.getText().toString());
                productMap.put("type",spinnerdata);

                firestore.collection("AllProducts").document(productId)
                        .update(productMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddProductActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully written!");


                                FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",
                                        "New Cakes Available Taste it Now...",
                                        productName.getText().toString(),getApplicationContext(),AddProductActivity.this);
                                notificationsSender.SendNotifications();


                                }


                        });


                /*   updateUserProfile(userName,userEmail,userAddress,UserPh);*/

            }
        });


        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof DisplayAllProductsModel) {
            viewAllModel = (DisplayAllProductsModel) object;


        Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(productImg);


        productName.setText(viewAllModel.getName());
        productPrice.setText(viewAllModel.getPrice());
        productDesc.setText(viewAllModel.getDescription());
            update.setVisibility(View.GONE);
            btn_add_update.setVisibility(View.VISIBLE);
            btn_add_update.setText("Update Product");

btn_add_update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        firestore.collection("AllProducts").whereEqualTo("name",productName.getText().toString())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Map<String, Object> productMap = new HashMap<>();
                                productMap.put("name", productName.getText().toString());
                                productMap.put("price",productPrice.getText().toString() );
                                productMap.put("rating", "5.0");
                                productMap.put("description", productDesc.getText().toString());
                                productMap.put("type",spinnerdata);

                                firestore.collection("AllProducts").document(document.getId())
                                        .update(productMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddProductActivity.this, "Product Updated!", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "DocumentSnapshot successfully written!");


                                                FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",
                                                        "20% Off Now....Make Your Parties Great",
                                                        productName.getText().toString(),getApplicationContext(),AddProductActivity.this);
                                                notificationsSender.SendNotifications();


                                            }


                                        });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
});



        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            Uri profileUri = data.getData();
            productImg.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("product_image")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddProductActivity.this, "Product Picture Uploaded", Toast.LENGTH_SHORT).show();


                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {




                          //  String productId= number+"product";
                            Map<String, Object> hopperUpdates = new HashMap<>();
                            hopperUpdates.put("img_url", uri.toString());
                            final Object object = getIntent().getSerializableExtra("detail");

                            if (object instanceof DisplayAllProductsModel) {
                                viewAllModel = (DisplayAllProductsModel) object;


                                firestore.collection("AllProducts").whereEqualTo("name",productName.getText().toString())
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                if (task.isSuccessful()) {
                                                    for (DocumentSnapshot document : task.getResult()) {
                                                        firestore.collection("AllProducts").document(document.getId()).update(hopperUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(AddProductActivity.this, "Product Image Updated!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });



                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }

                                        });

                            }else {

                                firestore.collection("AllProducts").document(productId).set(hopperUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(AddProductActivity.this, "Product Image Uploaded!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }
                    });
                }
            });

        }
    }
}