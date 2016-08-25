package com.example.esraeken.retrofitapp;

import com.example.esraeken.retrofitapp.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by esraeken on 25/07/16.
 */
public interface RestInterfaceController {

    @GET("/users/{username}")
    void getUser(@Path("username") String username, Callback<User> response);

    @GET("/users")
    void getUserList(Callback<List<User>> response);

}
