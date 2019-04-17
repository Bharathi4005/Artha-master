package com.jss.artha;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.artha.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpEnter extends AppCompatActivity {
    public Button otp;
    public String Case;
    public String URL_CREATE_USER="http://api.artha.today:3000/user/create";
    public EditText phone,OTP_verify1,OTP_verify2,OTP_verify3,OTP_verify4;
    public String otp_su,otp_si,OTP_verify;
    public ImageButton phonec;
    public static String username;
    private Context mContext;
    public session sess;
    public String ph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();

        setContentView(R.layout.activity_otp_enter);
        //phone=findViewById(R.id.phone);
        otp=findViewById(R.id.button);
        sess=new session(this);


        OTP_verify1=findViewById(R.id.enter_otp_1);
        OTP_verify2=findViewById(R.id.enter_otp_2);
        OTP_verify3=findViewById(R.id.enter_otp_3);
        OTP_verify4=findViewById(R.id.enter_otp_4);


        OTP_verify1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(OTP_verify1.getText().toString().length()==1)     //size as per your requirement
                {
                    OTP_verify2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        OTP_verify2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(OTP_verify2.getText().toString().length()==1)     //size as per your requirement
                {
                    OTP_verify3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        OTP_verify3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(OTP_verify3.getText().toString().length()==1)     //size as per your requirement
                {
                    OTP_verify4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });









        Case=intent.getStringExtra("case");
        ph=intent.getStringExtra("mobile_no");
        Toast.makeText(getApplicationContext(),"This number:",Toast.LENGTH_LONG);
        System.out.println("This number......"+ph);
        System.out.println("This number......"+ph);
        System.out.println("This number......"+ph);
        System.out.println("This number......"+ph);
        System.out.println("This number......"+ph);
        sess.set_mobile(ph);
        //System.out.println("otpotptopotpoopt"+OTP_verify1+OTP_verify2+OTP_verify3+OTP_verify4);
        switch (Case){
            case "SU":{
                Toast.makeText(getApplicationContext(),"This number:",Toast.LENGTH_LONG);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                mContext = getApplicationContext();
                username=intent.getStringExtra("username");
                otp_su=intent.getStringExtra("otp");
                System.out.println(username+"............."+otp_su);
                System.out.println(username+".............."+otp_su);
                System.out.println(username+"............"+otp_su);
                System.out.println(username+".........."+otp_su);
                System.out.println(username+"............."+otp_su);

                //phone.setBackgroundResource(R.drawable.curve_button2);

                // phone.setText(ph.toString());
                // phone.setEnabled(false);

                otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final ProgressDialog progressDialog_SU=new ProgressDialog(OtpEnter.this);
                        progressDialog_SU.setMessage("Please wait, You are getting Registered");
                        progressDialog_SU.show();

                        OTP_verify=OTP_verify1.getText().toString()+OTP_verify2.getText().toString()+OTP_verify3.getText().toString()+OTP_verify4.getText().toString();

                        final String otp_to_beVerified=OTP_verify.toString();
                        System.out.println(otp_to_beVerified+".....................................................");
                        System.out.println("Inside on click...........");
                        if(otp_to_beVerified.equalsIgnoreCase(otp_su)) {
                            System.out.println("Inside on click if...........");
                            JSONObject params_create=new JSONObject();
                            try {
                                params_create.put("mobile_no",sess.get_phone());
                                params_create.put("username",username);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonRequest = new JsonObjectRequest(1,URL_CREATE_USER, params_create,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                VolleyLog.v("Response:%n %s", response.toString(4));
                                                progressDialog_SU.dismiss();
                                                sess.setLoggedin(true,username,ph);
                                                Intent Otp_SU = new Intent(OtpEnter.this, feedselect.class).putExtra("mobile_no",ph);
                                                startActivity(Otp_SU);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                progressDialog_SU.dismiss();
                                                System.out.println("JSON Error>>>>.....");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog_SU.dismiss();
                                    Toast.makeText(OtpEnter.this,"INVALID OTP!",Toast.LENGTH_SHORT).show();
                                }
                            });

                            MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);


                        }else {
                            progressDialog_SU.dismiss();
                            Toast.makeText(OtpEnter.this,"INVALID OTP!",Toast.LENGTH_SHORT).show();
                            System.out.println("If error.....................");
                        }
                    }
                });
                break;
            }
            case "SI":{
                final Intent in=getIntent();
                Toast.makeText(getApplicationContext(),"This number:",Toast.LENGTH_LONG);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                ph=in.getStringExtra("mobile_no");
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                System.out.println("This number......"+ph);
                //phone.setBackgroundColor(Color.BLACK);
                otp=findViewById(R.id.button);
                mContext = getApplicationContext();
                otp_si=intent.getStringExtra("otp");
                otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("otpotptopotpoopt"+OTP_verify1.getText().toString()+OTP_verify2.getText().toString()+OTP_verify3.getText().toString()+OTP_verify4.getText().toString());
                        ProgressDialog progressDialog_SI=new ProgressDialog(OtpEnter.this);
                        progressDialog_SI.setMessage("Please Wait, You are Been Verified!!!");
                        progressDialog_SI.show();
                        OTP_verify=OTP_verify1.getText().toString()+OTP_verify2.getText().toString()+OTP_verify3.getText().toString()+OTP_verify4.getText().toString();
                        final String otp_to_beVerified=OTP_verify;
                        System.out.println(otp_to_beVerified+".....................................................");
                        System.out.println("Inside on click...........");
                        if(otp_to_beVerified.equalsIgnoreCase(otp_si)) {
                            progressDialog_SI.dismiss();

                            JSONObject login=new JSONObject();
                            try{
                                login.put("mobile_no",ph);
                            }catch (JSONException e){
                                System.out.println("This error="+e.toString());
                            }
                            JsonObjectRequest jsonRequest = new JsonObjectRequest("http://api.artha.today:3000/login", login,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(getApplicationContext(),"Post added successfully",Toast.LENGTH_SHORT);
                                            try {
                                                JSONObject log=new JSONObject();
                                                log=response.getJSONObject("result");
                                                String user_logged=log.get("username").toString();
                                                sess.setLoggedin(true,user_logged,in.getStringExtra("mobile_no"));

                                                Intent i=new Intent(OtpEnter.this, BottomNavigate1.class);
                                                i.putExtra("mobile_no",ph);
                                                i.putExtra("selected_category","all");
                                                //Intent Otp_SU = new Intent().putExtra();
                                                startActivity(i);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(".............................................."+error.toString());
                                    Toast.makeText(getApplicationContext(),"Error! Try after sometime",Toast.LENGTH_SHORT);
                                }
                            });
                            MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);

                        }else {
                            progressDialog_SI.dismiss();
                            Toast.makeText(OtpEnter.this,"INVALID OTP!",Toast.LENGTH_SHORT).show();
                            System.out.println("If error.....................");
                        }
                    }
                });
                break;

            }
        }

    }

}