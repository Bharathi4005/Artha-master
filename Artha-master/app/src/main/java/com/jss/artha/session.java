package com.jss.artha;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;
    public static String n;
    public static String e;
    public static String mobile;
    public static String category;
    public static String category1;
    public static String p;
    public session(Context ctx)
    {
        this.ctx=ctx;
        prefs=ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor=prefs.edit();
    }
    public void setLoggedin(boolean loggedin,String name,String phone){
        editor.putBoolean("loggedInmode",loggedin);
        editor.putString("name",name);
        editor.putString("mobile",phone);
        editor.commit();
    }
    public void set_city(String ci){
        editor.putString("city",ci);
        editor.commit();
    }
    public String get_c(){
        String m=prefs.getString("city","blr");
        return m;
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(n,prefs.getString(n,n));
        return user;
    }
    public void setdownloaded(boolean d) {
        editor.putBoolean("download",d);
        editor.commit();
    }
    public String getUser(){
        String m=prefs.getString("name","sexypaji");
        return m;
    }
    public String get_mobile(){
        String m=prefs.getString("mobile","");
        return m;
    }
    public void set_mobile(String m){
        editor.putString("phone",m);
        editor.commit();
    }
    public void set_first(int f){
        editor.putInt("first",f);
        editor.commit();
    }
    public int get_first(){
        int f=prefs.getInt("first",0);
        return f;
    }
    public String get_phone(){
        String m=prefs.getString("phone",null);
        return m;
    }
    public void set_home(boolean r){
        editor.putBoolean("athome",r);
    }
    public boolean get_home(){
        return  prefs.getBoolean("athome",false);
    }
    public void set_cat(int c){
        editor.putInt("stories",c);
    }
    public int get_cat(){
        int c=prefs.getInt("stories",0);
        return c;
    }
    public void set_cat1(String c){
        editor.putString(category1,c);
    }
    public String get_cat1(){
        String c=prefs.getString(category1,category1);
        return c;
    }
    public void set_fragment(int i){
        editor.putInt("frag",i);
        editor.commit();
    }
    public int get_fragment(){
        int i=prefs.getInt("frag",0);
        return i;
    }
    public boolean downloaded() {
        return  prefs.getBoolean("download",false);
    }
    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode",false);
    }
}

