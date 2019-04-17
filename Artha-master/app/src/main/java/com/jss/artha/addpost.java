package com.jss.artha;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

public class addpost extends AppCompatActivity {
    public Button post,attach;
    public ImageButton close;
    public EditText post_body;
    public com.jss.artha.session sess;
    private Context mContext;
    public String ADD_LOCAL_STORY="http://api.artha.today:3000/addlocalstory";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        post=findViewById(R.id.post);
       // attach=findViewById(R.id.attach);
        close=findViewById(R.id.close);
        sess=new com.jss.artha.session(this);
        Intent intent=getIntent();
        post_body=findViewById(R.id.post_body);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext = getApplicationContext();

                if (!post_body.getText().toString().trim().isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("mobile_no", sess.get_mobile().toString());
                        params.put("feature", "Local stories");
                        params.put("username", sess.getUser().toString());
                        int time = (int) (System.currentTimeMillis());
                        Timestamp tsTemp = new Timestamp(time);
                        String ts = tsTemp.toString();
                        params.put("posted_at", ts);
                        params.put("post_body", post_body.getText().toString());
                        params.put("location", "blr");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(ADD_LOCAL_STORY, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(), "Post added successfully", Toast.LENGTH_SHORT);
                                    sess.set_fragment(1);
                                    startActivity(new Intent(addpost.this, BottomNavigate1.class).putExtra("selected_category", "all"));

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(".............................................." + error.toString());
                            Toast.makeText(getApplicationContext(), "Error! Try after sometime", Toast.LENGTH_SHORT);
                        }
                    });
                    MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);

                } else {
                    Toast.makeText(getApplicationContext(), "Post cannot be empty...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Deactivated for now
//        attach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(addpost.this,BottomNavigate1.class));
//            }
//        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addpost.this,BottomNavigate1.class).putExtra("selected_category","all"));
            }
        });

    }
}
