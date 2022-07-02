package com.example.login.ui.gallery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRegistro {
    @FormUrlEncoded
    @POST("registroForm")
    Call<Registrar> REGISTRAR_CALL(
            @Field("nombre") String nombre,
            @Field("apellidoPaterno") String apellidoPaterno,
            @Field("apellidoMaterno")String apellidoMaterno,
            @Field("correo")String correo,
            @Field("contrasenia")String contrasenia,
            @Field("contrasenia2")String contrasenia2,
            @Field("fechaNacimiento")String fechaNacimiento
    );
}
