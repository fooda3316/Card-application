package com.example.mycards.adaptors;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fooda.mycards.R;
import com.example.mycards.models.History;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryAdaptor extends RecyclerView.Adapter<PurchaseHistoryAdaptor.PurchaseVH> {

private List<History> sellHistories=new ArrayList<>();
private Activity activity;

    public PurchaseHistoryAdaptor(Activity activity) {
        this.activity=activity;
    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sell_hestory, parent, false);

        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, int position) {
        holder.purchaseDate.setText(sellHistories.get(position).getmDate());
        holder.purchaseCardName.setText(sellHistories.get(position).getName()+" $");
        holder.purchaseSerialNumber.setText(sellHistories.get(position).getSerialNumber());
        holder.purchaseCardValue.setText(sellHistories.get(position).getValue());
        holder.products_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClip(sellHistories.get(position).getSerialNumber());
            }
        });
  }

    @Override
    public int getItemCount() {
        return sellHistories.size();
    }
    public void setSellHistories(List<History> histories) {
        this.sellHistories = histories;
        notifyDataSetChanged();
    }

    class PurchaseVH extends RecyclerView.ViewHolder{
        private TextView purchaseDate,purchaseCardName,purchaseSerialNumber,purchaseCardValue;
        private LinearLayout products_layout;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            purchaseDate=itemView.findViewById(R.id.purchase_date);
            purchaseCardName=itemView.findViewById(R.id.purchase_card_name);
            purchaseSerialNumber=itemView.findViewById(R.id.purchase_serial_Number);
            purchaseCardValue=itemView.findViewById(R.id.purchase_card_value);
            products_layout=itemView.findViewById(R.id.row_products_layout);

        }
    }
    private void saveClip(String text ){
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(activity, "تم النسخ الى الحافظة", Toast.LENGTH_SHORT).show();
    }
}
