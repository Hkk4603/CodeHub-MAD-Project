package com.example.codehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TextEditor extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
        webView = findViewById(R.id.webView);
        String url = "https://www.online-ide.com";
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }
}