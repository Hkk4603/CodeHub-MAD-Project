package com.example.codehub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddInfo extends AppCompatActivity implements View.OnClickListener {

    ImageView profilePic;
    Uri uri;
    EditText phno;
    String imgURL;
    Button signup;
    Bundle bundle;

    ActivityResultLauncher<Intent> activityResultLauncher;

    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        mAuth = FirebaseAuth.getInstance();

        profilePic = findViewById(R.id.uploadImg);
        phno = findViewById(R.id.phno);
        signup = findViewById(R.id.signup);

        profilePic.setOnClickListener(this);
        signup.setOnClickListener(this);

        // Fetching bundle from signup page
        bundle = getIntent().getExtras();
        ref = FirebaseDatabase.getInstance().getReference();


        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                uri = data.getData();
                            }
                            profilePic.setImageURI(uri);
                        } else {
                            Toast.makeText(AddInfo.this, "No image is selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        switch (view.getId()) {
            case R.id.uploadImg:
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
                break;
            case R.id.signup:
                saveData();
                Intent intent = new Intent(AddInfo.this, Home.class);
                Bundle homeBundle = new Bundle();
                homeBundle.putString("username", bundle.getString("username"));
                homeBundle.putString("email", bundle.getString("email"));
                intent.putExtras(homeBundle);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void saveData() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Profile Pics")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(AddInfo.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imgURL = urlImage.toString();
                Toast.makeText(AddInfo.this, "Pic upload done", Toast.LENGTH_SHORT).show();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });

        dialog.dismiss();
    }

    public void uploadData() {
        String uName = bundle.getString("username");
        String uEmailId = bundle.getString("email");
        String uPhno = phno.getText().toString();
        userModel uData = new userModel(uName, uEmailId, uPhno, imgURL);

        // The data is not getting pushed into the realtime database
//        System.out.println(mAuth.getCurrentUser().getUid());
        FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).setValue(uData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddInfo.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(AddInfo.this, "Data Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddInfo.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}