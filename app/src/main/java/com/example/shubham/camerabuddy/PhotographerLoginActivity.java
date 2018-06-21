package com.example.shubham.camerabuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shubham on 17-09-2017.
 */

public class PhotographerLoginActivity extends Activity {
    EditText etPhotographerEmail, etPhotographerPassword;
    Button btnPhotographerLogin;
    TextView tvNewUser;
    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photographer_login_layout);
        init();
    }
    private void init() {
        etPhotographerEmail = (EditText) findViewById(R.id.etPhotographerEmail);
        etPhotographerPassword = (EditText) findViewById(R.id.etPhotographerPassword);
        btnPhotographerLogin = (Button) findViewById(R.id.btnPhotographerLogin);
        btnPhotographerLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          login();
                    }
                });
        tvNewUser = (TextView) findViewById(R.id.tvNewUser);
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotographerLoginActivity.this, PhotographerRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        if (TextUtils.isEmpty(etPhotographerEmail.getText().toString().trim()) || TextUtils.isEmpty(etPhotographerPassword.getText().toString().trim())) {
            etPhotographerEmail.setError("Fields can't be Empty");
            etPhotographerEmail.requestFocus();
            etPhotographerPassword.setError("Fields can't be Empty");
            etPhotographerPassword.requestFocus();
        } else {
            final ProgressDialog loading = ProgressDialog.show(PhotographerLoginActivity.this,"Loading...","Please wait...",false,false);
            String email=etPhotographerEmail.getText().toString();
            Intent intent=new Intent(PhotographerLoginActivity.this,PhotographerProfileActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("uemail",email);
            intent.putExtras(bundle);
            loading.dismiss();
            Toast.makeText(this, "Succesfully Login..", Toast.LENGTH_SHORT).show();
            etPhotographerPassword.setText("");
            startActivity(intent);
        }
    }
}