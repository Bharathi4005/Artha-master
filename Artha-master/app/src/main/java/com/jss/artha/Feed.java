package com.jss.artha;

import java.util.HashMap;

public class Feed {


    private String mobile_no;
    private String flag;
    private HashMap<String,String> category;


    public Feed(String mobile_no, String flag, HashMap<String, String> category) {
        this.mobile_no = mobile_no;
        this.flag = flag;
        this.category = category;
    }



    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public HashMap<String, String> getCategory() {
        return category;
    }

    public void setCategory(HashMap<String, String> category) {
        this.category = category;
    }




}
