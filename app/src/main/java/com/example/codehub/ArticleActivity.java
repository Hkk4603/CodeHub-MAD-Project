package com.example.codehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ArticleActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private WebView webView;

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
        setContentView(R.layout.activity_java_article);

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
                    case R.id.nav_home: {
                        Toast.makeText(ArticleActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ArticleActivity.this, Home.class));
                        break;
                    }
                    case R.id.nav_community: {
                        Toast.makeText(ArticleActivity.this, "Community clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ArticleActivity.this, CommunityActivity.class));
//                        finish();
                        break;
                    }
                    case R.id.nav_progress:
                        Toast.makeText(ArticleActivity.this, "Progress clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_account:
                        Toast.makeText(ArticleActivity.this, "Account clicked", Toast.LENGTH_SHORT).show();
                        Intent intent_acc = new Intent(ArticleActivity.this, Account.class);
                        startActivity(intent_acc);
//                        finish();
                        break;
                    case R.id.nav_logout:
                    {
                        Toast.makeText(ArticleActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent_logout = new Intent(ArticleActivity.this, MainActivity.class);
                        startActivity(intent_logout);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

        webView = findViewById(R.id.webView);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            WebView.enableFileAccess(); // Enable file access
//        }

        // Retrieve the LearningPath object from the intent
        Intent intent = getIntent();
        if (intent != null) {
            LearningPath learningPath = (LearningPath) intent.getSerializableExtra("learningPath");
            if (learningPath != null) {
                // Load the Java learning path article in the WebView
                switch (learningPath.getTitle()){
                    case "Web Development" : loadArticle(learningPath.getTitle(), "file:///android_asset/static/javascript.html");
                        break;
                    case "Java" : loadArticle(learningPath.getTitle(), "file:///android_asset/static/JavaArticle.html");
                        break;
                    case "Pyhton" : loadArticle(learningPath.getTitle(), "file:///android_asset/static/python.html");
                        break;
                    case "C/C++" : loadArticle(learningPath.getTitle(), "file:///android_asset/static/Cpp.html");
                        break;
                }

//                if(learningPath.getTitle().equals("Java"))
//                    loadArticle(learningPath.getTitle(), "https://www.tutorialspoint.com/java/java_quick_guide.htm");
//                else if (learningPath.getTitle().equals("Python")) {
//                    loadArticle(learningPath.getTitle(), "https://www.tutorialspoint.com/python/python_quick_guide.htm");
//                }
//                else{
//                    loadArticle(learningPath.getTitle(), "https://www.tutorialspoint.com/cplusplus/cpp_quick_guide.htm");
//                }
            }
        }
    }
    private void loadArticle(String title, String url) {
        // Display the title in the ActionBar/Toolbar
        getSupportActionBar().setTitle(title);

        // Load the article URL in the WebView
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}