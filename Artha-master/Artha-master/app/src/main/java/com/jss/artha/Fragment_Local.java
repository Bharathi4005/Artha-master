package com.jss.artha;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Local extends Fragment implements View.OnClickListener {
    public TextView heading;
    public ConstraintLayout linearlayout1;
    public Dialog myDialog;
    public ListView lvw;
    public session sess;
    public ImageButton cls;
    public TextView a;
    public static String city_code="blr";
    public ImageButton addpost;
    public RecyclerView feed_recycler;
    public RecyclerView.Adapter feed_Adpater;
    public static String selectedItem="";
    private Context mContext;
    public String URL_GET_FEEDSLOCAL = "http://api.artha.today:3000/getfeeds";
    public ArrayList<String> user=new ArrayList<>();
    public ArrayList<String> body=new ArrayList<>();
    public ArrayList<String> time=new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_local,container,false);
        final View view1=inflater.inflate(R.layout.popup,container,false);
        LayoutInflater li = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        heading=view.findViewById(R.id.head);
        myDialog = new Dialog(view1.getContext());
        myDialog.setContentView(R.layout.popup);
        heading.setText("Local Stories");
        addpost=view.findViewById(R.id.more);
        sess=new session(view.getContext());
        a=view.findViewById(R.id.a);
        CategoryAdapter.last=0;
        CategoryAdapter2.last=0;
        a.setText("Bangalore");
        if(sess.get_first()==1){
            a.setText("Bangalore");
        }
        ImageButton imageButton=view.findViewById(R.id.profile);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(),User_Profile.class));
            }
        });
        feed_recycler=view.findViewById(R.id.feed_recycler);
        feed_recycler.setHasFixedSize(true);
        feed_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        feed_Adpater=new local_Adapter(user,body,time);
        feed_recycler.setAdapter(feed_Adpater);

        a=view.findViewById(R.id.a);
        cls=myDialog.findViewById(R.id.close);
        ArrayList<String> cities=new ArrayList<String>();
        lvw=(ListView)myDialog.findViewById(R.id.lv);
        cities.add("India");cities.add("Bangalore");cities.add("Mumbai");cities.add("Hyderabad");cities.add("Kolkata");cities.add("Delhi");
        System.out.println(cities);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (view.getContext(), android.R.layout.simple_list_item_1, cities);
        System.out.println("gfrvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvsdr");
        lvw.setAdapter(arrayAdapter);
        System.out.println("gfrvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvsdr");
        a.setText("Bangalore");

        mContext = view.getContext();
        JSONObject params = new JSONObject();
        try {
            params.put("mobile_no",sess.get_mobile().toString() );
            params.put("username",user);
            params.put("feature", "Local stories");
            params.put("location","blr");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,URL_GET_FEEDSLOCAL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("REsponse successful........................................");
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            System.out.println(response.toString());
                            System.out.println(response.getJSONArray("message"));
                            JSONArray jar=new JSONArray();
                            jar=response.getJSONArray("message");
                            user.clear();body.clear();time.clear();
                            for(int i=0;i<response.getJSONArray("message").length();i++){
                                JSONObject job=new JSONObject();
                                job=jar.getJSONObject(i);
                                //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
                                System.out.println(job.getString("username")+"...Author");
                                user.add(job.getString("username"));
                                System.out.println(job.getString("post_body")+".....body");
                                body.add(job.getString("post_body"));
                                System.out.println(job.getString("posted_at")+"////////timestamp");
                                time.add(job.getString("posted_at"));
                                //feed_Adpater=new local_Adapter(user,body,time);
                                feed_recycler.setAdapter(new local_Adapter(user,body,time));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("JSON error/......................................");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(".............................................."+error.toString());
            }
        });
        MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
        lvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("gfrvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvsdr");
                selectedItem = (String) parent.getItemAtPosition(position);
                System.out.println(".............................."+selectedItem);
                System.out.println(".............................."+selectedItem);
                System.out.println(".............................."+selectedItem);
                System.out.println(".............................."+selectedItem);
                System.out.println(".............................."+selectedItem);
                switch (selectedItem){
                    case "Bangalore":
                        city_code="blr";
                        break;
                    case "Chennai":
                        city_code="chn";
                        break;
                    case "Delhi":
                        city_code="del";
                        break;
                        case "Hyderabad":
                        city_code="hyd";
                        break;
                    case "Pune":
                        city_code="pnq";
                        break;
                    case "Ahmedabad":
                        city_code="amd";

                    case "Mumbai":
                        city_code="bom";
                        break;
                    case "Kolkatta":
                        city_code="mum";
                        break;
                    default:
                        city_code="blr";
                        break;

                }
                //startActivity(new Intent(view.getContext(),Fragment_Local.class));



                //heading.setText(selectedItem);
                a.setText(selectedItem);

                mContext = view.getContext();
                JSONObject params = new JSONObject();
                try {
                    params.put("mobile_no",sess.get_mobile().toString() );
                    params.put("username",user);
                    params.put("feature", "Local stories");
                    params.put("location",city_code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,URL_GET_FEEDSLOCAL, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("REsponse successful........................................");
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                    System.out.println(response.toString());
                                    System.out.println(response.getJSONArray("message"));
                                    JSONArray jar=new JSONArray();
                                    jar=response.getJSONArray("message");
                                    user.clear();body.clear();time.clear();
                                    for(int i=0;i<response.getJSONArray("message").length();i++){
                                        JSONObject job=new JSONObject();
                                        job=jar.getJSONObject(i);
                                        //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
                                        System.out.println(job.getString("username")+"...Author");
                                        user.add(job.getString("username"));
                                        System.out.println(job.getString("post_body")+".....body");
                                        body.add(job.getString("post_body"));
                                        System.out.println(job.getString("posted_at")+"////////timestamp");
                                        time.add(job.getString("posted_at"));
                                        //feed_Adpater=new local_Adapter(user,body,time);
                                        feed_recycler.setAdapter(new local_Adapter(user,body,time));

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println("JSON error/......................................");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(".............................................."+error.toString());
                    }
                });
                MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
                if(sess.get_first()==0){
                myDialog.dismiss();
                sess.set_first(1);}

            }
        });
        if(sess.get_first()==0){
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();}
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(),addpost.class).putExtra("city",selectedItem));
            }
        });
        feed_recycler=view.findViewById(R.id.feed_recycler);
        feed_recycler.setHasFixedSize(true);
        feed_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        feed_Adpater=new local_Adapter(user,body,time);
        feed_recycler.setAdapter(feed_Adpater);


        cls.setOnClickListener(this);
        System.out.println(".............................."+selectedItem);
        System.out.println(".............................."+selectedItem);
        System.out.println(".............................."+selectedItem);
        System.out.println(".............................."+selectedItem);
        System.out.println(".............................."+selectedItem);
        feed_recycler=view.findViewById(R.id.feed_recycler);
        feed_recycler.setHasFixedSize(true);
        feed_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        feed_Adpater=new local_Adapter(user,body,time);
        feed_recycler.setAdapter(feed_Adpater);

//        mContext = view.getContext();
//        JSONObject params = new JSONObject();
//        try {
//            params.put("mobile_no", "7892008953");
//            params.put("feature", "Local stories");
//            params.put("location",city_code);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,URL_GET_FEEDSLOCAL, params,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            System.out.println("REsponse successful........................................");
//                            VolleyLog.v("Response:%n %s", response.toString(4));
//                            System.out.println(response.toString());
//                            System.out.println(response.getJSONArray("message"));
//                            JSONArray jar=new JSONArray();
//                            jar=response.getJSONArray("message");
//                            for(int i=0;i<response.getJSONArray("message").length();i++){
//                                JSONObject job=new JSONObject();
//                                job=jar.getJSONObject(i);
//                                //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
//                                System.out.println(job.getString("username")+"...Author");
//                                user.add(job.getString("username"));
//                                System.out.println(job.getString("post_body")+".....body");
//                                body.add(job.getString("post_body"));
//                                System.out.println(job.getString("posted_at")+"////////timestamp");
//                                time.add(job.getString("posted_at"));
//                                feed_recycler=view.findViewById(R.id.feed_recycler);
//                                feed_recycler.setHasFixedSize(true);
//                                feed_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                                feed_Adpater=new local_Adapter(user,body,time);
//                                feed_recycler.setAdapter(feed_Adpater);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            System.out.println("JSON error/......................................");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(".............................................."+error.toString());
//            }
//        });
//        MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);







        return view;
    }
    @Override
    public void onClick(View v){
        System.out.println("Inside on click");
        if(sess.get_first()==0){
        myDialog.dismiss();
        myDialog.cancel();
        sess.set_first(1);}
    }

//    public void ShowPopup(View view){
//        LayoutInflater inflater1 = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        setContentView(R.layout.popup);
//        ArrayList cities=new ArrayList<String>();
//        ListView lv=view.findViewById(R.id.lv);
//        cities.add("India");cities.add("Bangalore");cities.add("Mumbai");cities.add("Hyderabad");cities.add("Kolkata");
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (view.getContext(), android.R.layout.simple_list_item_1, cities);
//        lv.setAdapter(arrayAdapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Get the selected item text from ListView
//                String selectedItem = (String) parent.getItemAtPosition(position);
//
//                // Display the selected item text on TextView
//                heading.setText(selectedItem);
//                myDialog.dismiss();
//            }
//        });
//        myDialog.setContentView(R.layout.popup);
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//    }



}

