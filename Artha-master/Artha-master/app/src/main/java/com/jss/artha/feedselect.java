package com.jss.artha;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class feedselect extends AppCompatActivity {

    public static ArrayList dataModels;
    ListView listView;
    public static RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerViewAdapter;
    public Button cnt;
    private CheckBox checkBox_app;
    //  public CustomAdapter adapter;
    public static ArrayList arrayList;
    public static ArrayList arrayList_id;
    public static ArrayList<Integer> images;
    public static Gson gson;
    public static String mobile_no;
    public static String URL_FEED_SELECT="http://api.artha.today:3000/getcategorylist";
    public static String URL_SUBSCRIBE="http://api.artha.today:3000/category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedselect);



        //  listView = (ListView) findViewById(R.id.chk);
        cnt=(Button)findViewById(R.id.next);
        dataModels = new ArrayList();

        images=new ArrayList<>();
        images.add(R.drawable.politics);
        images.add(R.drawable.business);
        images.add(R.drawable.education);
        images.add(R.drawable.technology);
        images.add(R.drawable.startups);
        images.add(R.drawable.travel);
        images.add(R.drawable.food);
        images.add(R.drawable.fashion);
        images.add(R.drawable.music);
        images.add(R.drawable.hollywood);
        images.add(R.drawable.bollywood);
        images.add(R.drawable.international);
        images.add(R.drawable.sports);
        images.add(R.drawable.elections);



        checkBox_app=findViewById(R.id.check);
        gson = new GsonBuilder().setPrettyPrinting().create();

        recyclerView=(RecyclerView)findViewById(R.id.recycler);

        recyclerViewAdapter=new RecyclerViewAdapter(feedselect.this,dataModels);

        recyclerView.setLayoutManager(new GridLayoutManager(feedselect.this,3));

        Intent intent=getIntent();
        mobile_no=intent.getStringExtra("mobile_no");



        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,URL_FEED_SELECT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("response::::::::"+response.toString());
                            VolleyLog.v("Response:%n %s", response.toString());
                            JSONObject result=new JSONObject();
                            result=response.getJSONObject("result");
                            JSONArray category_list= result.getJSONArray("category_list");
                            arrayList =new ArrayList();
                            arrayList_id =new ArrayList();



                            for(int i=1;i<category_list.length();i++){
                                JSONObject jsonObject=category_list.getJSONObject(i);
                                arrayList.add(jsonObject.getString("category_name"));
                                arrayList_id.add(jsonObject.getString("category_id"));
                                //   adapter = new CustomAdapter(dataModels, feedselect.this);
                                recyclerViewAdapter=new RecyclerViewAdapter(feedselect.this,dataModels);

                            }

                            recyclerView.setLayoutManager(new GridLayoutManager(feedselect.this,3));


                            for(int i=0;i<arrayList.size();i++){
                                String feed=arrayList.get(i).toString();
                                String id=arrayList_id.get(i).toString();
                                System.out.println("feed::::::::::"+feed);
                                System.out.println("feed::::::::::"+feed);

                                System.out.println("id::::::::::"+id);
                                System.out.println("id::::::::::"+id);

                                dataModels.add(new DataModel(feed,id,false,images.get(i)));
                            }

                            System.out.println("feeds::::::::::::"+arrayList.toString());
                            System.out.println("feeds::::::::::::"+arrayList.toString());
                            System.out.println("feeds::::::::::::"+arrayList.toString());


                            System.out.println("datamodels::::::::::::"+dataModels.toString());
                            System.out.println("datamodels::::::::::::"+dataModels.toString());
                            System.out.println("datamodels::::::::::::"+dataModels.toString());
                            // listView.setAdapter(adapter);
                            recyclerView.setAdapter(recyclerViewAdapter);
                            //      checkBox_app.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            }) {
//                                @Override
//                                public void onItemClick(AdapterView parent, View view, int position, long id) {
//
//                                    DataModel dataModel= (DataModel) dataModels.get(position);
//                                    dataModel.checked = !dataModel.checked;
//
//                                    int flag;
//
//                                    if(dataModel.checked){
//                                        flag=1;
//                                    }
//                                    else
//                                    {
//                                        flag=0;
//                                    }
//
//                                    HashMap<String,String> hashMap=new HashMap<>();
//                                    hashMap.put("category_id",dataModel.id);
//                                    hashMap.put("category_name",dataModel.name);
//
//                                    Feed feed=new Feed(mobile_no,String.valueOf(flag),hashMap);
//
//                                    final String final_feed=gson.toJson(feed);
//                                    System.out.println("final_feed 2:::::::"+final_feed);
//                                    System.out.println("final_feed 2:::::::"+final_feed);
//                                    System.out.println("final_feed 2:::::::"+final_feed);
//                                    System.out.println("final_feed 2:::::::"+final_feed);
//                                    System.out.println("final_feed 2:::::::"+final_feed);
//
//
//                                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL_SUBSCRIBE,
//                                            null, new Response.Listener<JSONObject>() {
//
//                                        @Override
//                                        public void onResponse(JSONObject response) {
//                                            try {
//                                                JSONObject jsonObject=new JSONObject();
//                                                jsonObject=response.getJSONObject("result");
//                                                if(jsonObject.getBoolean("success")) {
//
//                                                    Log.d("Response", response.toString());
//
//                                                    System.out.println("inside respone::::::::::::");
//                                                    System.out.println("inside respone::::::::::::");
//                                                    System.out.println("inside respone::::::::::::");
//
//                                                    if (response.getString("message").equals("subscribed")) {
//                                                        Toast.makeText(feedselect.this, "" + " Subscribed Successfully ", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                    else
//                                                    {
//                                                        Toast.makeText(feedselect.this, "" + " UnSubscribed Successfully ", Toast.LENGTH_SHORT).show();
//
//                                                    }
//
//                                                }
//                                                else
//                                                {
//                                                    Toast.makeText(feedselect.this, " Failed to subscribe ", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } catch (JSONException e) {
//                                                System.out.println("inside catch of feedselect  to server");
//                                                e.printStackTrace();
//
//                                            }
//                                        }
//                                    }, new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            VolleyLog.d("Error", "Error: " + error.getMessage());
//                                            System.out.println("Error inside errorrr listener of save feedselect to server ::::::::"+error.toString());
//                                            error.printStackTrace();
//
//                                            Toast.makeText(feedselect.this, "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }) {
//                                        @Override
//                                        public String getBodyContentType() {
//                                            return "application/json; charset=utf-8";
//                                        }
//
//                                        @Override
//                                        public byte[] getBody() {
//
//                                            return  final_feed.getBytes();
//                                        }
//                                    };
//                                    MySingleton.getInstance(feedselect.this).addToRequestQueue(req);
//
//
//
//                                    recyclerViewAdapter.notifyDataSetChanged();


                            //}
                            // });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Error in error response of feeedselect:::::::::"+error.toString());
                System.out.println("Error in error response of feeedselect:::::::::"+error.toString());
                System.out.println("Error in error response of feeedselect:::::::::"+error.toString());

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("mobile_no", mobile_no);
                return new JSONObject(params).toString().getBytes();
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(jsonRequest);


        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RecyclerViewAdapter.SelectedNumber < 3) {
                    Toast.makeText(getApplicationContext(),"Subscribe to Atleast 3 categories",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Intent in = new Intent(feedselect.this, BottomNavigate1.class);
                    in.putExtra("mobile_no", mobile_no);
                    in.putExtra("selected_category", "all");
                    startActivity(in);
                }
            }
        });
    }
}

