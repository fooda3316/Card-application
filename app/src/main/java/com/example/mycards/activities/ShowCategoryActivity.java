package com.example.mycards.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.dialog.DialogHelper;
import com.fooda.mycards.R;
import com.example.mycards.adaptors.AllCardsAdapter;
import com.example.mycards.app.BaseActivity;
import com.example.mycards.internet.CheckConnection;
import com.example.mycards.internet.NoInternetLayout;
import com.example.mycards.models.Card;
import com.example.mycards.themes.SetThemes;
import com.example.mycards.viewmodels.CardViewModel;

import java.util.ArrayList;
import java.util.List;


public class ShowCategoryActivity extends BaseActivity {
    //private Data data;
    private AllCardsAdapter mAdapter;
    private String Tag = "Grid";
    private RecyclerView recyclerView;
    private CardViewModel cardViewModel;
    private int _id;
    private MaterialDialog mDialog;
    private CheckConnection connection;
    private DialogHelper dialogHelper;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetThemes.setThemes(this);
        setContentView(R.layout.activity_catigory);
        connection=new CheckConnection(this);
        dialogHelper=new DialogHelper(ShowCategoryActivity.this);
        if (!CheckConnection.internetConnectionAvailable(2000)){
            finish();
            startActivity(new Intent(this, NoInternetLayout.class));
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        showLoadingDialog();
        _id = intent.getIntExtra("id",1);
        recyclerView = findViewById(R.id.product_rv);
        cardViewModel= ViewModelProviders.of(ShowCategoryActivity.this).get(CardViewModel.class);
        cardViewModel.getCardsLiveData(_id).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                dismissLoadingDialog();
                mAdapter = new AllCardsAdapter(cards, ShowCategoryActivity.this, Tag,ShowCategoryActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       return true;
    }

    public void showLoadingDialog() {
        dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
    }
}
