package com.example.esraeken.retrofitapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.esraeken.retrofitapp.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.Contants;

public class MainActivity extends AppCompatActivity {
    Button tikla;
    EditText gelen_yazi;
    Button tikla2;
    RecyclerView recyclerView;
    UserAdapter userAdapter;


    private RestInterfaceController restInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gelen_yazi = (EditText) findViewById(R.id.gelecekyazi);
        tikla = (Button) findViewById(R.id.button);
        tikla2 = (Button) findViewById(R.id.button2);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        userAdapter = new UserAdapter(this);//userAdapter'ın bu contextte olduğunu belirtiyor
        recyclerView.setAdapter(userAdapter);//


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Contants.URL).setLogLevel(RestAdapter.LogLevel.FULL).build();//ayarlar
        restInterface = restAdapter.create(RestInterfaceController.class);//İçerik

        //region button 1
        tikla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("İndiriliyor...");
                progressDialog.show();

                String gelenyazi = gelen_yazi.getText().toString();

                restInterface.getUser(gelenyazi, new Callback<User>() { //gelenyaziya göre user'ı çağırıyor
                    @Override
                    public void success(User model, Response response) {

                        StringBuilder stringBuilder = new StringBuilder();//string oluşturuyor
                        stringBuilder.append("username: " + model.getLogin());//username'i getLogin metodu ile getiriyor
                        stringBuilder.append("\nid: " + model.getId());//id'i getId metodu ile çağırıyor
                        stringBuilder.append("\nsite admin: " + model.getSiteAdmin());//site adminliğni getSiteAdmin metodu ile kontrol ediyor


                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);//Bir uyarı diyalogu oluşturuyor
                        builder.setCancelable(true);
                        builder.setTitle("Gelen Veri");
                        builder.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setMessage(stringBuilder.toString());

                        progressDialog.dismiss();
                        builder.create().show();


                    }


                    @Override
                    public void failure(RetrofitError error) {//Retrofit hata verirse çalışacak

                        String merror = error.getMessage();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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


        });
        //endregion

        tikla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("İndiriliyor...");
                progressDialog.show();

                String gelenyazi = gelen_yazi.getText().toString();


                restInterface.getUserList(new Callback<List<User>>() { // json array döneceği için modelimizi array tipinde belirledik
                    @Override
                    public void success(List<User> model, Response response) {

                        userAdapter.updateList(model);

                        progressDialog.dismiss();
                    }


                    @Override
                    public void failure(RetrofitError error) {

                        String merror = error.getMessage();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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


        });


    }


}
