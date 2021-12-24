package com.example.mycards.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.mycards.ads.BannerAdd;
import com.fooda.mycards.R;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.adaptors.BranchAdapter;
import com.example.mycards.api.APIService;
import com.example.mycards.internet.CheckConnection;
import com.example.mycards.internet.NoInternetLayout;
import com.example.mycards.models.Result;
import com.example.mycards.models.Results;
import com.example.mycards.network.RetrofitClient;
import com.example.mycards.utilits.Data;
import com.fooda.mycards.R;
import com.google.android.gms.ads.AdView;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mycards.utilits.Constants.BRANCH_KEY;
import static com.example.mycards.utilits.Constants.BRANCH_TITLE_KEY;

public class BranchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BranchAdapter branchAdapter;
    private Data data;
    private String title;
    private CheckConnection connection;
    private APIService apiService;
    private MaterialDialog mDialog;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        recyclerView=findViewById(R.id.branch_rv);
        adView = findViewById(R.id.ad_view);
        new BannerAdd(this).initializeAdd(adView);
        showLoadingDialog();
        apiService= RetrofitClient.getInstance().getApiService();


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        title=intent.getStringExtra(BRANCH_TITLE_KEY);
        data=new Data();
        Call<Results> call=apiService.getBranch(title);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NotNull Call<Results> call, @NotNull Response<Results> response) {
                dismissLoadingDialog();
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());

                    return;
                }
                recyclerView.setLayoutManager(new GridLayoutManager(BranchActivity.this,2));
                assert response.body() != null;
                branchAdapter=new BranchAdapter(response.body().getBranches(),BranchActivity.this,title);
                recyclerView.setAdapter(branchAdapter);
                if (response.body().getBranches().size()==0){
                    Intent intent =new Intent(BranchActivity.this,SubCardsActivity.class);
                    intent.putExtra(BRANCH_KEY,"a");
                    intent.putExtra(BRANCH_TITLE_KEY,title);
                    startActivity(intent);

                }
                dismissLoadingDialog();
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                dismissLoadingDialog();
                Log.e("onFailure",t.toString());

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(BranchActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }


    public void dismissLoadingDialog() {
        if (mDialog!=null){
            mDialog.dismiss();
        }

    }
}