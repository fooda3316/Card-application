package com.example.mycards.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

//import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import com.fooda.mycards.R;
import com.example.mycards.login.LoginHelper;
import com.example.mycards.login.LoginPresenter;
import com.example.mycards.login.LoginView;
import com.example.mycards.login.TravelmateSnackbars;
import com.example.mycards.helpers.FacebookHelper;
import com.example.mycards.helpers.GoogleHelper;
import com.example.mycards.models.UserMeta;
import com.facebook.GraphResponse;
import com.facebook.login.LoginFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.mycards.utilits.Constants.GOOGLE_CLIENT_ID;
import static com.example.mycards.utilits.Constants.RC_GOOGLE_SIGN_IN;

public class LogInActivity extends AppCompatActivity implements LoginView, TravelmateSnackbars ,  FacebookHelper.OnFbSignInListener, GoogleHelper.OnGoogleSignInListener{
    private static final int RC_SIGN_IN = 100;
    private LoginPresenter mLoginPresenter ;
    EditText email_login;
    EditText pass_login;
   // @BindView(R.id.ok_login)
    Button ok_login, skip;
   // private MaterialDialog mDialog;
   private LoadingDialog loadingDialog ;
    private FacebookHelper fbConnectHelper;
    private GoogleHelper gSignInHelper;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginButton facebookSignIn;
    private GoogleSignInButton googleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        setContentView(R.layout.activity_login);
        initializeGoogleLogIn();
        // Initialize Sign in client

        googleSignInClient = GoogleSignIn.getClient(LogInActivity.this, googleSignInOptions);

        mLoginPresenter = new LoginPresenter(this,this);
        mLoginPresenter.bind(this);
        ok_login=findViewById(R.id.btn_login);
        skip =findViewById(R.id.btnSkip);
        email_login =findViewById(R.id.input_email_login);
        pass_login =findViewById(R.id.input_pass_login);
        facebookSignIn = findViewById(R.id.signInWithFacebookBtn);
        googleSignInButton = findViewById(R.id.signInWithGoogleBtn);


        mAuth = FirebaseAuth.getInstance();
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // signIn();
              if(isGooglePlayServicesAvailable(LogInActivity.this)) {
                 // gSignInHelper.signIn(LogInActivity.this);
                 // googleLogIn();
                  loginNow();
              }
              else {
               sweetDialog();
              }
            }
        });
      //  facebookSignIn.setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
//        fbConnectHelper.looIn(facebookSignIn);
        findViewById(R.id.facebook_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbConnectHelper.looIn(facebookSignIn);
            }
        });
        findViewById(R.id.googleLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  signIn();
                gSignInHelper.signIn(LogInActivity.this);
            }
        });
        findViewById(R.id.forgot_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LogInActivity.this,PasswordActivity.class));
            }
        });


        findViewById(R.id.btn_new_accaunt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LogInActivity.this,HomeActivity.class));
            }
        });

        ok_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (isAllTextsNotEmpty(email_login,pass_login)){
                  mLoginPresenter.ok_login(email_login.getText().toString().trim(),pass_login.getText().toString().trim());
              }
            }
        });

    }

    private void loginNow() {
        // initialize sign in intent

        Intent intent = googleSignInClient.getSignInIntent();

        // start activity for result
        startActivityForResult(intent, 100);
        showMyLoadingDialog();
    }

    private void initializeGoogleLogIn() {
         googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken(GOOGLE_CLIENT_ID)
                .requestEmail()
                .build();
    }



    @Override
    public void showLoadingDialog() {
        loadingDialog= new LoadingDialog(this);

        loadingDialog.setLoadingText(getResources().getString(R.string.progress_wait));
        loadingDialog.show();

    }

    @Override
    public void dismissLoadingDialog() {
        loadingDialog.close();
    }

    @Override
    public void onDestroy() {
        mLoginPresenter.unbind();
        super.onDestroy();
    }
   private Boolean isAllTextsNotEmpty(EditText mail, EditText pass){
        if (mail.getText().toString().trim().isEmpty()){
            mail.setError("لا يمكن ترك هذا الحقل فارغا");
            vibrate();
            return false;
        }
       if (pass.getText().toString().trim().isEmpty()){
           mail.setError("لا يمكن ترك هذا الحقل فارغا");
           vibrate();
           return false;
       }
        return true;
   }
    private void setup() {
        GoogleHelper.setClientID(GOOGLE_CLIENT_ID);
        gSignInHelper = GoogleHelper.getInstance(this);
        gSignInHelper.initialize(this, this);
        fbConnectHelper = new FacebookHelper(this,this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null ) {
            showMyLoadingDialog();
            if (requestCode==RC_GOOGLE_SIGN_IN){
                Log.d(TAG, "onActivityResult: RC_GOOGLE_SIGN_IN");
                gSignInHelper.onActivityResult(requestCode, resultCode, data);

            }
            else {
                Log.d(TAG, "onActivityResult: RC_FACEBOOK_SIGN_IN");
                fbConnectHelper.onActivityResult(requestCode, resultCode, data);

            }
        }
        else {
            Log.d(TAG, "onActivityResult:there are no data ");
        }

    }

    private void displayToast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void OnFbSuccess(GraphResponse graphResponse) {

        Log.d(TAG, "OnFbSuccess: graphResponse "+graphResponse);
        UserMeta meta = getUserModelFromGraphResponse(graphResponse);
        if(meta !=null) {
            Log.d(TAG, "OnFbSuccess: data ");
            new LoginHelper(meta,LogInActivity.this).login();

        }
    }

    private UserMeta getUserModelFromGraphResponse(GraphResponse graphResponse)
    {
        UserMeta userMeta = new UserMeta();
        try {
            JSONObject jsonObject = graphResponse.getJSONObject();
            userMeta.userName = jsonObject.getString("name");
            // userMeta.userEmail = jsonObject.getString("email");
            String firstName=jsonObject.getString("first_name");
//            Random r = new Random();
//            String randomNumber = String.format("%04d", r.nextInt(1001));

            //userMeta.userEmail = "a.fooda@hotmail.com";
            String id = jsonObject.getString("id");
            userMeta.userEmail = firstName+id+"@gmail.com";

            String profileImg = "http://graph.facebook.com/"+ id+ "/picture?type=large";
            userMeta.profilePic = profileImg;
            Log.i(TAG,profileImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userMeta;
    }

    @Override
    public void OnFbError(String errorMessage) {
        dismissMyLoadingDialog();
        displayLog(errorMessage);
    }

    @Override
    public void OnGSignSuccess(GoogleSignInAccount acct, UserMeta person) {
        dismissMyLoadingDialog();
        UserMeta meta = new UserMeta();
        meta.userName = (acct.getDisplayName()==null)?"":acct.getDisplayName();
        meta.userEmail = acct.getEmail();

        Log.i(TAG, "OnGSignSuccess: AccessToken "+ acct.getIdToken());

        Uri photoUrl = acct.getPhotoUrl();
        if(photoUrl!=null)
            meta.profilePic = photoUrl.toString();
        else
            meta.profilePic = "";
        Log.i(TAG, acct.getIdToken());
        new LoginHelper(meta,LogInActivity.this).login();
        //   SharedPreferenceManager.getSharedInstance().saveUserModel(userMeta);

    }

    @Override
    public void OnGSignError(GoogleSignInResult errorMessage) {
        displayLog("Google Sign in error@");
        dismissMyLoadingDialog();
    }



    public void vibrate() {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
    //Code to check if Facebook app is installed:
    public static boolean doesUserHaveFacebookAppInstalled(Context context){
        try{
            context.getPackageManager().getApplicationInfo("com.facebook.katana", 0 );
            return true;
        } catch( PackageManager.NameNotFoundException e ){
            return false;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
              GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }


    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }
    private void sweetDialog(){

        new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setCustomImage(R.drawable.logo)
                .setTitleText(getResources().getString(R.string.google_service))
                .setContentText(getResources().getString(R.string.google_service_not_avilable))
                .setConfirmText(getResources().getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();




    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        UserMeta userMeta = new UserMeta();
        userMeta.userName = (acct.getDisplayName()==null)?"":acct.getDisplayName();
        userMeta.userEmail = acct.getEmail();

        userMeta.profilePic = acct.getPhotoUrl().toString();
        new LoginHelper(userMeta,LogInActivity.this).login();
    }

    private void showMyLoadingDialog(){
        loadingDialog= new LoadingDialog(this);
        loadingDialog.setLoadingText(getResources().getString(R.string.progress_wait));
        loadingDialog.show();
    }
    private void dismissMyLoadingDialog(){
      loadingDialog.close();

    }
    private void displayLog(String string) {
        Log.d(TAG, "displayLog: message "+string);
    }
}