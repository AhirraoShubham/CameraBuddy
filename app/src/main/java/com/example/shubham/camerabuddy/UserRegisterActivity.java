package com.example.shubham.camerabuddy;

import android.app.Activity;
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

public class UserRegisterActivity extends Activity implements AdapterView.OnItemSelectedListener {
    EditText etUFname,etULname,etUEmail,etUMobileno,etUPassword,etUConfPassword;
    Button btnRegister;
    TextView tvUNewUser;
    Spinner spinnerUCity;
    String spinnerucity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_layout);
        init();
    }
    private void init() {
        etUFname=(EditText)findViewById(R.id.etUFname);
        etULname=(EditText)findViewById(R.id.etULname);
        etUEmail=(EditText)findViewById(R.id.etUEmail);
        etUMobileno=(EditText)findViewById(R.id.etUMobileno);
        etUPassword=(EditText)findViewById(R.id.etUPassword);
        etUConfPassword=(EditText)findViewById(R.id.etUConfPassword);
        spinnerUCity=(Spinner)findViewById(R.id.spinnerUCity);
        spinnerUCity.setOnItemSelectedListener(this);
        tvUNewUser=(TextView)findViewById(R.id.tvUNewUser);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        tvUNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserRegisterActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn()==true){
                   if (etUFname.getText().toString().trim().isEmpty()||etULname.getText().toString().trim().isEmpty()||etUEmail.getText().toString().trim().isEmpty()||etUMobileno.getText().toString().trim().isEmpty()||etUPassword.getText().toString().trim().isEmpty()||etUConfPassword.getText().toString().trim().equals(etUPassword)){
                       etUFname.setError("Field Can't be Empty..!");
                       etUFname.requestFocus();
                       etULname.setError("Field Can't be Empty..!");
                       etULname.requestFocus();
                       etUEmail.setError("Field Can't be Empty..!");
                       etUEmail.requestFocus();
                       etUMobileno.setError("Field Can't be Empty..!");
                       etUMobileno.requestFocus();
                       etUPassword.setError("Field Can't be Empty..!");
                       etUPassword.requestFocus();
                       etUConfPassword.setError("Password Doen't Match.!");
                       etUConfPassword.requestFocus();
                   }
                    else {
                       final ProgressDialog loading = ProgressDialog.show(UserRegisterActivity.this,"Loading...","Please wait...",false,false);
                       String url=Config.JSON_URL+"user_regitration.php?&u_fname="+etUFname.getText().toString().trim()+"&u_lname="+etULname.getText().toString().trim()+"&u_email="+etUEmail.getText().toString().trim()+"&u_mobile="+etUMobileno.getText().toString().trim()+"&u_city="+spinnerUCity.getSelectedItem().toString().trim()+"&u_password="+etUPassword.getText().toString().trim();
                       Log.d(Config.tag,"Insert URL : "+url);
                       RequestQueue requestQueue= Volley.newRequestQueue(UserRegisterActivity.this);
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
                                       Intent intent=new Intent(UserRegisterActivity.this,UserLoginActivity.class);
                                       loading.dismiss();
                                       Toast.makeText(UserRegisterActivity.this, "Successfully Register..!", Toast.LENGTH_SHORT).show();
                                       etUFname.setText("");
                                       etULname.setText("");
                                       etUEmail.setText("");
                                       etUMobileno.setText("");
                                       etUPassword.setText("");
                                       etUConfPassword.setText("");
                                       startActivity(intent);
                                   }
                                   else {
                                       Log.d(Config.tag, "False :"+reason);
                                       Toast.makeText(UserRegisterActivity.this, "Database Error! please try again..!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserRegisterActivity.this, "Check Your INTERNET Connection..!", Toast.LENGTH_SHORT).show();
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
            case 0: spinnerucity="Chalisgaon";
                break;
            case 1: spinnerucity="Jalgaon";
                break;
            case 2: spinnerucity="Nashik";
                break;
            case 3: spinnerucity="Pune";
                break;
            case 4: spinnerucity="Aurangabad";
                break;
            case 5: spinnerucity="Mumbai";
                break;
            case 6: spinnerucity="Dhule";
                break;
            case 7: spinnerucity="Beed";
                break;
            case 8: spinnerucity="Shirdi";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please Select Your City..!", Toast.LENGTH_SHORT).show();
    }
}
