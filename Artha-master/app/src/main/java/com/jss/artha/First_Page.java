package com.jss.artha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;

import org.json.JSONException;
import org.json.JSONObject;

public class First_Page extends AppCompatActivity {
    Button otp,register;
    public EditText phone,username;
    //public String ph,user;
    public int p1,p2;
    public String URL_VALIDATE="http://api.artha.today:3000/validate/mobile";
    public String URL_VALIDATE_USERNAME="http://api.artha.today:3000/validate/username";
    public String URL_REGISTER_FOR_OTP="http://api.artha.today:3000/generateOtp";
    public Dialog myDialog;
    public Button ok;
    public String OTP_SU,OTP_SI;
    private Context mContext;
    public String Case;
    public Button Login;
    public int p1_login;
    public ImageButton phonecorrect,usercorrect1;
    public static int phone_valid_flag=0,username_valid_flag=0;
    private Button privacyPolicy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent=getIntent();
        Case=intent.getStringExtra("case");
        switch (Case){
            case "SU":{
                setContentView(R.layout.activity_main);
                usercorrect1=findViewById(R.id.usercorrect11);
                phonecorrect=findViewById(R.id.phonecorrect);
                p1=0;
                p2=0;
                myDialog = new Dialog(this);
                myDialog.setContentView(R.layout.alert);
                otp=findViewById(R.id.button);
                phone=findViewById(R.id.phone1);
                username=findViewById(R.id.username);
                ok=myDialog.findViewById(R.id.ok);
                //otp.setBackgroundColor(Color.RED);
                otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(generateOTP()+".......result");
                        if(generateOTP()==1) {
//                            Intent inte=new Intent(First_Page.this, OtpEnter.class);
//                            inte.putExtra("case","SU");
//                            inte.putExtra("phone",phone.getText().toString());
//                            startActivity(inte);
                            mContext = getApplicationContext();
                            JSONObject params=new JSONObject();
                            try {
                                params.put("mobile_no",phone.getText().toString());
                                params.put("case","su");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int f=generateOTP();
                                JsonObjectRequest jsonRequest = new JsonObjectRequest(URL_REGISTER_FOR_OTP, params,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                                    OTP_SU = response.getString("otp");
                                                    Intent Otp_SU = new Intent(First_Page.this, OtpEnter.class);
                                                    Otp_SU.putExtra("username", username.getText().toString());
                                                    Otp_SU.putExtra("mobile_no", phone.getText().toString());
                                                    Otp_SU.putExtra("case", "SU");
                                                    Otp_SU.putExtra("otp", OTP_SU);
                                                    startActivity(Otp_SU);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        myDialog.show();
                                        ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                myDialog.dismiss();
                                                Intent inte = new Intent(First_Page.this, OtpEnter.class);
                                                inte.putExtra("case", "SU");
                                                //inte.putExtra("phone",phone.getText().toString());
                                                startActivity(inte);
                                            }
                                        });
                                    }
                                });

                                MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
                            }
                        else {
                            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            myDialog.show();
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myDialog.dismiss();
                                    startActivity(new Intent(First_Page.this,First_Page.class).putExtra("case","SU"));
                                }
                            });
                        }
                    }
                });

                phone.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {

                        String phone_no=phone.getText().toString();
                        if(phone_no.isEmpty() || phone_no.length()!=10){
                            phone.setError("please enter 10 digit phone number");
                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        System.out.println("count="+count);
                        System.out.println(phone.getText().length());
                        mContext=getApplicationContext();
                        phonecorrect.setVisibility(View.VISIBLE);
                        if(phone.getText().length()==10){
                            final ProgressDialog progressDialog_SU=new ProgressDialog(First_Page.this);
                            progressDialog_SU.setMessage("Please Wait, Your Phone Number is been Verified!!!");
                            progressDialog_SU.show();
//                            p1=1;
//                            verify();
                            JSONObject phone_check=new JSONObject();
                            try {
                                phone_check.put("mobile_no",phone.getText().toString());
                                JsonObjectRequest phone_validate=new JsonObjectRequest(Request.Method.POST,URL_VALIDATE, phone_check,
                                        new Response.Listener<JSONObject>() {
                                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    System.out.println(".......Success");
                                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                                    System.out.println(response.toString());
                                                    JSONObject res=response.getJSONObject("result");
                                                    System.out.println(res.getString("status"));
                                                    if(res.getString("status").equals("0")){
                                                           phone_valid_flag=1;
                                                        phonecorrect.setImageDrawable(getResources().getDrawable(
                                                                R.drawable.tick));
                                                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_green_dark));

                                                        progressDialog_SU.dismiss();
                                                        Toast.makeText(First_Page.this,"phone number available",Toast.LENGTH_SHORT).show();

                                                    }
                                                    else
                                                    {

                                                        phone_valid_flag=0;
                                                        phonecorrect.setImageDrawable(getResources().getDrawable(
                                                                R.drawable.close));
                                                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_dark));

                                                        progressDialog_SU.dismiss();
                                                        Toast.makeText(First_Page.this, "Phone Number Already Registered!!", Toast.LENGTH_SHORT).show();

                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    System.out.println(".........Error......");
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        phone_valid_flag=0;
                                        phonecorrect.setImageDrawable(getResources().getDrawable(
                                                R.drawable.close));
                                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_dark));

                                        progressDialog_SU.dismiss();
                                        Toast.makeText(First_Page.this,"Error Connecting to Server",Toast.LENGTH_SHORT).show();

                                        System.out.println(error.toString()+"this......");
                                    }
                                });
                                MySingleton.getInstance(mContext).addToRequestQueue(phone_validate);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            p1=0;
                            phonecorrect.setImageDrawable(getResources().getDrawable(
                                    R.drawable.close));
                            phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_light));
                            phone_valid_flag=0;
                            //otp.setBackgroundColor(Color.RED);
                            //Toast.makeText(First_Page.this, "Please Enter an 10 digit Phone number !!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                username.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {


                        String user=username.getText().toString();
                        if(user.isEmpty() || user.length()<8){
                            username.setError("please enter atleast 8 characters username");
                        }

                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        System.out.println("count="+count);
                        System.out.println(username.getText().length());
                        mContext=getApplicationContext();
                        usercorrect1.setVisibility(View.VISIBLE);
                        if(username.getText().length()>=8){
                            final ProgressDialog progressDialog_SU=new ProgressDialog(First_Page.this);
                           // progressDialog_SU.setMessage("Please Wait, Your Username is been Verified!!!");
                           // progressDialog_SU.show();
                            usercorrect1.setVisibility(View.VISIBLE);
//                            p2=1;
//                            verify();
                            JSONObject user_check=new JSONObject();
                            try {
                                user_check.put("username",username.getText().toString());
                                JsonObjectRequest username_validate=new JsonObjectRequest(Request.Method.POST,URL_VALIDATE_USERNAME, user_check,
                                        new Response.Listener<JSONObject>() {
                                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    System.out.println(".......Success");
                                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                                    System.out.println(response.toString());
                                                    JSONObject res=response.getJSONObject("result");
                                                    System.out.println(res.getString("status"));
                                                    if(res.getString("status").contains("0")){

                                                        username_valid_flag=1;
                                                        usercorrect1.setImageDrawable(getResources().getDrawable(
                                                                R.drawable.tick));
                                                        usercorrect1.setImageTintList(getResources().getColorStateList(android.R.color.holo_green_dark));

                                                     //   progressDialog_SU.dismiss();
                                                        Toast.makeText(First_Page.this,"Username available",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        username_valid_flag=0;
                                                        usercorrect1.setImageDrawable(getResources().getDrawable(
                                                                R.drawable.close));
                                                        usercorrect1.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_dark));

                                                      //  progressDialog_SU.dismiss();
                                                        Toast.makeText(First_Page.this,"Username not available, Please enter different username",Toast.LENGTH_SHORT).show();

                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    System.out.println(".........Error......");
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        username_valid_flag=0;
                                        usercorrect1.setImageDrawable(getResources().getDrawable(
                                                R.drawable.close));
                                        usercorrect1.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_dark));

                                      //  progressDialog_SU.dismiss();
                                        Toast.makeText(First_Page.this,"Error while Connecting to server",Toast.LENGTH_SHORT).show();

                                        System.out.println(error.toString()+"this......");
                                    }
                                });
                                MySingleton.getInstance(mContext).addToRequestQueue(username_validate);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            p2=0;
                            //Toast.makeText(First_Page.this,"Please Enter atleast 8 characters long Username!!! ",Toast.LENGTH_SHORT).show();
                            usercorrect1.setImageDrawable(getResources().getDrawable(
                                    R.drawable.close));
                            usercorrect1.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_dark));
                           // otp.setBackgroundColor();
                        }
                    }
                });
                break;
            }

//            case "SI":{
//
//                setContentView(R.layout.activity_login);
//                p1_login=0;
//                phonecorrect=findViewById(R.id.phonecorrect);
//                phone=findViewById(R.id.phone1);
//                Login=findViewById(R.id.button);
//               // Login.setBackgroundColor(Color.RED);
//                myDialog = new Dialog(this);
//                myDialog.setContentView(R.layout.alert);
//                ok=myDialog.findViewById(R.id.ok);
//                Login.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (p1_login==1) {
//                            mContext = getApplicationContext();
//                            JSONObject params=new JSONObject();
//                            try {
//                                params.put("mobile_no",phone.getText().toString());
//                                params.put("case","si");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            JsonObjectRequest jsonRequest = new JsonObjectRequest(URL_REGISTER_FOR_OTP, params,
//                                    new Response.Listener<JSONObject>() {
//                                        @Override
//                                        public void onResponse(JSONObject response) {
//                                            try {
//                                                VolleyLog.v("Response:%n %s", response.toString(4));
//                                                OTP_SI = response.getString("otp");
//                                                Intent Otp_SI = new Intent(First_Page.this, OtpEnter.class);
//                                                Otp_SI.putExtra("mobile_no", phone.getText().toString());
//                                                Otp_SI.putExtra("case", "SI");
//                                                Otp_SI.putExtra("otp", OTP_SI);
//                                                startActivity(Otp_SI);
//
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(First_Page.this,"INVALID OTP",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
//                        }
//                        else {
//                            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            myDialog.show();
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    myDialog.dismiss();
//                                    startActivity(new Intent(First_Page.this,First_Page.class).putExtra("case","SI"));
//                                }
//                            });
//                        }
//                    }
//                });
//                phone.addTextChangedListener(new TextWatcher() {
//
//                    public void afterTextChanged(Editable s) {
//
//                        String phone_no=phone.getText().toString();
//                        if(phone_no.isEmpty() || phone_no.length()!=10){
//                            phone.setError("please enter 10 digit phone number");
//                        }
//
//                    }
//
//                    public void beforeTextChanged(CharSequence s, int start,
//                                                  int count, int after) {
//                    }
//
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    public void onTextChanged(CharSequence s, int start,
//                                              int before, int count) {
//                        phonecorrect.setVisibility(View.VISIBLE);
//                        System.out.println("count="+count);
//                        System.out.println(phone.getText().length());
//                        mContext=getApplicationContext();
//                        if(phone.getText().length()==10){
//                            final ProgressDialog progressDialog_SI=new ProgressDialog(First_Page.this);
//                            progressDialog_SI.setMessage(" Please Wait, phone number is been verified!!!");
//                            progressDialog_SI.show();
////                            phonecorrect.setImageDrawable(getResources().getDrawable(
////                                    R.drawable.tick));
////                            phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
////                            p1=1;
////                            verify_login();
//                            JSONObject phone_check=new JSONObject();
//                            try {
//                                phone_check.put("mobile_no",phone.getText().toString());
//                                JsonObjectRequest phone_validate=new JsonObjectRequest(Request.Method.POST,URL_VALIDATE, phone_check,
//                                        new Response.Listener<JSONObject>() {
//                                            @Override
//                                            public void onResponse(JSONObject response) {
//                                                try {
//                                                    System.out.println(".......Success");
//                                                    VolleyLog.v("Response:%n %s", response.toString(4));
//                                                    System.out.println(response.toString());
//                                                    JSONObject res=response.getJSONObject("result");
//                                                    System.out.println(res.getString("status"));
//                                                    if(res.getString("status").equals("0")){
//                                                        p1_login=0;
//
//                                                        phonecorrect.setImageDrawable(getResources().getDrawable(
//                                                                R.drawable.close));
//                                                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_light));
//                                                        Login.setBackgroundColor(Color.RED);
//
//                                                        progressDialog_SI.dismiss();
//                                                        Toast.makeText(First_Page.this,"phone number not registered",Toast.LENGTH_SHORT).show();
//
//                                                    }
//                                                    else {
//
//                                                        phonecorrect.setImageDrawable(getResources().getDrawable(
//                                                                R.drawable.tick));
//                                                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
////                                                        p1=1;
////                                                        verify_login();
//                                                        p1_login=1;
//
//                                                        progressDialog_SI.dismiss();
//                                                        Toast.makeText(First_Page.this,"valid phone number",Toast.LENGTH_SHORT).show();
//
//                                                    }
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                    System.out.println(".........Error......");
//                                                }
//                                            }
//                                        }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
//                                        p1_login=0;
//
//                                        phonecorrect.setImageDrawable(getResources().getDrawable(
//                                                R.drawable.close));
//                                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_light));
//                                       // Login.setBackgroundColor(Color.RED);
//
//                                        progressDialog_SI.dismiss();
//                                        Toast.makeText(First_Page.this,"error connecting with server",Toast.LENGTH_SHORT).show();
//
//                                        System.out.println(error.toString()+"this......");
//                                    }
//                                });
//                                MySingleton.getInstance(mContext).addToRequestQueue(phone_validate);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        else {
//                            p1_login=0;
//                           // Toast.makeText(First_Page.this,"Please Enter an valid 10 digit phone number!!!",Toast.LENGTH_SHORT).show();
//
//                            phonecorrect.setImageDrawable(getResources().getDrawable(
//                                    R.drawable.close));
//                            phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_light));
//                           // Login.setBackgroundColor(Color.RED);
//                        }
//                    }
//                });
//
//
//                break;}
        }


    }

    public int generateOTP(){
        if(phone_valid_flag==1&&username_valid_flag==1){
            return 1;
        }
        return 0;
    }
}
