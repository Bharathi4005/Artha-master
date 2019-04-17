package com.jss.artha;
public class DataModel {


    public String name,id;
    boolean checked;
    int image_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }







    public DataModel(String name, String id, boolean checked, int image_id) {
        this.name = name;
        this.id = id;
        this.checked = checked;
        this.image_id = image_id;
    }





}
