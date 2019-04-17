package com.jss.artha;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_Trending extends Fragment  implements View.OnClickListener {
        public TextView heading;
    public Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonG;
    public ImageButton addpost;
    private Context mContext;
    public static JSONArray jar;
    public session sess;
    public String mobile_no;
    protected static List<Category> categoryList;
    private ArrayList<String> categories;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter2 categoryAdapter;
    public RecyclerView feed_recycler;
    public RecyclerView.Adapter feed_Adpater;
    private ArrayList<String> user = new ArrayList<>();
    private ArrayList<String> body = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> body_url = new ArrayList<>();
    private String url="http://api.artha.today:3000/getfeeds";
    private ArrayList<String> userhandle = new ArrayList<>();
    private ArrayList<String> profile_resource_locater = new ArrayList<>();
    public static String selected;
    public static  String s;

    @SuppressLint("ValidFragment")
    public Fragment_Trending(String category){
        s=category;

    }
    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //just change the fragment_dashboard
        //s = getActivity().getIntent().getExtras().getString("selected_category");
            //with the fragment you want to inflate
            //like if the class is HomeFragment it should have R.layout.home_fragment
            //if it is DashboardFragment it should have R.layout.fragment_dashboard
            final View view=inflater.inflate(R.layout.fragment_trending,container,false);
            heading=view.findViewById(R.id.head);
            heading.setText("Trending");
            CategoryAdapter.last=0;

        final ProgressDialog progressDialogOut=new ProgressDialog(getContext());
        progressDialogOut.setMessage("Please Wait...");
        progressDialogOut.show();
            addpost=view.findViewById(R.id.more);
            ImageButton imageButton=view.findViewById(R.id.profile);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(view.getContext(),User_Profile.class));
                }
            });

        categoryList = new ArrayList<>();
        categoryList.clear();
        body.clear();
        user.clear();
        time.clear();
        body_url.clear();
        userhandle.clear();
        profile_resource_locater.clear();


        categoryRecyclerView = view.findViewById(R.id.idRecyclerViewHorizontalList);
        // add a divider after each item for more clarity
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.HORIZONTAL));

        categoryAdapter = new CategoryAdapter2(categoryList, view.getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);

        categoryRecyclerView.setAdapter(categoryAdapter);
        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(view.getContext()) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        smoothScroller.setTargetPosition(CategoryAdapter2.last);
        horizontalLayoutManager.startSmoothScroll(smoothScroller);
        categoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),feed_recycler,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View vv,int position){
                selected=categories.get(position);
                sess.set_home(false);
                Fragment_Trending f=new Fragment_Trending(selected);
                loadFragment(f);
                //startActivity(new Intent(getContext(),BottomNavigate1.class).putExtra("selected_category",selected));
            }

            public boolean loadFragment( Fragment fragment) {
                //switching fragment
                //if (fragment != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment )
                        .commit();
                System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

                System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

                System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

                System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

                System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

                return true;
                //return false;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        sess=new session(view.getContext());
        mobile_no=sess.get_mobile();

        categoryAdapter.notifyDataSetChanged();

        populateCategoryList();
        mContext = view.getContext();
        JSONObject params = new JSONObject();
        try {
            params.put("mobile_no", sess.get_mobile().toString());
            params.put("feature", "Trending stories");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("REsponse successful........................................");
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            System.out.println(response.toString());
                            System.out.println(response.getJSONArray("trendingData"));
                            jar = new JSONArray();
                            jar = response.getJSONArray("trendingData");
                            for (int i = 0; i <response.getJSONArray("trendingData").length(); i++) {
                                JSONObject job = new JSONObject();
                                job = jar.getJSONObject(i);
                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(BottomNavigate1.s+"////////////////////88888888888/");

                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////8888888888888888//////");

                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(BottomNavigate1.s+"/////////////////////////////////////888888888888888888888");

                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////////8888888888888//");

                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(CategoryAdapter.SelectedCategory+"/////////////////////////////////////");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////////8888888888888//");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////////8888888888888//");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////////8888888888888//");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////////8888888888888//");
                                System.out.println(BottomNavigate1.s+"///////////////////////////////////8888888888888//");
                                System.out.println(s+"@#$%^&*()(*&^%$%^&*()(*&^%$%^&*(*&^%$#$%^&*(");


                                if(job.getString("category").equalsIgnoreCase(s)) {

                                    if (job.getString("source_type").equalsIgnoreCase("twitter")) {
                                        //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
                                        System.out.println(job.getString("author") + "...Author");
                                        user.add(job.getString("author"));
                                        System.out.println(job.getString("body") + ".....body");
                                        body.add(job.getString("body"));
                                        System.out.println(job.getString("time_stamp") + "////////timestamp");
                                        time.add(job.getString("created_at"));
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
                                        if ((job.getString("source").isEmpty()))
                                            user.add("");
                                        else
                                            user.add(job.getString("source"));
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
                                else if( job.getString("category").equalsIgnoreCase(categoryList.get(0).getCategoryName())){

                                    if (job.getString("source_type").equalsIgnoreCase("twitter")) {
                                        //System.out.println(jar.get(i).toString()+"/////////////////////////////////");
                                        System.out.println(job.getString("author") + "...Author");
                                        user.add(job.getString("author"));
                                        System.out.println(job.getString("body") + ".....body");
                                        body.add(job.getString("body"));
                                        System.out.println(job.getString("time_stamp") + "////////timestamp");
                                        time.add(job.getString("created_at"));
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
                                        if ((job.getString("source").isEmpty()))
                                            user.add("");
                                        else
                                            user.add(job.getString("source"));
                                        profile_resource_locater.add(job.getString("image_url"));

                                        if ((job.getString("author").isEmpty()))
                                            userhandle.add("");
                                        else
                                            userhandle.add(job.getString("author"));
                                        time.add(job.getString("created_at"));
                                        body.add(job.getString("body"));
                                        if (job.getString("body_url").isEmpty()) {
                                            body_url.add("null");
                                        } else {
                                            body_url.add(job.getString("body_url"));
                                        }
                                    }
                                }
                                else
                                    {


                                        if( job.getString("category").equalsIgnoreCase(categoryList.get(0).getCategoryName())){


                                            System.out.println(job.getString("author") + "...Author");
                                            user.add(job.getString("author"));
                                            System.out.println(job.getString("body") + ".....body");
                                            body.add(job.getString("body"));
                                            System.out.println(job.getString("time_stamp") + "////////timestamp");
                                            time.add(job.getString("created_at"));
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

        return setfeed(view);
    }
    private void populateCategoryList(){

//        Category a=new Category("Apple");
//        Category b=new Category("Banana");
//        Category c=new Category("Cat");
//        Category d=new Category("Dog");
//        Category e=new Category("elephant");
//
//        categoryList.add(a);
//        categoryList.add(b);
//        categoryList.add(c);
//        categoryList.add(d);
//        categoryList.add(e);
//
//        categoryAdapter.notifyDataSetChanged();


        //        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,url, null,
        //                new Response.Listener<JSONObject>() {
        //                    @Override
        //                    public void onResponse(JSONObject response) {
        //                        try {
        //
        //                            System.out.println("aur bhai kaisa hai ::::::"+response.toString());
        //                            System.out.println("aur bhai kaisa hai ::::::"+response.toString());
        //                            System.out.println("aur bhai kaisa hai ::::::"+response.toString());
        //                            System.out.println("aur bhai kaisa hai ::::::"+response.toString());
        //                            System.out.println("aur bhai kaisa hai ::::::"+response.toString());
        //
        //                            JSONObject result;
        //                            categories=new ArrayList<>();
        //                            //result=response.getJSONObject("result");
        //                            JSONArray category_list=response.getJSONArray("trendingTopic");
        //
        //                            for (int i=0;i<category_list.length();i++){
        //
        //                                categories.add(category_list.getString(i));
        //                            }
        //
        //
        //                            System.out.println("categories ::::::"+categories.toString());
        //                            System.out.println("categories ::::::"+categories.toString());
        //                            System.out.println("categories ::::::"+categories.toString());
        //                            System.out.println("categories ::::::"+categories.toString());
        //                            System.out.println("categories ::::::"+categories.toString());
        //
        //
        //                            for (int i=0;i<categories.size();i++){
        //                                categoryList.add(new Category(categories.get(i)));
        //                            }
        //
        //                            System.out.println("categorieslist ::::::"+categoryList.toString());
        //                            System.out.println("categorieslist ::::::"+categoryList.toString());
        //                            System.out.println("categorieslist ::::::"+categoryList.toString());
        //                            System.out.println("categorieslist ::::::"+categoryList.toString());
        //                            System.out.println("categorieslist ::::::"+categoryList.toString());
        //
        //
        //
        //
        //
        //                            categoryAdapter.notifyDataSetChanged();
        //
        //
        //                        } catch (JSONException e) {
        //                            e.printStackTrace();
        //                        }
        //                    }
        //                }, new Response.ErrorListener() {
        //            @Override
        //            public void onErrorResponse(VolleyError error) {
        //
        //            }
        //        }) {
        //            @Override
        //            public String getBodyContentType() {
        //                return "application/json; charset=utf-8";
        //            }
        //
        //            @Override
        //            public byte[] getBody() {
        //                HashMap<String ,String> hashMap=new HashMap<>();
        //                hashMap.put("mobile_no",mobile_no);
        //                hashMap.put("feature","Trending stories");
        //                return  new JSONObject(hashMap).toString().getBytes();
        //            }
        //        };
        //        MySingleton.getInstance(getContext()).addToRequestQueue(jsonRequest);
                categories=Fragment_stories.trending;
        for (int i=0;i<categories.size();i++) {
            categoryList.add(new Category(categories.get(i)));
        }

                //categoryList.addAll(new Category(categories));
                categoryAdapter.notifyDataSetChanged();
//        for (int i=0;i<categories.size();i++){
//            categoryList.add(new Category(categories.get(i)));
//        }
//
//        System.out.println("categorieslist ::::::"+categoryList.toString());
//        System.out.println("categorieslist ::::::"+categoryList.toString());
//        System.out.println("categorieslist ::::::"+categoryList.toString());
//        System.out.println("categorieslist ::::::"+categoryList.toString());
//        System.out.println("categorieslist ::::::"+categoryList.toString());
//




        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
    public View setfeed(final View view)
    {   feed_recycler = view.findViewById(R.id.feed_recycler);
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
                if(body_url.get(position).isEmpty()){
                    Toast.makeText(getContext(),"This post is not available",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getContext(),article2.class).putExtra("selected_article",selected_article));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        categoryList = new ArrayList<>();


        categoryRecyclerView = view.findViewById(R.id.idRecyclerViewHorizontalList);
        // add a divider after each item for more clarity
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.HORIZONTAL));

        categoryAdapter = new CategoryAdapter2(categoryList, getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
//        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setAdapter(categoryAdapter);
        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(view.getContext()) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        smoothScroller.setTargetPosition(CategoryAdapter2.last);
        horizontalLayoutManager.startSmoothScroll(smoothScroller);
        categoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),feed_recycler,new RecyclerTouchListener.ClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View vv,int position){
                selected=categories.get(position);
                sess.set_home(false);
                CategoryAdapter2.last=position;
                Fragment_Trending trending=new Fragment_Trending(selected);
                loadFragment(trending);
                //startActivity(new Intent(getContext(),BottomNavigate1.class).putExtra("selected_category",selected));
            }

            @Override
            public void onLongClick(View view, int position) {
                selected=categories.get(position);
                sess.set_home(false);
                CategoryAdapter2.last=position;
                Fragment_Trending trending=new Fragment_Trending(selected);
                loadFragment(trending);

            }
        }));

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(),addpost.class).putExtra("city","blr"));
            }
        });

        categoryAdapter.notifyDataSetChanged();

        populateCategoryList();
        return view;
    }

    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        //if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
        System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

        System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

        System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

        System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

        System.out.println("jhsgfhsdhdfjsgfjdhsgcfbdhfchgf");

            return true;
        //return false;
    }
}
