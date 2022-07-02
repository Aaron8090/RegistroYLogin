package com.example.login.ui.home;

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

import com.example.login.databinding.FragmentHomeBinding;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final Button btn_inicio =binding.btnInicio;
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            final EditText edt_email=binding.edtEmail;
            final EditText edt_password=binding.edtPassword;
            final HttpLoggingInterceptor loggin =new HttpLoggingInterceptor();
            @Override
            public void onClick(View view) {
                String email=edt_email.getText().toString().trim();
                String password=edt_password.getText().toString().trim();

                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
                final OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://eyiogthd.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiLogin Log=retrofit.create(ApiLogin.class);
                Call<login>call=Log.LOGIN_CALL(email,password);
                call.enqueue(new Callback<login>() {
                    @Override
                    public void onResponse(Call<login> call, Response<login> response) {
                        if (response.isSuccessful()&& response.body()!=null){
                            edt_email.getText().clear();
                            edt_password.getText().clear();
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