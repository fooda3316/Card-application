package com.example.mycards.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.activities.SubCardsActivity;
import com.example.mycards.dialog.DialogHelper;
import com.example.mycards.models.Bank;
import com.example.mycards.models.Page;
import com.example.mycards.viewmodels.BanksViewModel;
import com.example.mycards.viewmodels.PageViewModel;
import com.fooda.mycards.R;
import com.example.mycards.adaptors.BanksAdaptor;
import com.example.mycards.utilits.Data;

import java.util.List;


public class BanckFragment extends Fragment {
    private RecyclerView recyclerView;
    private BanksAdaptor adaptor;
    private MaterialDialog mDialog;
    private BanksViewModel banksViewModel;
    private DialogHelper dialogHelper;
    public BanckFragment() {

    }
    public static BanckFragment newInstance() {
        return new BanckFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_banks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void showLoadingDialog() {
        dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
    }
}
