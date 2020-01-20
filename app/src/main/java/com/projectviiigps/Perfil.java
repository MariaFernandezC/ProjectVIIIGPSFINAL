package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;

import org.json.JSONException;
import org.json.JSONObject;

public class Perfil extends AppCompatActivity {

    // String  url="https://www.xataka.com/";
       String  url="https://elpais.com/tecnologia/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        WebView web = findViewById(R.id.MyWebSite);
        web.setWebViewClient(new MyWebViewClient());
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {

        public boolean shouldOverrideURLLoading (WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
