package com.example.codehub;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText edtTxtUsername, edtTxtEmail, edtTxtPassword, edtTxtRetypePassword;
    Button signup;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference ref;

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    HashMap<String, String> signUpData = new HashMap<>();
                    signUpData.put("username", edtTxtUsername.getText().toString());
                    signUpData.put("email", edtTxtEmail.getText().toString());

//                    String uId = user.getUid();
//                    ref = FirebaseDatabase.getInstance().getReference("Users").child(uId);
//                    ref.push().setValue(signUpData, ((error, ref1) -> {
//                        if(error == null){
//                            Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getBaseContext(), "UnSuccess", Toast.LENGTH_SHORT).show();
//                        }
//                    }));

                    Intent intent = new Intent(Signup.this, Home.class);
                    startActivity(intent);

                    finish();
                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Signup.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        // initializing firebase authentication
        mAuth = FirebaseAuth.getInstance();

        edtTxtUsername = findViewById(R.id.username);
        edtTxtEmail = findViewById(R.id.email);
        edtTxtPassword = findViewById(R.id.password);
        edtTxtRetypePassword = findViewById(R.id.retypePassword);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(edtTxtEmail.getText());
                password = String.valueOf(edtTxtPassword.getText());
                createAccount(email, password);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            navigate to some other activity when user details are found
            Log.d(TAG, "Navigate to another activity when user details are found");
        }
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}