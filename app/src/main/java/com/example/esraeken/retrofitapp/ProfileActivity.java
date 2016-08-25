package com.example.esraeken.retrofitapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.example.esraeken.retrofitapp.model.User;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.Contants;

/**
 * Created by esraeken on 27/07/16.
 */
public class ProfileActivity extends AppCompatActivity {


    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    UserAdapter userAdapter;
    private RestInterfaceController restInterface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("İndiriliyor...");
        progressDialog.show();
        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        userAdapter = new UserAdapter(this);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Contants.URL).setLogLevel(RestAdapter.LogLevel.FULL).build();//ayarlar kısmı url verildiğib yer
        restInterface = restAdapter.create(RestInterfaceController.class);


        String username = getIntent().getStringExtra("username").split(":")[1];

        restInterface.getUser(username.trim(), new Callback<User>() {//servise belirlediğimiz isim=username diğeri User classı
            @Override
            public void success(User model, Response response) {


                progressDialog.dismiss();
                tv1.setText(model.getId().toString());
                tv3.setText("\nadminlik durumu"+ model.getSiteAdmin());
                tv4.setText("\ntipi"+model.getType());


            }


            @Override
            public void failure(RetrofitError error) {

                String merror = error.getMessage();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Gelen Veri Yok");
                builder.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setMessage(merror);

                progressDialog.dismiss();
                builder.create().show();
            }

        });

    }

}