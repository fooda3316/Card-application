package com.example.mycards.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.ads.BannerAdd;
import com.example.mycards.dialog.DialogHelper;
import com.fooda.mycards.R;
import com.example.mycards.adaptors.AllCardsAdapter;
import com.example.mycards.internet.CheckConnection;
import com.example.mycards.internet.NoInternetLayout;
import com.example.mycards.models.Card;
import com.example.mycards.viewmodels.SubCardViewModel;
import com.google.android.gms.ads.AdView;

import java.util.List;

import static com.example.mycards.utilits.Constants.BRANCH_KEY;
import static com.example.mycards.utilits.Constants.BRANCH_TITLE_KEY;

public class SubCardsActivity extends AppCompatActivity {
    private AllCardsAdapter mAdapter;
    private String Tag = "Grid";
    private RecyclerView recyclerView;
    private SubCardViewModel viewModel;
    private String title,branch;
    private CheckConnection connection;
    private AdView adView;
    private DialogHelper dialogHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cards);
        adView = findViewById(R.id.ad_view);
        new BannerAdd(this).initializeAdd(adView);
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        branch=intent.getStringExtra(BRANCH_KEY);
        title=intent.getStringExtra(BRANCH_TITLE_KEY);
        dialogHelper=new DialogHelper(SubCardsActivity.this);
        showLoadingDialog();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        connection=new CheckConnection(this);
        if (!CheckConnection.internetConnectionAvailable(2000)){
            finish();
            startActivity(new Intent(this, NoInternetLayout.class));
        }
        viewModel= ViewModelProviders.of(SubCardsActivity.this).get(SubCardViewModel.class);
        Log.e("card data","title is "+title+" branch is "+branch);
        viewModel.getCardsLiveData(title,branch).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                  setUpRV(cards);
                dismissLoadingDialog();
            }
        });

    }
private void setUpRV(List<Card> cardsList){
    recyclerView = findViewById(R.id.product_rv);
    mAdapter = new AllCardsAdapter(cardsList, SubCardsActivity.this, Tag,SubCardsActivity.this);
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(mAdapter);

}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showLoadingDialog() {
        dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
    }
}