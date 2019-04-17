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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artha.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public Button otp, login;
    public session sess;
    public Button ok;
    public String OTP_SU,OTP_SI;
    private Context mContext;
    public String Case;
    public Button Login;

    public EditText phone;
    public int p1,p2;
    public String URL_VALIDATE="http://api.artha.today:3000/validate/mobile";
    public String URL_VALIDATE_USERNAME="http://api.artha.today:3000/validate/username";
    public String URL_REGISTER_FOR_OTP="http://api.artha.today:3000/generateOtp";
    public Dialog myDialog;

    public int p1_login;
    public ImageButton phonecorrect,usercorrect1;
    public static int phone_valid_flag=0,username_valid_flag=0;
    private Button privacyPolicy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__page);
        login = findViewById(R.id.login);
        phone = findViewById(R.id.phone1);
        Login = findViewById(R.id.button);
        otp = findViewById(R.id.signup);


        privacyPolicy=findViewById(R.id.privacy);


        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PrivacyPolicy.class));
            }
        });


        p1_login = 0;
        myDialog = new Dialog(this);
        sess = new session(this);

        if(sess.loggedin()) {
            Intent in=new Intent(MainActivity.this, BottomNavigate1.class);
            in.putExtra("selected_category", "All");
            in.putExtra("selected_trend","def");
            startActivity(in);}
        if (!sess.loggedin()) {
            otp = findViewById(R.id.signup);
            otp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, First_Page.class).putExtra("case", "SU"));
                }
            });
            // login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                  //  setContentView(R.layout.activity_first__page);
//                    p1_login=0;
//
//                    myDialog.setContentView(R.layout.alert);
//                    ok=myDialog.findViewById(R.id.ok);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.setContentView(R.layout.alert);
                    ok = myDialog.findViewById(R.id.ok);
                    if (p1_login == 1) {
                        mContext = getApplicationContext();
                        JSONObject params = new JSONObject();
                        try {
                            params.put("mobile_no", phone.getText().toString());
                            params.put("case", "si");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(URL_REGISTER_FOR_OTP, params,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            VolleyLog.v("Response:%n %s", response.toString(4));
                                            OTP_SI = response.getString("otp");
                                            Intent Otp_SI = new Intent(MainActivity.this, OtpEnter.class);
                                            Otp_SI.putExtra("mobile_no", phone.getText().toString());
                                            Otp_SI.putExtra("case", "SI");
                                            Otp_SI.putExtra("otp", OTP_SI);
                                            startActivity(Otp_SI);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "INVALID OTP", Toast.LENGTH_SHORT).show();
                            }
                        });

                        MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
                    } else {
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startActivity(new Intent(MainActivity.this, MainActivity.class).putExtra("case", "SI"));
                                myDialog.dismiss();
                            }
                        });
                    }
                }
            });
            phone.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {

                    String phone_no = phone.getText().toString();
                    if (phone_no.isEmpty() || phone_no.length() != 10) {
                        phone.setError("please enter 10 digit phone number");
                    }

                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    System.out.println("count=" + count);
                    System.out.println(phone.getText().length());
                    mContext = getApplicationContext();
                    if (phone.getText().length() == 10) {
                        final ProgressDialog progressDialog_SI = new ProgressDialog(MainActivity.this);
                        progressDialog_SI.setMessage(" Please Wait, phone number is been verified!!!");
                        progressDialog_SI.show();
//                            phonecorrect.setImageDrawable(getResources().getDrawable(
//                                    R.drawable.tick));
//                            phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
//                            p1=1;
//                            verify_login();
                        JSONObject phone_check = new JSONObject();
                        try {
                            phone_check.put("mobile_no", phone.getText().toString());
                            JsonObjectRequest phone_validate = new JsonObjectRequest(Request.Method.POST, URL_VALIDATE, phone_check,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                System.out.println(".......Success");
                                                VolleyLog.v("Response:%n %s", response.toString(4));
                                                System.out.println(response.toString());
                                                JSONObject res = response.getJSONObject("result");
                                                System.out.println(res.getString("status"));
                                                if (res.getString("status").equals("0")) {
                                                    p1_login = 0;


                                                   // Login.setBackgroundColor(Color.RED);

                                                    progressDialog_SI.dismiss();
                                                    Toast.makeText(MainActivity.this, "phone number not registered", Toast.LENGTH_SHORT).show();

                                                } else {

//                                                    phonecorrect.setImageDrawable(getResources().getDrawable(
//                                                            R.drawable.tick));
//                                                    phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
//                                                        p1=1;
//                                                        verify_login();
                                                    p1_login = 1;

                                                    progressDialog_SI.dismiss();
                                                    Toast.makeText(MainActivity.this, "valid phone number", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                System.out.println(".........Error......");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    p1_login = 0;

//                                    phonecorrect.setImageDrawable(getResources().getDrawable(
//                                            R.drawable.close));
//                                    phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_light));
                                    // Login.setBackgroundColor(Color.RED);

                                    progressDialog_SI.dismiss();
                                    Toast.makeText(MainActivity.this, "error connecting with server", Toast.LENGTH_SHORT).show();

                                    System.out.println(error.toString() + "this......");
                                }
                            });
                            MySingleton.getInstance(mContext).addToRequestQueue(phone_validate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        p1_login = 0;
                        // Toast.makeText(First_Page.this,"Please Enter an valid 10 digit phone number!!!",Toast.LENGTH_SHORT).show();
//
//                        phonecorrect.setImageDrawable(getResources().getDrawable(
//                                R.drawable.close));
//                        phonecorrect.setImageTintList(getResources().getColorStateList(android.R.color.holo_red_light));
                        // Login.setBackgroundColor(Color.RED);
                    }
                }
            });

            // startActivity(new Intent(MainActivity.this, First_Page.class).putExtra("case", "SI"));
        }
        //  });

        else {
            Intent in=new Intent(MainActivity.this, BottomNavigate1.class);
            in.putExtra("selected_category", "All");
            in.putExtra("selected_trend","def");
            startActivity(in);
        }
    }
    public int generateOTP(){
        if(phone_valid_flag==1&&username_valid_flag==1){
            return 1;
        }
        return 0;
    }

    }

