package com.example.shubham.camerabuddy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shubham on 21-09-2017.
 */

public class MyPhotographers {

    public String p_fname="";
    public String p_lname="";
    public String p_email="";
    public String p_mobile="";
    public String p_rate="";
    public String p_city="";
    public String cameratype="";
    public MyPhotographers(JSONObject jsonObject){
        try {
            this.p_fname=jsonObject.getString("p_fname");
            this.p_lname=jsonObject.getString("p_lname");
            this.p_email=jsonObject.getString("p_email");
            this.p_mobile=jsonObject.getString("p_mobile");
            this.p_rate=jsonObject.getString("p_rate");
            this.p_city=jsonObject.getString("p_city");
            this.cameratype=jsonObject.getString("cameratype");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
