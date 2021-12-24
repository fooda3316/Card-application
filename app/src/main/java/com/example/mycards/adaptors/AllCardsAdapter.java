package com.example.mycards.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mycards.api.APIService;
import com.example.mycards.dialog.DialogHelper;
import com.example.mycards.models.AdminCard;
import com.example.mycards.models.Result;
import com.example.mycards.models.Users;
import com.fooda.mycards.R;
import com.example.mycards.activities.LogInActivity;
import com.example.mycards.activities.SubCardsActivity;
import com.example.mycards.api.CardAPIService;
import com.example.mycards.models.Card;
import com.example.mycards.models.CardResult;
import com.example.mycards.network.RetrofitClient;
import com.example.mycards.utilits.DialogPresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;
import static com.example.mycards.utilits.DialogPresenter.showAlert;
import static com.example.mycards.utilits.DialogPresenter.showSuccessAlert;


public class AllCardsAdapter extends RecyclerView.Adapter<AllCardsAdapter.MyViewHolder> {

    List<Card> cardList;
    Context context;
    String Tag;
    private MaterialDialog mDialog;
    private String _name,_sub,_branch;
    private int _price,_cardId;
    private int[] backgrounds = new int[6];
    private CardAPIService cardAPIService;
    private APIService aPIService;
    private DialogPresenter dialogPresenter;
    private Activity activity;
    private Call<Users> userCall;
    private DialogHelper dialogHelper;

    public AllCardsAdapter(List<Card> cardList, Context context, String tag,Activity activity) {
        this.cardList = cardList;
        this.context = context;
        this.activity=activity;

        Tag = tag;
        dialogPresenter=new DialogPresenter(context);
        dialogHelper=new DialogHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        if (Tag.equalsIgnoreCase("List")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_all_cards, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_all_cards, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Card card = cardList.get(position);
        holder.title.setText(card.getTitle());
        holder.price.setText(card.getPrice()+"");
       // holder.progressBar.setVisibility(View.GONE);
        String imageUri="http://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+card.getImage();
        Log.e("image uri","http://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+card.getImage());
        Glide.with(activity).load(imageUri).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                Log.e("GlideException : ", e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                //if you want to convert the drawable to ImageView
                Bitmap bitmapImage  = ((BitmapDrawable) resource).getBitmap();
                holder.progressBar.setVisibility(View.GONE);


                return false;
            }
        }).into(holder.imageView);

       /* Picasso.get()
                .load("https://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+card.getImage())
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.drawable.no_image)
                .into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(Exception e) {
                Log.e("picaso Error : ", e.getMessage());
                holder.progressBar.setVisibility(View.GONE);
            }
        });*/





        holder.products_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _price = card.getPrice();
                _name = card.getTitle();
                _sub = card.getSubName();
                _branch = card.getBranch();
                _cardId = card.getId();

                if (_name.equals("google play")) {
                    context.startActivity(new Intent(context, SubCardsActivity.class));
                } else {
                    if (isUserSigned()) {
                        showBuyAlert(_price, _name,_sub,_branch,_cardId);
                    } else {
                        new MaterialDialog.Builder(context)
                                .title(context.getResources().getString(R.string.login_message))
                                .positiveText(context.getResources().getString(R.string.yes))
                                .negativeText(context.getResources().getString(R.string.no))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        context.startActivity(new Intent(context, LogInActivity.class));
                                    }
                                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                                .build().show();
                    }
                }
            }
        });

    }
    private Boolean isUserSigned(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("registration",MODE_PRIVATE);
        return  sharedPreferences.getBoolean(IS_USER_REGISTERED,false);
    }
    private void showBuyAlert(int price, String name,String sub,String branch,int cardId) {
       // Log.e("showBuyAlert","showBuyAlert excuted price is "+price+" name is "+name+" sub name is "+sub+" branch is "+branch);
        AlertDialog.Builder alert= new AlertDialog.Builder(context);
        alert.setMessage("هذة العملية سوف تكلفك  "+price+" جنيه "+" \n هل ترغب في المتابعة ?")
                .setIcon(R.drawable.app_logo)
                .setTitle(sub+" "+branch+" "+name+" شراء ")
                .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   Log.e("onClick","onClick excuted");
                        buyOperation(name,sub,branch,cardId);
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    private void buyOperation(String name,String sub,String branch,int cardId) {
        showLoadingDialog();        //test();
       // if (context instanceof HomeActivity) {
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            cardAPIService = RetrofitClient.getInstance().getCardAPIService();
        Log.e("inside buy method","User id:"+getUserID()+" sub "+sub+" branch "+branch+" name "+name);

        Call<CardResult> call = cardAPIService.buyCard(sub,branch,name, getUserID(),date,cardId);
            call.enqueue(new retrofit2.Callback<CardResult>() {
                @Override
                public void onResponse(Call<CardResult> call, Response<CardResult> response) {
                    dismissLoadingDialog();
                    assert response.body() != null;
                    String message;
                    if (!response.isSuccessful()) {
                        Log.e("error code","Code: " + response.code());
                        return;
                    }

                    else {
                         message=response.body().getMessage();
                       // Log.e("error message", message);
                        if (!response.body().getError()) {
                            dismissLoadingDialog();
                            showSuccessAlert(message);


                        } else {
                            dismissLoadingDialog();
                            if (response.body().getMessage().equals("نأسف البطاقة التي طلبتها غير موجودة الرجاء المحاولة لاحقا")){
                                Log.e(" message", message);
                                requestCardAlert("نأسف البطاقة التي طلبتها غير موجودة هل ترغب في اخطار الادارة بذلك ؟",new AdminCard(name,sub,branch,date),context);
                            }else {
                                showAlert(message);
                            }


                        }
                    }
                }

                @Override
                public void onFailure(Call<CardResult> call, Throwable t) {
                    dismissLoadingDialog();
                    showAlert("نأسف حدث خطأ اثناء عملية الشراء  \n تارجاء المحاولة من جديد");
                    Log.e("buy Failure",t.toString());
                }
            });
      //  }
    }





    private int getUserID() {
        SharedPreferences sharedPreferences=context.getSharedPreferences("registration", MODE_PRIVATE);
        return sharedPreferences.getInt(USER_REGISTER_ID,-1);
    }

    @Override
    public int getItemCount() {

        return cardList.size();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, attribute, price, shopNow;
        ImageView progressBar;
        LinearLayout quantity_ll;
        TextView quantity;
        private LinearLayout linearLayout;
        private LinearLayout products_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.all_product_image);
            title = itemView.findViewById(R.id.all_product_title);
            price = itemView.findViewById(R.id.all_product_price);
            shopNow = itemView.findViewById(R.id.all_product_shop_now);
            progressBar = itemView.findViewById(R.id.all_product_progressbar);
            products_layout=itemView.findViewById(R.id.row_products_layout);

        }
    }
    public void showLoadingDialog() {
        dialogHelper.showLoadingDialog();
    }
    public void dismissLoadingDialog() {
        dialogHelper.dismissLoadingDialog();
    }

    private void addNewReqUest(AdminCard card) {
        showLoadingDialog();
        aPIService = RetrofitClient.getInstance().getApiService();
       // Log.e("card data","Title "+card.getTitle()+" getSubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result>call= aPIService.requestCard(card.getTitle(),card.getSubName(),card.getBranch(),card.getDate());
        call.enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(context, "لم يتم ارسال الطلب بنجاح بنجاح", Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    Toast.makeText(context, "تم ارسال الطلب بنجاح بنجاح", Toast.LENGTH_LONG).show();
                }
                else {
                    dismissLoadingDialog();
                    Toast.makeText(context, "تم ارسال الطلب بنجاح بنجاح", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Add admin card error",t.toString());
                dismissLoadingDialog();
                Toast.makeText(context, "لم يتم ارسال الطلب بنجاح بنجاح", Toast.LENGTH_LONG).show();

            }
        });
    }
    public  void requestCardAlert(String message,AdminCard card,Context context) {
        Log.e("requestCardAlert",message);
        AlertDialog.Builder alert= new AlertDialog.Builder(context);
        alert.setMessage(message)
                .setIcon(R.drawable.app_logo)
                .setTitle(context.getResources().getString(R.string.alert_title))
                .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getUserBalance(card);

                    }

                }).setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .show();
    }

    private void getUserBalance(AdminCard card) {
        userCall = RetrofitClient.getInstance()
                .getApiService().getParticularUser(getUserID());
        userCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code", "Code: " + response.code());
                    return;
                }
                assert response.body() != null;
            int balance=    response.body().getUsers().get(0).getBalance();
            if (balance>=_price){
                addNewReqUest(card) ;
            }
            else {
                Toast.makeText(context, "عفواً لا يمكنك اجراء هذة العملية", Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

}
