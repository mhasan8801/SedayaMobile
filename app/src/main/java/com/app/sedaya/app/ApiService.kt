package com.app.sedaya.app

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register.php") //http://ws-tif.com/j-art/API/register
    fun register(
            @Field("nama") nama :String?,
            @Field("email") email :String?,
            @Field("telp") telp :String?,
            @Field("alamat") alamat :String?,
            @Field("password") password :String?
    ):Call<ResponseBody>

}