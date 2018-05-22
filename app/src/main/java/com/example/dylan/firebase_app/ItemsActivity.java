package com.example.dylan.firebase_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class ItemsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private TextView txtEmail;
    public static final String EXTRA_MESSAGE = "com.example.dylan.firebase_app";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        auth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtUserEmail);
        loadUserData();

        mFirebaseInstance = FirebaseDatabase.getInstance();
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


        auth.signOut();

        FirebaseUser user = auth.getCurrentUser();
        if(user == null) {
            openMainActivity();
        }
        else {
            Toast.makeText(ItemsActivity.this, "No se pudo salir de la sesión correctamente, por favor, inténtelo de nuevo", Toast.LENGTH_SHORT).show();
            return;
        }



    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);
    }


}
