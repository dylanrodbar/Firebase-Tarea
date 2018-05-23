package com.example.dylan.firebase_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private TextView txtEmail;
    private ProgressBar progressBar;
    private ListView list;
    DatabaseReference productDatabase;
    List<Item> items;



    public static final String EXTRA_MESSAGE = "com.example.dylan.firebase_app";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        auth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtUserEmail);
        progressBar = findViewById(R.id.progressBarSignout);
        loadUserData();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        productDatabase = FirebaseDatabase.getInstance().getReference("productos");

        list = findViewById(R.id.listView);

        items = new ArrayList<>();



    }

    @Override
    protected void onStart() {
        super.onStart();


        productDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Item> itemsL = new ArrayList<>();
                for(DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    String name = productSnapshot.getValue(Item.class).getName();
                    String price = productSnapshot.getValue(Item.class).getPrice();
                    String photo = productSnapshot.getValue(Item.class).getPhoto();
                    String description = productSnapshot.getValue(Item.class).getDescription();
                    String userId = productSnapshot.getValue(Item.class).getUserId();
                    String id = productSnapshot.getKey();

                    Item item = new Item(name, price, photo, description, userId, id);
                    itemsL.add(item);

                }

                //Toast.makeText(ItemsActivity.this, itemsL.get(2).getName(), Toast.LENGTH_SHORT).show();
                AdapterListView adapter = new AdapterListView(ItemsActivity.this, itemsL);
                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void createItem(View view) {
        startActivity(new Intent(ItemsActivity.this, AddItem.class));
    }

    public void saveItem(View view) {

    }

    public void loadUserData() {
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        txtEmail.setText(email);
    }

    public void signout(View view) {


        progressBar.setVisibility(View.VISIBLE);

        auth.signOut();

        FirebaseUser user = auth.getCurrentUser();
        if(user == null) {
            openMainActivity();
        }
        else {
            Toast.makeText(ItemsActivity.this, "No se pudo salir de la sesión correctamente, por favor, inténtelo de nuevo", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.GONE);



    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
