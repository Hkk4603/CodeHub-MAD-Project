package com.example.codehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_article);

        webView = findViewById(R.id.webView);

        // Retrieve the LearningPath object from the intent
        Intent intent = getIntent();
        if (intent != null) {
            LearningPath learningPath = (LearningPath) intent.getSerializableExtra("learningPath");
            if (learningPath != null) {
                // Load the Java learning path article in the WebView
                switch (learningPath.getTitle()){
                    case "Web Development" : loadArticle(learningPath.getTitle(), "https://ajith-s1.github.io/CodeHub_WebArticle/static/javascript.html");
                        break;
                    case "Java" : loadArticle(learningPath.getTitle(), "https://ajith-s1.github.io/CodeHub_WebArticle/static/JavaArticle.html");
                        break;
                    case "Pyhton" : loadArticle(learningPath.getTitle(), "https://ajith-s1.github.io/CodeHub_WebArticle/static/python.html");
                        break;
                    case "C/C++" : loadArticle(learningPath.getTitle(), "https://ajith-s1.github.io/CodeHub_WebArticle/static/Cpp.html");
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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}