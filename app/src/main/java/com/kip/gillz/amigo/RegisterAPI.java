package com.kip.gillz.amigo;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface RegisterAPI
{

    @FormUrlEncoded
    @POST("/tal/newclient.php")
    public void insertUser(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("meterno") String meterno,
            @Field("Username") String Username,
            Callback<Response> callback);
}
