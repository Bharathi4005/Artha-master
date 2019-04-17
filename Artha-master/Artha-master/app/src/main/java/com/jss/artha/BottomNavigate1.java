package com.jss.artha;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artha.R;

public class BottomNavigate1 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public TextView heading;
    public com.jss.artha.session sess;
    public static String s="all";
    int choice;
    public static int article2_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigate1);
        heading=findViewById(R.id.head);
        //heading.setText("Local Stories");
        s= com.jss.artha.CategoryAdapter.SelectedCategory;
        sess=new com.jss.artha.session(this);
        int set=sess.get_fragment();
        if(set==1){
            sess.set_fragment(0);
            heading.setText("Local Stories");
            Fragment fragment = new Fragment_Local();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }
        else {
            loadFragment(new com.jss.artha.Fragment_stories("all"));

        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        //getting bottom navigation view and attaching the listener
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_stories:
                heading.setText("Stories");
                fragment = new Fragment_stories(Fragment_stories.categoryList.get(0).getCategoryName());
                break;

            case R.id.navigation_local:
                heading.setText("Local Stories");
                fragment = new Fragment_Local();
                break;

            case R.id.navigation_trending:
                heading.setText("Trending");
                fragment = new Fragment_Trending(Fragment_stories.trending.get(0));
                break;

        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        sess=new session(this);
//        finish();
        //  Intent intent = new Intent(Village_Activity.this, MainActivity.class);
        if(!sess.get_home()){
//            Intent launchNextActivity;
//            launchNextActivity = new Intent(Intent.ACTION_MAIN);
//            launchNextActivity.addCategory(Intent.CATEGORY_HOME);
//            launchNextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(launchNextActivity);


            if (exit) {
             //   finish(); // finish activity

                Intent launchNextActivity;
            launchNextActivity = new Intent(Intent.ACTION_MAIN);
            launchNextActivity.addCategory(Intent.CATEGORY_HOME);
            launchNextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchNextActivity);


            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }

        }
        else {
//            Intent launchNextActivity;
//            launchNextActivity = new Intent(BottomNavigate1.this, BottomNavigate1.class).putExtra("selected_category","all");
//            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            sess.set_home(true);
//            startActivity(launchNextActivity);

            if (exit) {
               // finish(); // finish activity

                Intent launchNextActivity;
                launchNextActivity = new Intent(Intent.ACTION_MAIN);
                launchNextActivity.addCategory(Intent.CATEGORY_HOME);
                launchNextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchNextActivity);



            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }



        }

    }


}