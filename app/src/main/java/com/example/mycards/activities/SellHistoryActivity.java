package com.example.mycards.activities;

import android.os.Bundle;
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

import com.example.mycards.dialog.DialogHelper;
import com.example.mycards.models.SellHistory;
import com.example.mycards.themes.SetThemes;
import com.fooda.mycards.R;
import com.example.mycards.adaptors.PurchaseHistoryAdaptor;
import com.example.mycards.models.History;
import com.example.mycards.viewmodels.SellHistoryVM;

import java.util.List;


public class SellHistoryActivity extends AppCompatActivity {

    private SellHistoryVM historyVM;
    private RecyclerView recyclerView;
    private PurchaseHistoryAdaptor adaptor;
    private DialogHelper dialogHelper;

       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_hestory);
           SetThemes.setThemes(this);

           ActionBar actionBar = getSupportActionBar();
           if (actionBar != null) {
               actionBar.setDisplayHomeAsUpEnabled(true);
           }
           recyclerView=findViewById(R.id.sell_history_rv);
           dialogHelper=new DialogHelper(SellHistoryActivity.this);
           showLoadingDialog();
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           historyVM= ViewModelProviders.of(this).get(SellHistoryVM.class);
           historyVM.getCardsLiveData(this).observe(this, new Observer<List<History>>() {
               @Override
               public void onChanged(List<History> sellHistories) {
//                Log.e("SerialNumber",sellHistories.get(0).getSerialNumber());
                   adaptor=new PurchaseHistoryAdaptor(SellHistoryActivity.this);
                   adaptor.setSellHistories(sellHistories);
                   recyclerView.setAdapter(adaptor);
                   dismissLoadingDialog();
               }
           });

       }




    public void showLoadingDialog() {
      dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
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