package com.example.mycards.adaptors;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.fooda.mycards.R;
import com.example.mycards.models.Bank;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BanksAdaptor extends RecyclerView.Adapter<BanksAdaptor.PurchaseVH> {

private List<Bank> banks ;
private Context context;
private Activity activity;

    public BanksAdaptor(List<Bank> banks,Activity activity) {
        this.banks = banks;
        this.activity=activity;

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bank, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, int position) {
        holder.bankName.setText(banks.get(position).getBankName());
        holder.name.setText(banks.get(position).getName());
        holder.account.setText(banks.get(position).getAccount());
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+banks.get(position).getImage()).error(R.drawable.no_image).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClip(banks.get(position).getAccount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return banks.size();
    }


    class PurchaseVH extends RecyclerView.ViewHolder{
        private TextView bankName,name,account;
        ImageView imageView;
        private LinearLayout linearLayout;
        private ProgressBar progressBar;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            bankName=itemView.findViewById(R.id.bank_bank_name_item);
              name=itemView.findViewById(R.id.bank_name_item);
              account=itemView.findViewById(R.id.bank_account_item);
              imageView=itemView.findViewById(R.id.photo_bank);
            progressBar = itemView.findViewById(R.id.bank_progressbar);
            linearLayout= itemView.findViewById(R.id.banksRootLayout);

        }
    }
    private void saveClip(String text ){
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(activity, "تم النسخ الى الحافظة", Toast.LENGTH_SHORT).show();
    }
}
