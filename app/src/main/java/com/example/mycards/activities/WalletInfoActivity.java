package com.example.mycards.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.adaptors.BanksAdaptor;
import com.example.mycards.fragments.WalletActivity;
import com.example.mycards.models.Bank;
import com.example.mycards.models.WalletInfo;
import com.example.mycards.themes.SetThemes;
import com.example.mycards.viewmodels.BanksViewModel;
import com.fooda.mycards.R;
import com.example.mycards.api.APIService;
import com.example.mycards.app.MyPermissions;
import com.example.mycards.models.Result;
import com.example.mycards.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.PICK_IMAGE_REQUEST;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;

public class WalletInfoActivity extends AppCompatActivity {

    private MyPermissions permissions;

    //private Uri selectedImage;
    private String imageOriginalName,imageName;
    private APIService apiService;
    private MaterialDialog mDialog;
    private Uri selectedImage;
    private TextView showInfoText;
    private RelativeLayout wallet_layout,history,improvement;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private BanksAdaptor adaptor;
    private BanksViewModel banksViewModel;
    private ProgressBar progressBar;
    private EditText editText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);
        SetThemes.setThemes(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setBankRV();
        showInfoText=findViewById(R.id.showInfoTV);
        linearLayout=findViewById(R.id.waletInfoLayout);
        imageView=findViewById(R.id.showImageIV);
        wallet_layout=findViewById(R.id.walletLayout);
        improvement=findViewById(R.id.layout_upload_improvement);
        history=findViewById(R.id.mHistory);
        editText=findViewById(R.id.edtOp);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletInfoActivity.this, SellHistoryActivity.class));
            }
        });
        wallet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletInfoActivity.this, WalletActivity.class));
            }
        });
        permissions=new MyPermissions(WalletInfoActivity.this);
        improvement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUserSigned()) {


                    if (selectedImage!=null){
                        if (!isEdtEmpty()){
                            sendImprove();
                        }
                        //showLoadingDialog();
                        imageOriginalName= System.currentTimeMillis()+"";
                        // sendPicture(getUserID(),imageOriginalName,selectedImage);
                    }
                    else {
                        if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            openFileChooser();
                        }
                        else {
                            permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }
                }
                else {
                    new MaterialDialog.Builder(WalletInfoActivity.this)
                            .title(getResources().getString(R.string.login_message))
                            .positiveText(getResources().getString(R.string.yes))
                            .negativeText(getResources().getString(R.string.no))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    startActivity(new Intent(WalletInfoActivity.this, LogInActivity.class));
                                }
                            }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).build().show();
                }

            }
        });
    }

    private boolean isEdtEmpty() {
        if (editText.getText().toString().isEmpty()){
            editText.setError("لا يمكن ترك هذا الحقل فارغاً");
            vibrate();
          //  Toast.makeText(this, "لا يمكن ترك هذا الحقل فارغاً", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }
    public void vibrate() {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
    private Boolean isUserSigned(){
        SharedPreferences sharedPreferences=getSharedPreferences("registration",MODE_PRIVATE);
        return  sharedPreferences.getBoolean(IS_USER_REGISTERED,false);
    }





    private void sendImprove() {
        imageOriginalName= System.currentTimeMillis()+"";
        showLoadingDialog();
       if (selectedImage!=null&&imageOriginalName!=null){
           sendPicture(getUserID(),imageOriginalName,selectedImage,editText.getText().toString().trim());

       }
    }


private void goBack(String message){
    imageView.setVisibility(View.GONE);
    showInfoText.setVisibility(View.VISIBLE);
    showInfoText.setText(message);

}
    private void resultNotSuccess() {
       recreate();
//        imageView.setVisibility(View.GONE);
//        showInfoText.setVisibility(View.VISIBLE);
//        sendImprovement.setText(getResources().getString(R.string.btn_back));
      //  linearLayout.setVisibility(View.VISIBLE);
      //  showInfoText.setText(getResources().getString(R.string.not_sent_successfly));

    }

    private int getUserID() {
        SharedPreferences sharedPreferences=getSharedPreferences("registration",MODE_PRIVATE);
       return sharedPreferences.getInt(USER_REGISTER_ID,-1);
      //  return 42;
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }
    private void showAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(WalletInfoActivity.this);
        alert.setMessage(message)
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle("Alert")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //do some thing
                    }
                }).show();
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(WalletInfoActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }


    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode,  resultCode,  data);
       // Log.d("FragmentA.java","onActivityResult called");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            //the image URI
             selectedImage = data.getData();
             linearLayout.setVisibility(View.GONE);
             imageView.setVisibility(View.VISIBLE);
             imageView.setImageURI(selectedImage);

        }
    }

    private void sendPicture(int userID, String imageName, Uri imageUri,String op){

        File file = new File(getRealPathFromURI(imageUri));
        RequestBody userIDBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userID));
        RequestBody imageBody = RequestBody.create(MediaType.parse("text/plain"), imageName);
        RequestBody opBody = RequestBody.create(MediaType.parse("text/plain"), op);

        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        apiService= RetrofitClient.getInstance().getApiService();
        Call<Result> call=apiService.uploadPicture(requestFile,userIDBody,opBody,imageBody);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissLoadingDialog();
                goBack(response.body().getMessage());
                if (!response.isSuccessful()) {
                    dismissLoadingDialog();
                 //   Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                if (!response.body().getError()){
                  //  Log.d("Success Message",response.body().getMessage());
                    finish();
                    startActivity(new Intent(WalletInfoActivity.this, OrderCopmleteActivity.class));
                    dismissLoadingDialog();
                }
                showAlert(response.body().getMessage());

//                Log.e("Message",response.body().getMessage());

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("onFailure",t.toString());
                dismissLoadingDialog();
                resultNotSuccess();
            }
        });

    }
    private String getRealPathFromURI(Uri contentUri) {
        Log.e("getRealPath","getRealPath");
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(WalletInfoActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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
    private void setBankRV(){
       // showLoadingDialog();

        banksViewModel = ViewModelProviders.of(this).get(BanksViewModel.class);
        banksViewModel.getCardsLiveData().observe(this, new Observer<List<Bank>>() {
            @Override
            public void onChanged(List<Bank> banks) {
                recyclerView=findViewById(R.id.banks_rv);
                progressBar=findViewById(R.id.banks_progressbar);
                recyclerView.setLayoutManager(new GridLayoutManager(WalletInfoActivity.this,2));
                adaptor=new BanksAdaptor(banks,WalletInfoActivity.this);
                progressBar.setVisibility(View.GONE);
//                Log.e("name",banks.get(1).getBankName());
                recyclerView.setAdapter(adaptor);

              //  dismissLoadingDialog();



            }
        });
    }
}