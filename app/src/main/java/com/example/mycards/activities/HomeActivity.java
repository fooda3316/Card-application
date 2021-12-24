package com.example.mycards.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//import com.afollestad.materialdialogs.DialogAction;
//import com.afollestad.materialdialogs.MaterialDialog;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mycards.api.APIService;
import com.example.mycards.intro.IntroActivity;
import com.example.mycards.models.Result;
import com.example.mycards.social.SocialPresenter;
import com.fooda.mycards.R;
import com.example.mycards.fragments.HomeFragment;
import com.example.mycards.fragments.ProfileFragment;
import com.example.mycards.fragments.SendFeedbackFragment;
import com.example.mycards.internet.CheckConnection;
import com.example.mycards.internet.NoInternetLayout;
import com.example.mycards.lang.LangUtility;
import com.example.mycards.models.Users;
import com.example.mycards.network.RetrofitClient;
import com.example.mycards.themes.SetThemes;
import com.example.mycards.utilits.SharingConnection;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mycards.utilits.Constants.IS_USER_FACEBOOK_REGISTERED;
import static com.example.mycards.utilits.Constants.IS_USER_REGISTERED;
import static com.example.mycards.utilits.Constants.LANG_KEY;
import static com.example.mycards.utilits.Constants.TYPE;
import static com.example.mycards.utilits.Constants.USER_REGISTER_ID;
import static com.example.mycards.utilits.Constants.USER_REGISTER_IMAGE;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CircleImageView circleImageView;
    private TextView headerTextViewName, headerTextViewBalance;
    private DrawerLayout drawer;

    Fragment fragment = null;
    private int q = 0;
    private ImageView youtube, about, wats;
    private LinearLayout navHeaderLayout;
    private LangUtility langUtility;
    private CheckConnection connection;
    private SharingConnection sharingConnection;
    private Call<Users> userCall;
    //private ImageView ivFacebook, ivTwitter;
    private SocialPresenter presenter;
    private APIService apiService;
    private String appVersion;
    String currentVersion = null;

    @SuppressLint({"ResourceAsColor", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        langUtility = new LangUtility(this, this);
        langUtility.changeLocale();
        langUtility.SetLng();
        super.onCreate(savedInstanceState);
        connection = new CheckConnection(this);
        setContentView(R.layout.activity_main);
        presenter = new SocialPresenter();
        apiService = RetrofitClient.getInstance().getApiService();
        getVersion();
       /// JavaMailAPI javaMailAPI = new JavaMailAPI(this, "ak1169194@gmail.com",  "mSubject", "mMessage");

       // javaMailAPI.execute();
//new GMailSender(this).sendMessage("ak1169194@gmail.com","TEST","this is TEST body");

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("currentVersion", currentVersion);
        //    new LoginHelper(new UserMeta("test name","test email","profilePic"),this).login();
        sharingConnection = new SharingConnection(this);


        if (!CheckConnection.internetConnectionAvailable(2000)) {
            finish();
            startActivity(new Intent(this, NoInternetLayout.class));
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        //  setLocale();
        //navHeaderLayout = findViewById(R.id.nav_header_main_layout);
        setSupportActionBar(toolbar);

        // toolbar.setBackgroundColor((Color.parseColor("#ff5254")));
        SetThemes.ChangeToolbarColor(toolbar, this);
        drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // drawer.customAdjustment(10.0f, 0.8f,10.0f, 10f);

        NavigationView navigationView = findViewById(R.id.navegation_view);
        displaySelectedScreen(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(
                item -> {
                    drawer.closeDrawer(GravityCompat.START);
                    //  drawer.openDrawer(Gravity.START);

                    displaySelectedScreen(item.getItemId());
                    return false;
                });


        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_main);
        headerTextViewName = findViewById(R.id.tvTitle);
        headerTextViewBalance = findViewById(R.id.tvBalance);
        circleImageView = headerLayout.findViewById(R.id.civProfile);
       /* ivTwitter = headerLayout.findViewById(R.id.ivTwitter);


        ivFacebook = headerLayout.findViewById(R.id.ivFacebook);
        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharingConnection.goTo(SocialPresenter.getFacebook());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharingConnection.goTo(SocialPresenter.getTwitter());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
*/
        if (isUserSigned()) {
            intiateUserData();
        }


    }

    private void intiateUserData() {
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
                //    Log.d("user Name",response.body().getUsers().get(0).getName());
                //    Log.e("image","http://onlinesd.store/billing/images/"+response.body().getUsers().get(0).getImage());

                String uri = "";
                if (isFacebookLogIn()) {
                    uri = getUserImageUri();
                } else {
                    uri = "http://onlinesd.store/billing/ImageUploadApi/uploads/registrations/" + response.body().getUsers().get(0).getImage();
                }
                assert response.body() != null;
                Picasso.get().load(uri).error(R.drawable.no_image).into(circleImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Error : ", e.getMessage());
                    }
                });
                int mBalance=response.body().getUsers().get(0).getBalance();
                if (mBalance==0){
                    headerTextViewBalance.setText("0.00");
                }
                else {
                    headerTextViewBalance.setText(mBalance+"");
                }

                headerTextViewName.setText(response.body().getUsers().get(0).getName());

                //  Log.d("user Name",userRepository.getUser().getName());


            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Log.e("onFailure", t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.exist_message))
                .positiveText(getResources().getString(R.string.yes))
                .negativeText(getResources().getString(R.string.no))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
                .build().show();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.navWallet:
                startActivity(new Intent(HomeActivity.this, WalletInfoActivity.class));
                finish();
                break;
            case R.id.nav_facebook:
                sharingConnection.goTo(SocialPresenter.getFacebook());
                break;
            case R.id.nav_twitter:
                sharingConnection.goTo(SocialPresenter.getTwitter());
                break;
            case R.id.nav_settings:

                // finish();
                startActivity(new Intent(HomeActivity.this, UserSettingActivity.class));
                break;
            case R.id.nav_rating:
                sharingConnection.RateApp();
                break;
            case R.id.nav_send_feedback:
                fragment = new SendFeedbackFragment();
                break;

            case R.id.nav_inst:
                sharingConnection.goTo(SocialPresenter.getInst());

                break;
            case R.id.nav_youtube:
                sharingConnection.goTo(SocialPresenter.getYoutube());

                break;
            case R.id.nav_about:
                sharingConnection.showAppInfo();

                break;
            case R.id.nav_how_to_use:
                startActivity(new Intent(HomeActivity.this, IntroActivity.class).putExtra(TYPE, "how to use"));
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_action:
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onStart() {
        super.onStart();
        connection = new CheckConnection(HomeActivity.this);
        // connection.checkConnection();

    }

    private boolean isFacebookLogIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("registration", MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_USER_FACEBOOK_REGISTERED, false);
    }

    private int getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("registration", MODE_PRIVATE);
        return sharedPreferences.getInt(USER_REGISTER_ID, -1);
    }

    private String getUserImageUri() {
        SharedPreferences sharedPreferences = getSharedPreferences("registration", MODE_PRIVATE);
        return sharedPreferences.getString(USER_REGISTER_IMAGE, " ");
    }



    private Boolean isUserSigned() {
        SharedPreferences sharedPreferences = getSharedPreferences("registration", MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_USER_REGISTERED, false);
    }

    public void setLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("lang", MODE_PRIVATE);
        String defaultLng = Locale.getDefault().getDisplayLanguage();
        String lang = sharedPreferences.getString(LANG_KEY, defaultLng);

        Locale myLocale = new Locale(lang);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = myLocale;
        getResources().updateConfiguration(conf, dm);

    }

    private void getVersion() {

        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getAppVersion().enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code", "Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                appVersion = response.body().getVersion();
                if (!appVersion.equals(currentVersion)) {
                    sweetDialog();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("getVerificatin error", t.toString());

            }
        });
    }

    private void sweetDialog() {

        new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setCustomImage(R.drawable.logo)
                .setTitleText(getResources().getString(R.string.update))
                .setContentText(getResources().getString(R.string.update_message))
                .setConfirmText(getResources().getString(R.string.yes))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (isGooglePlayInstalled(HomeActivity.this)){
                            finish();
                            goToGooglePlay();
                        }


                    }
                })
                .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();

                    }
                })
                .show();


    }

    private void goToGooglePlay() {
        Intent updateApp = new Intent(Intent.ACTION_VIEW);
        updateApp.setData(Uri.parse("market://details?id=" + getPackageName()));
        startActivity(updateApp);
    }
    public static boolean isGooglePlayInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try
        {
            PackageInfo info = pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            String label = (String) info.applicationInfo.loadLabel(pm);
            app_installed = (label != null && !label.equals("Market"));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }
}