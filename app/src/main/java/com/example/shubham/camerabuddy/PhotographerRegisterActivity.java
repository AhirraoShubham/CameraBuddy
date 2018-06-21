package com.example.shubham.camerabuddy;

import
        android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

/**
 * Created by Shubham on 19-09-2017.
 */

public class PhotographerRegisterActivity extends Activity implements AdapterView.OnItemSelectedListener {
    EditText etPFname,etPLname,etPEmail,etPMobileno,etPPassword,etPConfPassword;
    Button btnNext;
    TextView tvPNewUser;
    Spinner spinnerPCity;
    String spinnerpcity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photographer_register_layout);
        init();
    }

    private void init() {
        etPFname=(EditText)findViewById(R.id.etPFname);
        etPLname=(EditText)findViewById(R.id.etPLname);
        etPEmail=(EditText)findViewById(R.id.etPEmail);
        etPMobileno=(EditText)findViewById(R.id.etPMobileno);
        etPPassword=(EditText)findViewById(R.id.etPPassword);
        etPConfPassword=(EditText)findViewById(R.id.etPConfPassword);
        spinnerPCity=(Spinner)findViewById(R.id.spinnerPCity);
        spinnerPCity.setOnItemSelectedListener(this);
        tvPNewUser=(TextView)findViewById(R.id.tvPNewUser);
        btnNext=(Button)findViewById(R.id.btnNext);
        tvPNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PhotographerRegisterActivity.this,PhotographerLoginActivity.class);
                startActivity(intent);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn()==true) {
                    if(etPFname.getText().toString().trim().isEmpty()||etPLname.getText().toString().trim().isEmpty()||etPEmail.getText().toString().trim().isEmpty()||etPMobileno.getText().toString().trim().isEmpty()||etPPassword.getText().toString().trim().isEmpty()||etPConfPassword.getText().toString().trim().equals(etPPassword)){
                        etPFname.setError("Field Can't be Empty..!");
                        etPFname.requestFocus();
                        etPLname.setError("Field Can't be Empty..!");
                        etPLname.requestFocus();
                        etPEmail.setError("Field Can't be Empty..!");
                        etPEmail.requestFocus();
                        etPMobileno.setError("Field Can't be Empty..!");
                        etPMobileno.requestFocus();
                        etPPassword.setError("Field Can't be Empty..!");
                        etPPassword.requestFocus();
                        etPConfPassword.setError("Password Doesn't Match.!");
                        etPConfPassword.requestFocus();
                     //   finish();
                    }
                    else {
                        final ProgressDialog loading = ProgressDialog.show(PhotographerRegisterActivity.this,"Loading...","Please wait...",false,false);
                        String url=Config.JSON_URL+"photographer_regitration.php?&p_fname="+etPFname.getText().toString().trim()+"&p_lname="+etPLname.getText().toString().trim()+"&p_email="+etPEmail.getText().toString().trim()+"&p_mobile="+etPMobileno.getText().toString().trim()+"&p_city="+spinnerPCity.getSelectedItem().toString().trim()+"&p_password="+etPPassword.getText().toString().trim();
                        Log.d(Config.tag,"Insert URL : "+url);
                        RequestQueue requestQueue= Volley.newRequestQueue(PhotographerRegisterActivity.this);
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String responce) {
                                try {
                                    JSONArray jsonArray=new JSONArray(responce);
                                    JSONObject jsonObject=new JSONObject(jsonArray.get(0).toString());
                                    boolean result=jsonObject.getBoolean("result");
                                    String reason=jsonObject.getString("reason");
                                    if (result){
                                        Log.d(Config.tag,"True :"+result);
                                        String email=etPEmail.getText().toString();
                                        Intent intent=new Intent(PhotographerRegisterActivity.this,PhotoUploadActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putString("uemail",email);
                                        intent.putExtras(bundle);
                                        loading.dismiss();
                                        Toast.makeText(PhotographerRegisterActivity.this, "Done..!", Toast.LENGTH_SHORT).show();
                                        etPFname.setText("");
                                        etPLname.setText("");
                                       // etPEmail.setText("");
                                        etPMobileno.setText("");
                                        etPPassword.setText("");
                                        etPConfPassword.setText("");
                                        startActivity(intent);
                                    }
                                    else {
                                        loading.dismiss();
                                        Log.d(Config.tag, "False :"+reason);
                                        Toast.makeText(PhotographerRegisterActivity.this, "Fill Correct Information..!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        });
                        requestQueue.add(stringRequest);
                    }
                }
                else {
                    Toast.makeText(PhotographerRegisterActivity.this, "Check Your INTERNET Connection..!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet..

            //  Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            return false;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0: spinnerpcity="Chalisgaon";
                break;
            case 1: spinnerpcity="Jalgaon";
                break;
            case 2: spinnerpcity="Nashik";
                break;
            case 3: spinnerpcity="Pune";
                break;
            case 4: spinnerpcity="Aurangabad";
                break;
            case 5: spinnerpcity="Mumbai";
                break;
            case 6: spinnerpcity="Dhule";
                break;
            case 7: spinnerpcity="Beed";
                break;
            case 8: spinnerpcity="Shirdi";
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please Select Your City..!", Toast.LENGTH_SHORT).show();
    }
}
