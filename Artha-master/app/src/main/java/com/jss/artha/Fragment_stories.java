package com.jss.artha;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

public class Fragment_stories  extends Fragment implements OnClickListener {
    public TextView heading;
    public Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonG;
    public ImageButton addpost;
    public String URL_GET_FEEDSALL = "http://api.artha.today:3000/getfeeds";
    private Context mContext;
    public static JSONArray jar;
    public session sess;
    public String mobile_no;
    protected static List<Category> categoryList;
    private ArrayList<String> categories;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    public RecyclerView feed_recycler;
    public RecyclerView.Adapter feed_Adpater;
    private ArrayList<String> user = new ArrayList<>();
    private ArrayList<String> body = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> body_url = new ArrayList<>();
    private String url="http://api.artha.today:3000/getfeaturelist";
    private ArrayList<String> userhandle = new ArrayList<>();
    private ArrayList<String> profile_resource_locater = new ArrayList<>();
    public static ArrayList<String> trending=new ArrayList<>();
    public static String selected;
    public static  String s="all";

    @SuppressLint("ValidFragment")
    public Fragment_stories(String category){
      s=category;

    }

    public Fragment_stories(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stories, container, false);
        heading = view.findViewById(R.id.head);
        heading.setText("Stories");
        sess=new session(view.getContext());
        addpost = view.findViewById(R.id.more);
        feed_recycler=view.findViewById(R.id.feed_recycler);
        CategoryAdapter2.last=0;

        final ProgressDialog progressDialogOut=new ProgressDialog(getContext());
        progressDialogOut.setMessage("Please Wait...");
        progressDialogOut.show();

//        if(!s.equalsIgnoreCase("all")) {
//            s = getActivity().getIntent().getExtras().getString("selected_category");
//        }
        s = getActivity().getIntent().getExtras().getString("selected_category");
        if(s=="")
        {
            s="all";
        }
        ImageButton imageButton = view.findViewById(R.id.profile);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), User_Profile.class));
            }
        });

        mobile_no=sess.get_mobile();
        //sess.set_cat("all");
        //selected=sess.get_cat();

        mContext = view.getContext();
        JSONObject params = new JSONObject();
        try {
            params.put("mobile_no", mobile_no);
            params.put("feature", "Stories");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(),addpost.class).putExtra("city","blr"));
            }
        });


        JsonObjectRequest jsonRequest = new JsonObjectRequest(URL_GET_FEEDSALL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            body.clear();
                            user.clear();
                            time.clear();
                            body_url.clear();
                            userhandle.clear();
                            profile_resource_locater.clear();

                            System.out.println("REsponse successful........................................");
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            System.out.println(response.toString());
                            System.out.println(response.getJSONArray("category_data"));
                            jar = new JSONArray();
                            jar = response.getJSONArray("category_data");
                            for (int i = 0; i <response.getJSONArray("category_data").length(); i++) {
                                 JSONObject job = new JSONObject();
                                job = jar.getJSONObject(i);
                                if(job.getString("category").equalsIgnoreCase(s)) {

                                    if (job.getString("source_type").equalsIgnoreCase("twitter")) {
                                        //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
                                        System.out.println(job.getString("author") + "...Author");
                                        user.add(job.getString("author"));
                                        System.out.println(job.getString("body") + ".....body");
                                        body.add(job.getString("body"));
                                        System.out.println(job.getString("time_stamp") + "////////timestamp");
                                        time.add(job.getString("time_stamp"));
                                        userhandle.add("@" + job.getString("author_screen_name"));
                                        profile_resource_locater.add(job.getString("author_profile_image_url"));
                                        System.out.println(job.getString("author_profile_image_url"));
//                                                                        feed_recycler = view.findViewById(R.id.feed_recycler);
//                                                                        feed_recycler.setAdapter(feed_Adpater);
                                        if (job.getString("body_url").isEmpty()) {
                                            body_url.add("null");
                                        } else {
                                            body_url.add(job.getString("body_url"));
                                        }

                                    } else {
                                        if ((job.getString("title").isEmpty()))
                                            user.add("");
                                        else
                                            user.add(job.getString("title"));
                                        profile_resource_locater.add(job.getString("image_url"));

                                        if ((job.getString("author").isEmpty()))
                                            userhandle.add("");
                                        else
                                            userhandle.add(job.getString("author"));
//                                        userhandle.add(job.getString("author"));
                                        time.add(job.getString("created_at"));
                                        body.add(job.getString("body"));
                                        if (job.getString("body_url").isEmpty()) {
                                            body_url.add("null");
                                        } else {
                                            body_url.add(job.getString("body_url"));
                                        }
                                    }
                                }
                                else if(s.equalsIgnoreCase("all")){

                                    if (job.getString("source_type").equalsIgnoreCase("twitter")) {
                                        //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
                                        System.out.println(job.getString("author") + "...Author");
                                        user.add(job.getString("author"));
                                        System.out.println(job.getString("body") + ".....body");
                                        body.add(job.getString("body"));
                                        System.out.println(job.getString("time_stamp") + "////////timestamp");
                                        time.add(job.getString("time_stamp"));
                                        userhandle.add("@" + job.getString("author_screen_name"));
                                        profile_resource_locater.add(job.getString("author_profile_image_url"));
                                        System.out.println(job.getString("author_profile_image_url"));
//                                                                        feed_recycler = view.findViewById(R.id.feed_recycler);
//                                                                        feed_recycler.setAdapter(feed_Adpater);
                                        if (job.getString("body_url").isEmpty()) {
                                            body_url.add("null");
                                        } else {
                                            body_url.add(job.getString("body_url"));
                                        }

                                    } else {
                                        if ((job.getString("title").isEmpty()))
                                            user.add("");
                                        else
                                            user.add(job.getString("title"));
                                        profile_resource_locater.add(job.getString("image_url"));

                                        //if ((job.getString("author").isEmpty()))
                                            userhandle.add("");
                                        //else
                                          //  userhandle.add(job.getString("author"));
                                        time.add(job.getString("created_at"));
                                        body.add(job.getString("body"));
                                        if (job.getString("body_url").isEmpty()) {
                                            body_url.add("null");
                                        } else {
                                            body_url.add(job.getString("body_url"));
                                        }
                                    }
                                }


                            }

                            progressDialogOut.dismiss();

                        } catch (JSONException e) {
                            progressDialogOut.dismiss();
                            e.printStackTrace();
                            System.out.println("JSON error/......................................");
                        }
                        setfeed(view);
                       }},new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse (VolleyError error){
                            progressDialogOut.dismiss();
                        System.out.println(".............................................." + error.toString());
                       // startActivity(new Intent(getContext(),BottomNavigate1.class).putExtra("selected_category","all"));
                        progressDialogOut.dismiss();
                    }}

                    );
                        MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);


        categoryList = new ArrayList<>();



        categoryRecyclerView = view.findViewById(R.id.idRecyclerViewHorizontalList);
        // add a divider after each item for more clarity
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.HORIZONTAL));

        categoryAdapter = new CategoryAdapter(categoryList, getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
                        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(view.getContext()) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        smoothScroller.setTargetPosition(CategoryAdapter.last);
        horizontalLayoutManager.startSmoothScroll(smoothScroller);
        //categoryAdapter.notifyDataSetChanged();
        return setfeed(view);
    }

    @Override
    public void onClick(View v) {

    }



    public View setfeed(final View view)
    {
        populateCategoryList(view);
        feed_recycler = view.findViewById(R.id.feed_recycler);
        feed_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        feed_Adpater = new feed_adapter(user, body, time, userhandle, profile_resource_locater);
        feed_recycler.setDrawingCacheEnabled(true);
        feed_recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        feed_recycler.setAdapter(feed_Adpater);
        feed_recycler.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),feed_recycler,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View vv,int position){
                String selected_article=body_url.get(position);
                //sess.set_cat(selected_article);
                if(body_url.get(position).isEmpty()){Toast.makeText(getContext(),"This post is not available",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getContext(),article.class).putExtra("selected_article",selected_article));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        categoryList = new ArrayList<>();



        categoryRecyclerView = view.findViewById(R.id.idRecyclerViewHorizontalList);
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.HORIZONTAL));

        categoryAdapter = new CategoryAdapter(categoryList, getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
        final RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(view.getContext()) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
        };
        smoothScroller.setTargetPosition(CategoryAdapter.last);
        System.out.println(CategoryAdapter.last+"last position");
        System.out.println(CategoryAdapter.last+"last position");
        System.out.println(CategoryAdapter.last+"last position");
        System.out.println(CategoryAdapter.last+"last position");
        System.out.println(CategoryAdapter.last+"last position");
        System.out.println(CategoryAdapter.last+"last position");
        System.out.println(CategoryAdapter.last+"last position");
        horizontalLayoutManager.startSmoothScroll(smoothScroller);
        categoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),feed_recycler,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View vv,int position){
                selected=categories.get(position);
                CategoryAdapter.last=position;
                sess.set_home(false);
                startActivity(new Intent(getContext(),BottomNavigate1.class).putExtra("selected_category",selected));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }
    private void populateCategoryList(final View view){
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject result;
                            categories=new ArrayList<>();
                            categories.add("ALL");
                            result=response.getJSONObject("result");
                            JSONArray category_list=result.getJSONArray("subscribed_list");
                            JSONArray tr=result.getJSONArray("trending_list");
                            for (int i=0;i<category_list.length();i++){

                                categories.add(category_list.getJSONObject(i).getString("category_name"));
                            }
                            trending.clear();

                            for (int i=0;i<tr.length();i++){

                                trending.add(tr.getString(i));
                            }

                            for (int i=0;i<categories.size();i++){
                                categoryList.add(new Category(categories.get(i)));
                            }
                            categoryAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            System.out.println("/////////"+e.toString()+"thisthisthisithisthis");

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("rrrrrrrrrrrrrrrrrrr"+error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                HashMap<String ,String> hashMap=new HashMap<>();
                hashMap.put("mobile_no",mobile_no);

                return  new JSONObject(hashMap).toString().getBytes();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonRequest);
        categoryAdapter.notifyDataSetChanged();
    }

}