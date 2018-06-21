package com.example.shubham.camerabuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Shubham on 17-09-2017.
 */

public class UserLoginActivity extends Activity {
    EditText etUserEmail,etUserPassword;
    Button btnUserLogin;
    TextView tvNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_layout);
        init();  //initialization function
    }

    private void init() {
        etUserEmail=(EditText)findViewById(R.id.etUserEmail);
        etUserPassword=(EditText)findViewById(R.id.etUserPassword);
        btnUserLogin=(Button)findViewById(R.id.btnUserLogin);
        tvNewUser=(TextView)findViewById(R.id.tvNewUser);
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);
            }
        });
        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn()==true) {
                    if (etUserEmail.getText().toString().trim().isEmpty() || etUserPassword.getText().toString().trim().isEmpty()) {
                        etUserEmail.setError("Filed is Empty..!");
                        etUserPassword.requestFocus();
                        etUserPassword.setError("Field is Empty..!");
                        etUserPassword.requestFocus();
                    } else {
                        final ProgressDialog loading = ProgressDialog.show(UserLoginActivity.this, "Loading...", "Please wait...", false, false);
                        String email=etUserEmail.getText().toString();
                        Intent intent=new Intent(UserLoginActivity.this,AllPhotoGrapherActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("uemail",email);
                        intent.putExtras(bundle);
                        loading.dismiss();
                        Toast.makeText(UserLoginActivity.this, "Succesfully Login..", Toast.LENGTH_SHORT).show();
                        etUserPassword.setText("");
                        startActivity(intent);
                    }
                }

//                    final ProgressDialog loading = ProgressDialog.show(UserLoginActivity.this,"Loading...","Please wait...",false,false);
//                    String url=Config.JSON_URL+"user_login.php?&u_email="+etUserEmail.getText().toString().trim()+"&u_password="+etUserPassword.getText().toString().trim();
//                    Log.d(Config.tag,"Insert URL : "+url);
//                    RequestQueue requestQueue= Volley.newRequestQueue(UserLoginActivity.this);
//                    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String responce) {
//                            try {
//                                JSONArray jsonArray=new JSONArray(responce);
//                                JSONObject jsonObject=new JSONObject(jsonArray.get(0).toString());
//                                boolean result=jsonObject.getBoolean("result");
//                                String reason=jsonObject.getString("reason");
//                                if (result){
//                                    Log.d(Config.tag,"True :"+result);
//                                        Intent intent=new Intent(UserLoginActivity.this,AllPhotoGrapherActivity.class);
//                                        etUserEmail.setText("");
//                                        etUserPassword.setText("");
//                                        loading.dismiss();
//                                        Toast.makeText(UserLoginActivity.this, "Succesfully Login..", Toast.LENGTH_SHORT).show();
//                                        startActivity(intent);
//                                }
//                                else {
//                                    loading.dismiss();
//                                    Log.d(Config.tag, "False :"+reason);
//                                    Toast.makeText(UserLoginActivity.this, "Fill Correct Information..!", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//
//                        }
//                    });
//                    requestQueue.add(stringRequest);


//                if (isInternetOn()==true){
//                    if (etUserEmail.getText().toString().trim().isEmpty() || etUserPassword.getText().toString().trim().isEmpty()) {
//                        etUserEmail.setError("Filed is Empty..!");
//                        etUserPassword.requestFocus();
//                        etUserPassword.setError("Field is Empty..!");
//                        etUserPassword.requestFocus();
//                    } else {
//                        String url=Config.JSON_URL+"user_login.php?&u_email="+etUserEmail.getText().toString().trim()+"&u_password="+etUserPassword.getText().toString().trim();
//                        Log.d(Config.tag,"Insert URL : "+url);
//                        RequestQueue requestQueue= Volley.newRequestQueue(UserLoginActivity.this);
//                        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String responce) {
//                                try {
//                                    JSONArray jsonArray=new JSONArray(responce);
//                                    JSONObject jsonObject=new JSONObject(jsonArray.get(0).toString());
//                                    boolean result=jsonObject.getBoolean("result");
//                                    String reason=jsonObject.getString("reason");
//                                    if (result){
//                                        Log.d(Config.tag,"True :"+result);
//                                        Intent intent=new Intent(UserLoginActivity.this,AllPhotoGrapherActivity.class);
//                                        etUserEmail.setText("");
//                                        etUserPassword.setText("");
//                                        Toast.makeText(UserLoginActivity.this, "Succesfully Login..", Toast.LENGTH_SHORT).show();
//                                        startActivity(intent);
//                                    }
//                                    else {
//                                        Log.d(Config.tag, "False :"+reason);
//                                        Toast.makeText(UserLoginActivity.this, "Database Error! please try again..!", Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//
//                            }
//                        });
//                        requestQueue.add(stringRequest);
//                    }
//                }
//                else {
//                    Toast.makeText(UserLoginActivity.this, "Check Your INTERNET Connection..!", Toast.LENGTH_SHORT).show();
//                }
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
}
