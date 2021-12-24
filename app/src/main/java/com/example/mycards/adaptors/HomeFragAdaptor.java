package com.example.mycards.adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fooda.mycards.R;
import com.example.mycards.activities.BranchActivity;
import com.example.mycards.activities.ShowCategoryActivity;
import com.example.mycards.activities.SubCardsActivity;
import com.example.mycards.app.BaseActivity;
import com.example.mycards.models.Home;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.mycards.utilits.Constants.BRANCH_KEY;
import static com.example.mycards.utilits.Constants.BRANCH_TITLE_KEY;

public class HomeFragAdaptor extends RecyclerView.Adapter<HomeFragAdaptor.PurchaseVH> {

    private List<Home> homeList = new ArrayList<>();
    private Context context;


    public HomeFragAdaptor(Context context) {
        this.context = context;


    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_rv, parent, false);



        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {

        // Log.e("image","https://onlinesd.store/billing/images/main/"+homeList.get(position)+".png");
        Picasso.get().load("https://onlinesd.store/billing/images/main/"+homeList.get(position).getTitle()+".png").error(R.drawable.no_im).into(holder.imageView, new Callback() {
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
        holder.admin_layout.setBackgroundResource(homeList.get(position).getColor());
        holder.imageView.setBackgroundResource(homeList.get(position).getColor());

        holder.admin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(homeList.get(position));
            }
        });
        //holder.admin_layout.setBackgroundResource(backgrounds[holder.backgroundIndex]);

        //   Log.e("balance",sellHistories.get(position).getBalance()+"");
    }

            private void sendData(Home home) {
                Log.e("image",home.getTitle());
                Intent intent;
        if (home.getHaveBranch()){
             intent = new Intent(context, BranchActivity.class);
            intent.putExtra(BRANCH_TITLE_KEY,home.getTitle());
            context.startActivity(intent);

        }
        else {
            context.startActivity(new Intent(context, SubCardsActivity.class).putExtra(BRANCH_KEY,"a").putExtra(BRANCH_TITLE_KEY,home.getTitle()));

        }

       // Log.e("id adbtor",""+pos);


           }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public void setUnfinishedRQ(List<Home> homeList) {
        this.homeList = homeList;
        notifyDataSetChanged();
    }

    static class PurchaseVH extends RecyclerView.ViewHolder {
        private TextView adminTv;
        private RelativeLayout admin_layout;
        private ImageView imageView;
        private ProgressBar progressBar;

        public PurchaseVH(@NonNull View itemView) {
            super(itemView);


            admin_layout = itemView.findViewById(R.id.main_frag_layout);
            imageView = itemView.findViewById(R.id.item_rv_image);
            progressBar = itemView.findViewById(R.id.item_rv_progressbar);

        }
    }



}
