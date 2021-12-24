package com.example.mycards.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycards.models.Users;
import com.example.mycards.network.RetrofitClient;
import com.fooda.mycards.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ImageView userImage;
    private TextView name,phone,email,balance;
    Call<Users> userCall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return  inflater.inflate(R.layout.fragment_profile, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userImage=view.findViewById(R.id.pro_image);
        name=view.findViewById(R.id.pro_name);
        email=view.findViewById(R.id.pro_email);
        balance=view.findViewById(R.id.pro_balance);
        phone=view.findViewById(R.id.pro_phone);
        if (isLogIn()) {
             initiateUserData(getContext());
        }
    }
   private void initiateUserData(Context context) {
        userCall= RetrofitClient.getInstance()
                .getApiService().getParticularUser(getUserID(context));
        userCall.enqueue(new Callback<Users>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                Log.d("user Name",response.body().getUsers().get(0).getName());
               // Log.e("image","http://onlinesd.store/billing/images/"+response.body().getImage());

                assert response.body() != null;
                Picasso.get().load("http://onlinesd.store/billing/ImageUploadApi/uploads/registrations/"+response.body().getUsers().get(0).getImage()).error(R.drawable.no_image).into(userImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Error : ", e.getMessage());
                    }
                });
                name.setText(response.body().getUsers().get(0).getName());
                phone.setText(response.body().getUsers().get(0).getPhone());
                email.setText(response.body().getUsers().get(0).getEmail());
                balance.setText((response.body().getUsers().get(0).getBalance())+"");
                //  Log.d("user Name",userRepository.getUser().getName());


            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Log.e("onFailure",t.toString());
            }
        });
    }
    private int getUserID(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("registration", MODE_PRIVATE);
        return sharedPreferences.getInt(USER_REGISTER_ID,-1);
    }
    private boolean isLogIn() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("registration",MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_USER_REGISTERED,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().recreate();
    }
}
