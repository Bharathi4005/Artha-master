package com.jss.artha;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class User_Profile extends AppCompatActivity {
    public String URL_UPDATE_PROFILE = "http://api.artha.today:3000/user/update";
    private Context mContext;
    public EditText firstname, lastname, dob, country, zip, email, district;
    public ArrayList<String> stateList = new ArrayList<>();
    public ArrayList<String> cityList = new ArrayList<>();
    public ArrayList<String> genderlist = new ArrayList<>();
    public Spinner gender;
    public String stateUrl = "http://api.artha.today:3000/getState";
    public String cityUrl = "http://api.artha.today:3000/getCity?state=";
    public String UserFetchUrl = "http://api.artha.today:3000/user/fetch";
    public Spinner city, state;
    public Calendar myCalendar;
    public static String stateSelected = new String();
    public static String citySelected = new String();
    public static String genderSelected = new String();

    public session sess;
    public Button save;
    public ImageView back;
    public String mob;
    private TextView userName, mobileNumber;
    private String mobile_no, username, cityName, countryName, dateOfBirth, email_id, firstName, genderType = new String(), lastName, stateName, zipCode, districtName;


    private String blockCharacterSet = "`~!@#$%^&*()_-+={[}]:;\"\'<,>.?/0123456789";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait, Loading Profile ");
        progressDialog.show();
        back=(ImageView) findViewById(R.id.backb);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        gender = (Spinner) findViewById(R.id.gender);
        dob = (EditText) findViewById(R.id.dob);
        email = findViewById(R.id.email);

        //  country = (EditText) findViewById(R.id.country);
        zip = (EditText) findViewById(R.id.zipcode);
        save = (Button) findViewById(R.id.save);

        userName = findViewById(R.id.username);
        mobileNumber = findViewById(R.id.mobile);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BottomNavigate1.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
        //  district = findViewById(R.id.district);

        firstname.setFilters(new InputFilter[] { filter });
        lastname.setFilters(new InputFilter[] { filter });

        stateSelected = new String();
        citySelected = new String();
        genderSelected = new String();


        final GlobalClass globalClass = (GlobalClass) getApplicationContext();


        mContext = getApplicationContext();
        sess = new session(this);
        mob = sess.get_mobile();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("mobile_no", mob);
        } catch (Exception e) {

        }

        myCalendar = Calendar.getInstance();

        dob = (EditText) findViewById(R.id.dob);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                // myCalendar.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                myCalendar.set(Calendar.YEAR,year);
                if(year>2018)
                {
                    Toast.makeText(User_Profile.this, "Give valid Date of Birth", Toast.LENGTH_SHORT).show();


                }
                if(year<2019) {
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }


            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(User_Profile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                //DatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            }
        });

        JsonObjectRequest jsonRequest1 = new JsonObjectRequest(UserFetchUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("REsponse successful........................................" + response.toString());
                            System.out.println("REsponse successful........................................" + response.toString());
                            System.out.println("REsponse successful........................................" + response.toString());
                            VolleyLog.v("Response:%n %s", response.toString(4));


                            firstName = response.getString("firstname");
                            lastName = response.getString("lastname");
                            username = response.getString("username");
                            mobile_no = response.getString("mobile_no");
                            email_id = response.getString("email");
                            genderType = response.getString("gender");
                            dateOfBirth = response.getString("dob");
                            cityName = response.getString("city");
                            // districtName = response.getString("district");
                            stateName = response.getString("state");
                            countryName = response.getString("country");
                            zipCode = response.getString("zipcode");


                            firstname.setText(firstName);
                            lastname.setText(lastName);
                            userName.setText(username);
                            mobileNumber.setText(mobile_no);
                            email.setText(email_id);
                            dob.setText(dateOfBirth);
                            //  district.setText(districtName);
                            zip.setText(zipCode);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("JSON error/......................................");
                        } finally {
                            try {


                                cityName = response.getString("city");
                                stateName = response.getString("state");
                                genderType = response.getString("gender");


                                if (genderType.isEmpty()) {
                                    genderlist.clear();
                                    genderlist.add("---Select Gender---");
                                    genderlist.add("MALE");
                                    genderlist.add("FEMALE");
                                    genderlist.add("OTHERS");


                                    gender = findViewById(R.id.gender);


                                    final ArrayAdapter<String> adapter_gender = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, genderlist);


                                    adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    gender.setAdapter(adapter_gender);


                                    int index = genderlist.indexOf(genderType);

                                    gender.setSelection(index);


                                    adapter_gender.notifyDataSetChanged();

                                } else {
                                    genderlist.clear();
                                    genderlist.add("Male".toUpperCase());
                                    genderlist.add("Female".toUpperCase());
                                    genderlist.add("OTHERS".toUpperCase());

                                    gender = findViewById(R.id.gender);


                                    final ArrayAdapter<String> adapter_gender = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, genderlist);


                                    adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    gender.setAdapter(adapter_gender);


                                    int index = genderlist.indexOf(genderType.toUpperCase());

                                    gender.setSelection(index);

                                    adapter_gender.notifyDataSetChanged();

                                }

                                gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                                               int position, long id) {
                                        Object item = adapterView.getItemAtPosition(position);
                                        if (item != null) {

                                            Object SElect = adapterView.getItemAtPosition(position);

                                            genderSelected = SElect.toString();




                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                if (citySelected.isEmpty()) {
                                    cityList.clear();
                                    cityList.add("---Select City---");
                                    cityList.add("  AHEMEDABAD");
                                    cityList.add("  BANGALORE");
                                    cityList.add("  chennai".toUpperCase());
                                    cityList.add("  delhi".toUpperCase());
                                    cityList.add("  hyderabad".toUpperCase());
                                    cityList.add("  kolkata".toUpperCase());
                                    cityList.add("  mumbai".toUpperCase());
                                    cityList.add("  pune".toUpperCase());


                                    city = findViewById(R.id.city);
                                    final ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cityList);


                                    adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    city.setAdapter(adapter_city);


                                    int index = cityList.indexOf(cityName);

                                    city.setSelection(index);

                                    adapter_city.notifyDataSetChanged();

                                } else {
                                    cityList.clear();
                                    cityList.add("  AHEMEDABAD");
                                    cityList.add("  BANGALORE");
                                    cityList.add("  chennai".toUpperCase());
                                    cityList.add("  delhi".toUpperCase());
                                    cityList.add("  hyderabad".toUpperCase());
                                    cityList.add("  kolkata".toUpperCase());
                                    cityList.add("  mumbai".toUpperCase());
                                    cityList.add("  pune".toUpperCase());

                                    city = findViewById(R.id.city);
                                    final ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cityList);


                                    adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    city.setAdapter(adapter_city);


                                    int index = cityList.indexOf(cityName);

                                    city.setSelection(index);


                                    adapter_city.notifyDataSetChanged();

                                }


                                city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                                               int position, long id) {
                                        Object item = adapterView.getItemAtPosition(position);
                                        if (item != null) {

                                            Object item1 = adapterView.getItemAtPosition(position);

                                            citySelected = item1.toString();

                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                                JsonObjectRequest jsonRequest = new JsonObjectRequest(stateUrl, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    System.out.println("REsponse successful........................................" + response.toString());
                                                    System.out.println("REsponse successful........................................" + response.toString());
                                                    System.out.println("REsponse successful........................................" + response.toString());
                                                    VolleyLog.v("Response:%n %s", response.toString(4));


                                                    JSONArray jsonArray = response.getJSONArray("state");

                                                    if (stateSelected.isEmpty()) {
                                                        stateList.clear();
                                                        stateList.add("---Select State---");
                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                            stateList.add(jsonArray.getString(i).toUpperCase());
                                                        }


                                                        state = findViewById(R.id.state);
                                                        final ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stateList);


                                                        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        state.setAdapter(adapter_state);


                                                        int index = stateList.indexOf(stateName);

                                                        state.setSelection(index);

                                                        adapter_state.notifyDataSetChanged();


                                                    } else {
                                                        stateList.clear();
                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                            stateList.add(jsonArray.getString(i).toUpperCase());
                                                        }


                                                        state = findViewById(R.id.state);
                                                        final ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stateList);


                                                        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        state.setAdapter(adapter_state);


                                                        int index = stateList.indexOf(stateName);

                                                        state.setSelection(index);

                                                        adapter_state.notifyDataSetChanged();

                                                    }


                                                    state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                                                   int position, long id) {
                                                            Object item = adapterView.getItemAtPosition(position);
                                                            if (item != null) {

                                                                Object item1 = adapterView.getItemAtPosition(position);
                                                                stateSelected = item1.toString();
                                                            }

                                                        }

                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> parent) {

                                                        }
                                                    });

                                                    progressDialog.dismiss();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    System.out.println("JSON error/......................................");
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        System.out.println(".............................................." + error.toString());

                                    }
                                }

                                );
                                MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);


                            } catch (Exception e) {

                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(".............................................." + error.toString());
            }
        }

        );
        MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest1);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(firstname.getText().toString().isEmpty() || firstname.getText().toString().length() < 4 || lastname.getText().toString().isEmpty() || gender.getSelectedItem().toString().isEmpty() || genderSelected.equalsIgnoreCase("---Select Gender---") || citySelected.equalsIgnoreCase("---Select City---") || stateSelected.equalsIgnoreCase("---Select State---") || dob.getText().toString().isEmpty() || email.getText().toString().isEmpty() || city.getSelectedItem().toString().isEmpty() || state.getSelectedItem().toString().isEmpty()  || zip.getText().toString().isEmpty() || zip.getText().toString().length() != 6)) {

                    JSONObject params = new JSONObject();
                    try {
                        params.put("mobile_no", mob);
                        params.put("firstname", firstname.getText().toString());
                        params.put("lastname", lastname.getText().toString());
                        params.put("gender", genderSelected.toString());
                        params.put("dob", dob.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("city", citySelected);
                        params.put("state", stateSelected);
                        // params.put("district", district.getText().toString());
                        params.put("country", "India");
                        params.put("zipcode", zip.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonRequest = new JsonObjectRequest(URL_UPDATE_PROFILE, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        System.out.println("REsponse successful........................................");
                                        VolleyLog.v("Response:%n %s", response.toString(4));
                                        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(User_Profile.this, BottomNavigate1.class);
                                        i.putExtra("mobile_no", mob);
                                        i.putExtra("selected_category", "all");
                                        startActivity(i);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Try Later", Toast.LENGTH_SHORT);
                                        System.out.println("JSON error/......................................");
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(".............................................." + error.toString());

                        }
                    }

                    );
                    MySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);


                } else {

                    if (zip.getText().toString().length() != 6) {
                        zip.setError(" Zip code must be 6 digits long !!!");

                    }
                    if (firstname.getText().toString().length() < 4) {
                        firstname.setError("Firstname must be atleast 4 characters long !!!");
                    }

                    if (genderSelected.equalsIgnoreCase("---Select Gender---")) {
                        TextView textView = (TextView) gender.getSelectedView();
                        textView.setError("");
                        textView.setTextColor(Color.RED);
                        textView.setText("Please Select Gender");
                    }

                    if (citySelected.equalsIgnoreCase("---Select City---")) {
                        TextView textView = (TextView) city.getSelectedView();
                        textView.setError("");
                        textView.setTextColor(Color.RED);
                        textView.setText("Please Select City");
                    }

                    if (stateSelected.equalsIgnoreCase("---Select State---")) {
                        TextView textView = (TextView) state.getSelectedView();
                        textView.setError("");
                        textView.setTextColor(Color.RED);
                        textView.setText("Please Select State");
                    }


                    if ((firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || gender.getSelectedItem().toString().isEmpty() || genderSelected.equalsIgnoreCase("---Select Gender---") || citySelected.equalsIgnoreCase("---Select City---") || stateSelected.equalsIgnoreCase("---Select State---") || dob.getText().toString().isEmpty() || email.getText().toString().isEmpty() || city.getSelectedItem().toString().isEmpty() || state.getSelectedItem().toString().isEmpty()  || zip.getText().toString().isEmpty())) {


                        Toast.makeText(User_Profile.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
    }

}