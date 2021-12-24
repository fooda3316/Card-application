package com.example.mycards.utilits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swati on 11/10/15.
 */
public class Constants {
    public static final String IS_ADDED_INDB = "is_added_in_db";
    public static final String USER_NAME = "user_name";
    public static final String IS_USER_REGISTERED = "IS_USER_REGISTERED";
    public static final String IS_USER_FACEBOOK_REGISTERED = "IS_USER_FACEBOOK_REGISTERED";
    public static final String LANG_KEY = "lang key";
    public static final String VERIFICATION="Verification";
    public static final String VERIFICATION_VAL_KEY="Verification val";
    public static final String VERIFICATION_KEY="Verification key";
    public static final String USER_REGISTER_ID="USER_REGISTER_ID";
    public static final String USER_REGISTER_IMAGE="USER_REGISTER_IMAGE";
    public static final int PERMISSION_REQUEST_CODE=1;
    public static final int PICK_IMAGE_REQUEST = 1;
    public static final String USER_EMAIL = "user_email";
    public static final String USER_STATUS = "user_status";
    public static final String USER_TOKEN = "user_token";
    public static final String TYPE="type";
    public enum SharedPreferenceKeys {
        USER_NAME("userName"),
        USER_EMAIL("userEmail"),
        USER_IMAGE_URL("userImageUrl");


        private String value;

        SharedPreferenceKeys(String value) {
            this.value = value;
        }

        public String getKey() {
            return value;
        }
    }


    public static final String GOOGLE_CLIENT_ID = "321961600407-j82e9o8qc70777f5bmla24bvsg21fk0t.apps.googleusercontent.com";
    //public static final String GOOGLE_CLIENT_ID ="1141001093713-mv9486j187n2vnj3io3g3ktl3vh11gal.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_ID_TEST ="942633758424-5573115vc3ss6p8b3m1uedo541icepdq.apps.googleusercontent.com";
    public static final String TWITTER_KEY = "FwFPnIRYBouYLvgpqJQRlXiKr";
    public static final String TWITTER_SECRET = "ZMRhvt9evKdf2hzgFQokFU0jhjRtpWk4bCSpgegxYO2iHPIpWr";
    public static final String BRANCH_KEY="BRANCH_KEY";
    public static final String BRANCH_TITLE_KEY="BRANCH_TITLE_KEY";
    public static final String ClientSecret="NZhV61eU3K40GPnqbax9ahT6";

    public static final  String EMAIL = "sudanmart2020@gmail.com";
    public static final String PASSWORD = "";
    public static final int RC_GOOGLE_SIGN_IN = 100;
    public static final int RC_FACEBOOK_SIGN_IN = 200;
}