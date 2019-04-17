package com.jss.artha;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public static int SelectedNumber=0;
    private Context mContext ;
    public CardView crd;
    private ArrayList mData =  new ArrayList<>();



    public RecyclerViewAdapter(Context mContext, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_lay,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, final int position) {

        DataModel dataModel=(DataModel) mData.get(position);

//        if(dataModel.getName().equalsIgnoreCase("politics")){
//            holder.p.setImageDrawable(R.drawable.Politics);
//        }


//        holder.tv_book_title.setText(String.valueOf(mData.get(position)));
        holder.tv_book_title.setText(dataModel.getName());
        // holder.p.setImageResource(dataModel.getImage_id());
        Picasso.with(mContext).load(dataModel.getImage_id()).transform(new CircleTransform()).fit().into(holder.p);

        // Picasso.with(mContext).load(mayorShipImageLink).transform(new CircleTransform()).into(ImageView);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (!holder.checkBox.isChecked()) {
//
//                    feedselect.recyclerViewAdapter.notifyDataSetChanged();
//
                    DataModel dataModel= (DataModel) feedselect.dataModels.get(position);
                    dataModel.checked = !dataModel.checked;
                    --SelectedNumber;
//
//                    if(dataModel.checked){
//
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        //holder.checkBox.setChecked(false);
//                    }
//                    else
//                    {
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//                        System.out.println("checked:::::::::"+dataModel.checked);
//
//                      //  holder.checkBox.setChecked(true);
//                    }
//
//                    dataModel.checked = !dataModel.checked;
//
//                    System.out.println("position::::::: "+position);
//                    System.out.println("position::::::: "+position);
//                    System.out.println("position::::::: "+position);
//                    System.out.println("position::::::: "+position);
//                    System.out.println("position::::::: "+position);
//
                    int flag;

                    if(dataModel.checked){
                        flag=1;

                    }
                    else
                    {
                        flag=0;
                    }

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("category_id",dataModel.id);
                    hashMap.put("category_name",dataModel.name);

                    Feed feed=new Feed(feedselect.mobile_no,String.valueOf(flag),hashMap);

                    final String final_feed=feedselect.gson.toJson(feed);
                    System.out.println("final_feed 2:::::::"+final_feed);
                    System.out.println("final_feed 2:::::::"+final_feed);
                    System.out.println("final_feed 2:::::::"+final_feed);
                    System.out.println("final_feed 2:::::::"+final_feed);
                    System.out.println("final_feed 2:::::::"+final_feed);


                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, feedselect.URL_SUBSCRIBE,
                            null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject=response.getJSONObject("result");
                                if(jsonObject.getBoolean("success")) {

                                    Log.d("Response", response.toString());

                                    System.out.println("inside respone::::::::::::");
                                    System.out.println("inside respone::::::::::::");
                                    System.out.println("inside respone::::::::::::");

                                    if (!response.getString("message").equalsIgnoreCase("unsubscribed")) {
                                        //Toast.makeText(v.getContext(), "" + " UnSubscribed Successfully ", Toast.LENGTH_SHORT).show();
                                    }
//                                    else
//                                    {
//                                        Toast.makeText(view.getContext(), "" + " UnSubscribed Successfully ", Toast.LENGTH_SHORT).show();
//
//                                    }

                                }
                                else
                                {
                                    Toast.makeText(v.getContext(), " Failed to subscribe ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("inside catch of feedselect  to server");
                                e.printStackTrace();

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Error", "Error: " + error.getMessage());
                            System.out.println("Error inside errorrr listener of save feedselect to server ::::::::"+error.toString());
                            error.printStackTrace();

                            Toast.makeText(v.getContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() {

                            return  final_feed.getBytes();
                        }
                    };
                    MySingleton.getInstance(v.getContext()).addToRequestQueue(req);
//
//
//
//                    feedselect.recyclerViewAdapter.notifyDataSetChanged();


                    // Toast.makeText(v.getContext(), "Unsubscribed Successfully---> " + mData.get(position), Toast.LENGTH_SHORT).show();
                } else {
//
//                    feedselect.recyclerViewAdapter.notifyDataSetChanged();
//                    System.out.println("position1::::::: "+position);
//                    System.out.println("position1::::::: "+position);
//                    System.out.println("position1::::::: "+position);
//                    System.out.println("position1::::::: "+position);
//                    System.out.println("position1::::::: "+position);
//
//
//
                    DataModel dataModel= (DataModel) feedselect.dataModels.get(position);
                    dataModel.checked = !dataModel.checked;

                    ++SelectedNumber;
//
                    int flag;

                    if(dataModel.checked){
                        flag=1;


                    }
                    else
                    {
                        flag=0;
                    }

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("category_id",dataModel.id);
                    hashMap.put("category_name",dataModel.name);

                    Feed feed=new Feed(feedselect.mobile_no,String.valueOf(flag),hashMap);

                    final String final_feed=feedselect.gson.toJson(feed);
                    System.out.println("final_feed 3:::::::"+final_feed);
                    System.out.println("final_feed 3:::::::"+final_feed);
                    System.out.println("final_feed 3:::::::"+final_feed);
                    System.out.println("final_feed 3:::::::"+final_feed);
                    System.out.println("final_feed 3:::::::"+final_feed);


                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, feedselect.URL_SUBSCRIBE,
                            null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject=response.getJSONObject("result");
                                if(jsonObject.getBoolean("success")) {

                                    Log.d("Response", response.toString());

                                    System.out.println("inside respone::::::::::::");
                                    System.out.println("inside respone::::::::::::");
                                    System.out.println("inside respone::::::::::::");

                                    if (response.getString("message").equals("subscribed")) {
                                        //Toast.makeText(v.getContext(), "" + " Subscribed Successfully ", Toast.LENGTH_SHORT).show();
                                    }
//                                    else
//                                    {
//                                        Toast.makeText(view.getContext(), "" + " UnSubscribed Successfully ", Toast.LENGTH_SHORT).show();
//
//                                    }

                                }
                                else
                                {
                                    Toast.makeText(v.getContext(), " Failed to subscribe ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("inside catch of feedselect  to server");
                                e.printStackTrace();

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("Error", "Error: " + error.getMessage());
                            System.out.println("Error inside errorrr listener of save feedselect to server ::::::::"+error.toString());
                            error.printStackTrace();

                            Toast.makeText(v.getContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() {

                            return  final_feed.getBytes();
                        }
                    };
                    MySingleton.getInstance(v.getContext()).addToRequestQueue(req);
//
//
//
//                    feedselect.recyclerViewAdapter.notifyDataSetChanged();
//
//
//
//
//
//

                    //   Toast.makeText(v.getContext(), "Subscribed Successfully---> " + mData.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView p;
        CardView crd;
        CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.tit) ;
            crd=(CardView)itemView.findViewById(R.id.cr);
            p=(ImageView)itemView.findViewById(R.id.p);
            checkBox=itemView.findViewById(R.id.check);


        }
    }


}
