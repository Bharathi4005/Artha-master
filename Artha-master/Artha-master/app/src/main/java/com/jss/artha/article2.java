package com.jss.artha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.artha.R;

public class article2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        final String selected_article=intent.getStringExtra("selected_article");
        setContentView(R.layout.activity_article2);
        final WebView wb = new WebView(this);
        wb.getSettings().setJavaScriptEnabled(true);
        final ProgressDialog bar=new ProgressDialog(article2.this);
        bar.setMessage("Fetching Article");
        bar.show();
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view,String url){
                if(bar.isShowing()) {
                    bar.dismiss();
                }
            }
            @Override
            public void onReceivedError(WebView view,int errorCode,String description,String failingUrl){
                Toast.makeText(getApplicationContext(),"Failed to load!",Toast.LENGTH_SHORT).show();
            }
        });
        wb.loadUrl(selected_article);
        setContentView(wb);

    }
//    @Override
//    public void onBackPressed() {
//
//        finish();
//        //  Intent intent = new Intent(Village_Activity.this, MainActivity.class);
//        BottomNavigate1.article2_flag=1;
//        Intent launchNextActivity;
//        launchNextActivity = new Intent(article2.this, BottomNavigate1.class).putExtra("selected_category",Fragment_stories.s);
////        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(launchNextActivity);
//    }
}
