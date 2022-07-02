package com.example.login.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.login.databinding.FragmentGalleryBinding;
import com.example.login.ui.home.ApiLogin;
import com.example.login.ui.home.login;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button btnRegistrar=binding.btnRegistrar;
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            final EditText nombre= binding.nombre;
            final EditText apPat= binding.apPat;
            final EditText apMat= binding.apMat;
            final EditText Correo= binding.Correo;
            final EditText Contrasenia= binding.Contrasenia;
            final EditText ConfirmacionC= binding.ConfirmacionC;
            final EditText FechaNa= binding.FechaNa;
            final HttpLoggingInterceptor logging =new HttpLoggingInterceptor();
            @Override
            public void onClick(View view) {
                String correo=Correo.getText().toString().trim();
                String contrasenia=Contrasenia.getText().toString().trim();

                Registrar.setLevel(HttpLoggingInterceptor.Level.BODY);
                final OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(Registrar);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://eyiogthd.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiLogin Log=retrofit.create(ApiLogin.class);
                Call<login> call=Log.LOGIN_CALL(correo,contrasenia);
                call.enqueue(new Callback<login>() {
                    @Override
                    public void onResponse(Call<login> call, Response<login> response) {
                        if (response.isSuccessful()&& response.body()!=null){
                            Correo.getText().clear();
                            Contrasenia.getText().clear();
                            Toast.makeText(getContext(),"Bienvenido ",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<login> call, Throwable t) {
                    }
                });
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}