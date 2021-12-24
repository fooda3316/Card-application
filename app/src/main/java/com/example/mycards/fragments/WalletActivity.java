package com.example.mycards.fragments;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mycards.themes.SetThemes;
import com.fooda.mycards.R;
import com.example.mycards.adaptors.WalletInfoAdaptor;
import com.example.mycards.models.WalletInfo;
import com.example.mycards.viewmodels.WalletInfoVM;

import java.util.List;

public class WalletActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WalletInfoVM infoVM;
    private WalletInfoAdaptor adaptor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        SetThemes.setThemes(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        recyclerView=findViewById(R.id.wallet_inf_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        infoVM= ViewModelProviders.of(this).get(WalletInfoVM.class);
        infoVM.getCardsLiveData(this).observe(this, new Observer<List<WalletInfo>>() {
            @Override
            public void onChanged(List<WalletInfo> walletInfos) {
                //     Log.e("Balance",walletInfos.get(0).getBalance()+"");
                adaptor=new WalletInfoAdaptor();
                adaptor.setSellHistories(walletInfos);
                recyclerView.setAdapter(adaptor);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;}
        return super.onOptionsItemSelected(item);

    }

}
