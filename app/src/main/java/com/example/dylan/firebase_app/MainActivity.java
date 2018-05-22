package com.example.dylan.firebase_app;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.dylan.firebase_app";
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            openItemsActivity();
        }

        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarLogin);
        email = findViewById(R.id.emailLogIn);
        password = findViewById(R.id.passwordLogIn);

    }


    public void openSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);
    }

    public void openItemsActivity() {
        Intent intent = new Intent(this, ItemsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);
    }

    public void login(View view) {

        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        if(TextUtils.isEmpty(emailStr)) {

            Toast.makeText(getApplicationContext(), "Ingresa un email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(passwordStr)) {

            Toast.makeText(getApplicationContext(), "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(MainActivity.this, "El email o contraseña son incorrectos, por favor, inténtelo de nuevo", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        else {
                            openItemsActivity();
                        }
                    }
                });
        }

        @Override
        public void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }
}

