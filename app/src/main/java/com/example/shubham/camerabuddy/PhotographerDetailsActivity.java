package com.example.shubham.camerabuddy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shubham on 22-09-2017.
 */

public class PhotographerDetailsActivity extends Activity {
    TextView tvPFnameDetails, tvPLnameDetails, tvPEmailDetails, tvPCityDetails, tvPRateDetails, tvPCameraTypeDetails, tvPMobileDetails;
    String p_fname, p_rate, p_city, p_lname, p_email, p_mobile, cameratype;
    ImageView imageView2,imageViewOne;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photographer_details_layout);
        init();
        p_fname = getIntent().getStringExtra("p_fname");
        p_lname = getIntent().getStringExtra("p_lname");
        p_email = getIntent().getStringExtra("p_email");
        p_mobile = getIntent().getStringExtra("p_mobile");
        p_rate = getIntent().getStringExtra("p_rate");
        p_city = getIntent().getStringExtra("p_city");
        cameratype = getIntent().getStringExtra("cameratype");

        tvPFnameDetails.setText("First Name:- " + p_fname);
        tvPLnameDetails.setText("Last Name:- " + p_lname);
        tvPEmailDetails.setText(p_email);
        tvPMobileDetails.setText(p_mobile);
        tvPRateDetails.setText("Charges:- " + p_rate + "/- per photo");
        tvPCityDetails.setText("City:- " + p_city);
        tvPCameraTypeDetails.setText("Camera:- " + cameratype);

    }
    private void init() {
        tvPFnameDetails = (TextView) findViewById(R.id.tvPFnameDetails);
        tvPLnameDetails = (TextView) findViewById(R.id.tvPLnameDetails);
        tvPEmailDetails = (TextView) findViewById(R.id.tvPEmailDetails);
        tvPCityDetails = (TextView) findViewById(R.id.tvPCityDetails);
        tvPRateDetails = (TextView) findViewById(R.id.tvPRateDetails);
        tvPCameraTypeDetails = (TextView) findViewById(R.id.tvPCameraTypeDetails);
        tvPMobileDetails = (TextView) findViewById(R.id.tvPMobileDetails);
        imageViewOne=(ImageView)findViewById(R.id.imageViewOne);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                number = tvPMobileDetails.getText().toString();
//                intent.setData(Uri.parse("Number :" + number));
//                startActivity(intent);
            }
        });
    }
}
