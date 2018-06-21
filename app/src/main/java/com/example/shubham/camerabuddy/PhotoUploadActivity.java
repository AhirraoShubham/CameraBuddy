package com.example.shubham.camerabuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.IOException;

/**
 * Created by Shubham on 20-09-2017.
 */

public class PhotoUploadActivity extends Activity{
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    //private String UPLOAD_URL ="http://notesync.esy.es/upload.php";

    private String KEY_IMAGE = "image";
    Spinner spinnerPCameraType,spinnerPCharges;
    ImageView imageViewUploadPhotoFirst;
    String spinerrates,spinercameratype;
    Button btnDone;
    TextView tvPEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_upload_layout);
        tvPEmail=(TextView)findViewById(R.id.tvPEmailDetails);
        Bundle bundle=getIntent().getExtras();
        String data1=bundle.getString("uemail");
        tvPEmail.setText(data1);
        spinnerPCameraType=(Spinner)findViewById(R.id.spinnerPCameraType);
        spinnerPCharges=(Spinner)findViewById(R.id.spinnerPCharges);
        imageViewUploadPhotoFirst=(ImageView)findViewById(R.id.imageViewUploadPhotoFirst);
        imageViewUploadPhotoFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnDone=(Button)findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn()==true){
                    final ProgressDialog loading = ProgressDialog.show(PhotoUploadActivity.this,"Loading...","Please wait...",false,false);
                    String url=Config.JSON_URL+"image_upload.php?&cameratype="+spinnerPCameraType.getSelectedItem().toString().trim().replace(" ","_")+"&p_rate="+spinnerPCharges.getSelectedItem().toString().trim().replace("~","_")+"&p_email="+tvPEmail.getText().toString().trim();
                    Log.d(Config.tag,"Insert URL : "+url);
                    RequestQueue requestQueue= Volley.newRequestQueue(PhotoUploadActivity.this);
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
                                    Intent intent=new Intent(PhotoUploadActivity.this,MainActivity.class);
                                    loading.dismiss();
                                    Toast.makeText(PhotoUploadActivity.this, "Successfully Register..!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                else {
                                    Log.d(Config.tag, "False :"+reason);
                                    Toast.makeText(PhotoUploadActivity.this, "Fill Correct Information..!", Toast.LENGTH_SHORT).show();
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
                else {
                    Toast.makeText(PhotoUploadActivity.this, "Check Your INTERNET Connection..!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinnerPCharges.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        spinerrates = "Rs5~20";
                        break;
                    case 1:
                        spinerrates = "Rs21~40";
                        break;
                    case 2:
                        spinerrates = "Rs41~60";
                        break;
                    case 3:
                        spinerrates = "Rs61~80";
                        break;
                    case 4:
                        spinerrates = "Rs81~100";
                        break;
                    case 5:
                        spinerrates = "Rs101~120";
                        break;
                    case 6:
                        spinerrates = "Rs121~150";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(PhotoUploadActivity.this, "Select Your Rate..!", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerPCameraType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        spinercameratype = "SONY DSLR";
                        break;
                    case 1:
                        spinercameratype = "Canon DSLR";
                        break;
                    case 2:
                        spinercameratype = "Nikon DSLR";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(PhotoUploadActivity.this, "Select Your Camera Type", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageViewUploadPhotoFirst.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
