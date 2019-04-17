package com.jss.artha;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.artha.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static com.jss.artha.Fragment_Local.selectedItem;

public class BottomNavigate1 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private ImageView procam;
    private CircleImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private String URLstring = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<NaviagteModel> dataModelArrayList;
    private Naviagate_Adapter rvAdapter;
    RecyclerView recycle;
    RecyclerView.LayoutManager layoutManager;
    public TextView heading,profilename,addpost;
    public com.jss.artha.session sess;
    public static String s="all";
    int choice;
    public static int article2_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigate1);
        requestMultiplePermissions();
        addpost=(TextView)findViewById(R.id.addpost1);
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),addpost.class).putExtra("city",selectedItem));
            }
        });
        procam = (ImageView) findViewById(R.id.procam);
        imageview = findViewById(R.id.profileimg);
        procam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT);
            }
        });

        profilename=(TextView)findViewById(R.id.profilename);
        profilename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pro=new Intent(getApplicationContext(),User_Profile.class);
                startActivity(pro);
            }
        });
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recycle=findViewById(R.id.recycle);
        layoutManager=new LinearLayoutManager(this);
        recycle.setLayoutManager(layoutManager);
        drawerLayout=findViewById(R.id.drawer);
        drawerLayout.closeDrawers();
        recycle.setHasFixedSize(true);
        DividerItemDecoration myDivider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);

        myDivider.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        recycle.addItemDecoration(myDivider);
        fetch();





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

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(BottomNavigate1.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(BottomNavigate1.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(BottomNavigate1.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void fetch() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {


                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    NaviagteModel playerModel = new NaviagteModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setName(dataobj.getString("name"));

                                    dataModelArrayList.add(playerModel);

                                }

                                setupRecycler1();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void setupRecycler1(){

        rvAdapter = new Naviagate_Adapter(this,dataModelArrayList);
        recycle.setAdapter(rvAdapter);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
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