package com.example.codehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private LearningPathAdapter adapter;

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
                    case R.id.nav_community: {
                        Toast.makeText(Home.this, "Community clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Home.this, CommunityActivity.class));
                        finish();
                        break;
                    }
                    case R.id.nav_progress:
                        Toast.makeText(Home.this, "Progress clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_account:
                        Toast.makeText(Home.this, "Account clicked", Toast.LENGTH_SHORT).show();
                        Intent intent_acc = new Intent(Home.this, Account.class);
                        startActivity(intent_acc);
//                        finish();
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
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of learning paths
        List<LearningPath> learningPaths = createLearningPaths();

        adapter = new LearningPathAdapter(learningPaths);
        recyclerView.setAdapter(adapter);
    }

    // Method to create a list of learning paths
    private List<LearningPath> createLearningPaths() {
        List<LearningPath> learningPaths = new ArrayList<>();

        // Add learning paths to the list
        learningPaths.add(new LearningPath("Java", R.drawable.ic_data_object, "Description of Java learning path"));
        learningPaths.add(new LearningPath("Python", R.drawable.ic_data_object, "Description of Python learning path"));
        learningPaths.add(new LearningPath("C/C++", R.drawable.ic_data_object, "Description of C/C++ learning path"));
//        learningPaths.add(new LearningPath("Web Development", R.drawable.ic_data_object, "Description of WebDev learning path"));
        // Add more learning paths as needed

        return learningPaths;
    }
}