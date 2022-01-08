package com.app.sedaya.app

import com.app.sedaya.model.ResponModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register.php") //http://ws-tif.com/sedaya/API/register
    fun register(
            @Field("nama") nama :String,
            @Field("email") email :String,
            @Field("telp") telp :String,
            @Field("alamat") alamat :String,
            @Field("password") password :String
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("login.php") //http://ws-tif.com/j-art/API/register
    fun login(
        @Field("email") email :String,
        @Field("password") password :String
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("update-profile.php") //http://ws-tif.com/j-art/API/register
    fun updateProfile(
        @Field("usr_id") usr_id : Int,
        @Field("nama") nama :String,
        @Field("email") email :String,
        @Field("telp") telp :String,
        @Field("alamat") alamat :String
    ):Call<ResponModel>

    @GET("seni.php")
    fun getSeni(): Call<ResponModel>

}