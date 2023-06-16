package com.example.codehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(Home.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_community:
                        Toast.makeText(Home.this, "Community clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_progress:
                        Toast.makeText(Home.this, "Progress clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_account:
                        Toast.makeText(Home.this, "Account clicked", Toast.LENGTH_SHORT).show();
                        Intent intent_acc = new Intent(Home.this, Account.class);
                        startActivity(intent_acc);
                        finish();
                        break;
                    case R.id.nav_logout:
                    {
                        Toast.makeText(Home.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent_logout = new Intent(Home.this, MainActivity.class);
                        startActivity(intent_logout);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

    }
}