package com.example.login.ui.home;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiLogin {
    @FormUrlEncoded
    @POST("login")
    Call<login> LOGIN_CALL(
            @Field("email") String email,
            @Field("password")String password
    );

}
