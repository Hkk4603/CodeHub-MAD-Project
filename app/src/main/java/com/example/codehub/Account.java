package com.example.codehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;

    ImageView profile_pic;
    TextView uname, email, phno;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uname = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        phno = findViewById(R.id.phno);
        profile_pic = findViewById(R.id.profile_pic);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        fetchAllInfo();
        dialog.dismiss();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(Account.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        Intent intent_acc = new Intent(Account.this, MainActivity.class);
                        startActivity(intent_acc);
                        finish();
                        break;
                    case R.id.nav_community:{
                        Toast.makeText(Account.this, "Community clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Account.this, CommunityActivity.class));
//                        finish();
                        break;
                    }
                    case R.id.nav_editor:
                        Toast.makeText(Account.this, "CodeEditor clicked", Toast.LENGTH_SHORT).show();
                        startActivity((new Intent(Account.this, TextEditor.class)));
                        break;
                    case R.id.nav_account:
                        Toast.makeText(Account.this, "Account clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout: {
                        Toast.makeText(Account.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent_logout = new Intent(Account.this, MainActivity.class);
                        startActivity(intent_logout);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void fetchAllInfo() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userModel retrieveUser = snapshot.getValue(userModel.class);
                assert retrieveUser != null;
                uname.setText(retrieveUser.getUserName());
                email.setText(retrieveUser.getUserEmail());
                phno.setText(retrieveUser.getuPhno());
                String imgUrl = retrieveUser.getProfilePic();

                Glide.with(Account.this).load(imgUrl).into(profile_pic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Account.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}