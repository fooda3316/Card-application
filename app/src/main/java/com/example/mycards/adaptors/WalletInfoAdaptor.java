package com.example.mycards.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fooda.mycards.R;
import com.example.mycards.models.WalletInfo;

import java.util.ArrayList;
import java.util.List;

public class WalletInfoAdaptor extends RecyclerView.Adapter<WalletInfoAdaptor.PurchaseVH> {

private List<WalletInfo> sellHistories=new ArrayList<>();
private Context context;
private String tag;

    public WalletInfoAdaptor() {
    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_wallet, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, int position) {
        holder.purchaseDate.setText(sellHistories.get(position).getmDate());
     //   Log.e("balance",sellHistories.get(position).getBalance()+"");
        holder.purchaseCardValue.setText(sellHistories.get(position).getBalance()+"");
  }

    @Override
    public int getItemCount() {
        return sellHistories.size();
    }
    public void setSellHistories(List<WalletInfo> walletInfos) {
        this.sellHistories = walletInfos;
        notifyDataSetChanged();
    }

    class PurchaseVH extends RecyclerView.ViewHolder{
        private TextView purchaseDate,purchaseCardValue;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            purchaseDate=itemView.findViewById(R.id.wallet_date);
              purchaseCardValue=itemView.findViewById(R.id.wallet_balance);
        }
    }
}
