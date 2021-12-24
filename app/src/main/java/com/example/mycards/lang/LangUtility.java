package com.example.mycards.lang;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

import static com.example.mycards.utilits.Constants.LANG_KEY;

public class LangUtility {
    private Context context;
    private Activity activity;

    public LangUtility(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void SetLng(){
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale(getLang()));
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }*/
if (getLang().equals("en")){
    activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

}
else {
    activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

}
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(getLang())); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
        activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Locale locale = new Locale(getLang());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, context.getApplicationContext().getResources().getDisplayMetrics());

       // context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }
    public  void changeLocale() {
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(getLang());
        res.updateConfiguration(conf, res.getDisplayMetrics());
    }
    public String getLang(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("lang",context.MODE_PRIVATE);
        String defaultLng=Locale.getDefault().getDisplayLanguage();
        return sharedPreferences.getString(LANG_KEY,defaultLng);
    }
}
