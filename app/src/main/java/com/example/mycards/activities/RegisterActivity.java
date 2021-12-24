package com.example.mycards.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fooda.mycards.R;
import com.example.mycards.app.MyPermissions;
import com.example.mycards.login.LoginPresenter;
import com.example.mycards.login.LoginView;
import com.example.mycards.login.TravelmateSnackbars;
import com.example.mycards.utilits.DialogPresenter;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import dev.shreyaspatil.MaterialDialog.MaterialDialog;

import static com.example.mycards.utilits.Constants.PICK_IMAGE_REQUEST;
import static com.example.mycards.utilits.Constants.USER_EMAIL;
import static com.example.mycards.utilits.Constants.USER_TOKEN;


public class RegisterActivity extends AppCompatActivity implements LoginView, TravelmateSnackbars {

    private LoginPresenter mLoginPresenter ;
    private String phone,confirmPassString,firstname,lastname,emailString,passString,imageOriginalName;
    MyPermissions permissions;
    private Uri selectedImage;
    EditText email_signup,phoneNumber,pass_signup,confirm_pass_signup,firstName,lastName;
    Button ok_signup;
    ImageView imageView;
    private SharedPreferences mSharedPreferences;
    //private MaterialDialog mDialog;
    private LoadingDialog loadingDialog ;
    private DialogPresenter dialogPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        permissions=new MyPermissions(this);
        firstName=findViewById(R.id.input_firstname_signup);
        lastName=findViewById(R.id.input_lastname_signup);
        ok_signup=findViewById(R.id.btnFragRegister);
        email_signup=findViewById(R.id.input_email_signup);
        pass_signup=findViewById(R.id.input_pass_signup);
        phoneNumber=findViewById(R.id.input_phone_signup);
        confirm_pass_signup=findViewById(R.id.input_confirm_pass_signup);
        imageView=findViewById(R.id.selectedImage);
        dialogPresenter=new DialogPresenter(this);
        mLoginPresenter=new LoginPresenter(this,this);
        mLoginPresenter.bind(this);
        ok_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = email_signup.getText().toString();
                passString = pass_signup.getText().toString();
                confirmPassString = confirm_pass_signup.getText().toString();
                firstname = firstName.getText().toString();
                lastname = lastName.getText().toString();
                phone = phoneNumber.getText().toString();
                if (selectedImage!=null) {
                    imageOriginalName= System.currentTimeMillis()+"";


                    if (validateEmail(emailString,email_signup)) {
                        if (validatePassword(passString,pass_signup)) {
                            if (passString.equals(confirmPassString)) {
                                //  imageOriginalName=System.currentTimeMillis()+"";
                                //   Log.d("loin info",firstname+" "+lastname+" "+emailString+" "+phone+" "+passString);
                                mLoginPresenter.ok_signUp( selectedImage,RegisterActivity.this,firstname, lastname, emailString, passString, imageOriginalName,phone);
                                //  mLoginPresenter.ok_signUp("firstname", "lastname", "emailString", "passString", "default","phone");


                            } else {
//                            Snackbar snackbar = Snackbar
//                                    .make(view.findViewById(android.R.id.content),
//                                            R.string.passwords_check, Snackbar.LENGTH_LONG);
//                            snackbar.show();
                                pass_signup.setError(getResources().getString(R.string.passwords_check));
                            }
                        }
                    }

                }
                else {
                    ok_signup.setText(getResources().getString(R.string.choise_image));
                    if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        openFileChooser();
                    }
                    else {
                        permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    openFileChooser();
                }
                else {
                    permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }
        });

    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    public boolean validatePassword(String passString, TextView textView) {
        if (passString.length() >= 8) {
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(passString);

            if (matcher.matches()) {
                return true;
            } else {
                DialogPresenter.showAlert(getResources().getString(R.string.invalid_password));

                textView.setError(getResources().getString(R.string.invalid_password));
                return false;
            }
        } else {
            textView.setError(getResources().getString(R.string.password_length));

            DialogPresenter.showAlert(getResources().getString(R.string.password_length));

            return false;
        }
    }
    public boolean validateEmail(String email, TextView textView) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (!email.equals("") && matcher.matches()) {
            return true;
        } else {
            DialogPresenter.showAlert(getResources().getString(R.string.invalid_email));
            textView.setError(getResources().getString(R.string.invalid_email));

            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);

        }
    }
    @Override
    public void rememberUserInfo(String token, String email) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        finish();
    }

    @Override
    public void showError() {
//        TravelmateSnackbars.createSnackBar(findViewById(R.id.login_activity),
//                R.string.toast_invalid_username_or_password, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog= new LoadingDialog(this);

        loadingDialog.setLoadingText(getResources().getString(R.string.progress_wait));
        loadingDialog.show();
       /* mDialog = new MaterialDialog.Builder(RegisterActivity.this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(getResources().getString(R.string.progress_wait))
                //.set(true, 0)
                .show();*/
    }

    @Override
    public void dismissLoadingDialog() {
        loadingDialog.close();
        //mDialog.dismiss();
    }

    @Override
    public void getRunTimePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.VIBRATE,

                }, 0);
            }
        }
    }

    @Override
    public void checkUserSession() {
        if (mSharedPreferences.getString(USER_TOKEN, null) != null) {
            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));

            finish();
        }
    }

    @Override
    public void openSignUp() {

    }

    @Override
    public void openLogin() {

    }

    @Override
    public void setLoginEmail(String email) {

    }

    @Override
    public void showMessage(String message) {

    }
    @Override
    public void onDestroy() {
        mLoginPresenter.unbind();
        super.onDestroy();
    }
}