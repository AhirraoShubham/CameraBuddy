package com.example.shubham.camerabuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shubham on 26-09-2017.
 */

public class PhotographerProfileActivity extends Activity {

    ListView listViewSinglePhotographer;
    ArrayList<MyPhotographers> arrayList;
    PhotographerProfileActivity.MyPhotographerAdapter myPhotographerAdapter;
    TextView tvEmail;
    ImageView imageViewLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photographer_profile_layout);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        Bundle bundle=getIntent().getExtras();
        String data1=bundle.getString("uemail");
        tvEmail.setText(data1);
        imageViewLogout=(ImageView)findViewById(R.id.imageViewLogout);
        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PhotographerProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        listViewSinglePhotographer=(ListView)findViewById(R.id.listViewSinglePhotographer);
        filListView();
    }

    private void filListView() {
        String url=Config.JSON_URL+"single_photographer.php";
        Log.d(Config.tag,"Insert URL : "+url);
        RequestQueue queue= Volley.newRequestQueue(PhotographerProfileActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responce) {
                try {
                    JSONArray jsonArray= new JSONArray(responce);
                    arrayList = new ArrayList<>();
                    PhotographerProfileActivity.MyPhotographerAdapter myPhotographerAdapter = new PhotographerProfileActivity.MyPhotographerAdapter(PhotographerProfileActivity.this,arrayList);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        MyPhotographers myPhotographers= new MyPhotographers(jsonObject);
                        myPhotographerAdapter.add(myPhotographers);
                    }
                    listViewSinglePhotographer.setAdapter(myPhotographerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(Config.tag, "JSONException : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(Config.tag,"Volley Error : "+volleyError.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private class MyPhotographerAdapter extends ArrayAdapter {
        Context context;
        public MyPhotographerAdapter(Context context, ArrayList<MyPhotographers> arrayList){
            super(context,0,arrayList);
            this.context=context;
        }
        @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            final MyPhotographers myPhotographers=(MyPhotographers)getItem(position);
            PhotographerProfileActivity.MyPhotographerAdapter.ViewHolder viewHolder;
            if (convertView==null){
                viewHolder = new PhotographerProfileActivity.MyPhotographerAdapter.ViewHolder();
                LayoutInflater layoutInflater=LayoutInflater.from(getContext());
                convertView=layoutInflater.inflate(R.layout.single_p_profile_layout,parent,false);
                viewHolder.tvPhotoName=(TextView)convertView.findViewById(R.id.tvPhotoName);
                viewHolder.tvPhotoRate=(TextView)convertView.findViewById(R.id.tvPhotoRate);
                viewHolder.tvPhotoCity=(TextView)convertView.findViewById(R.id.tvPhotoCity);

                viewHolder.tvPhotoLname=(TextView)convertView.findViewById(R.id.tvPhotoLname);
                viewHolder.tvPhotoMobile=(TextView)convertView.findViewById(R.id.tvPhotoMobile);
                viewHolder.tvPCameraType=(TextView)convertView.findViewById(R.id.tvPCameraType);
                viewHolder.tvPhotoEmail=(TextView)convertView.findViewById(R.id.tvPhotoEmail);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder=(PhotographerProfileActivity.MyPhotographerAdapter.ViewHolder)convertView.getTag();
            }
            viewHolder.tvPhotoName.setText("Name:- "+myPhotographers.p_fname+" "+myPhotographers.p_lname);
            viewHolder.tvPhotoRate.setText("Charges:- "+myPhotographers.p_rate);
            viewHolder.tvPhotoCity.setText("City:- "+myPhotographers.p_city);
            viewHolder.tvPhotoLname.setText(""+myPhotographers.p_lname);
            viewHolder.tvPhotoMobile.setText("Mobile :-"+myPhotographers.p_mobile);
            viewHolder.tvPCameraType.setText("Camera :-"+myPhotographers.cameratype);
            viewHolder.tvPhotoEmail.setText("Email :-"+myPhotographers.p_email);
            return convertView;
        }
        public class ViewHolder{
            TextView tvPhotoName,tvPhotoRate,tvPhotoCity,tvPhotoLname,tvPhotoMobile,tvPCameraType,tvPhotoEmail;
        }
    }
}