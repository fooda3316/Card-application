package com.example.mycards.intro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mycards.activities.HomeActivity;
import com.example.mycards.activities.LogInActivity;
import com.example.mycards.activities.UserSettingActivity;
import com.example.mycards.themes.SetThemes;
import com.example.mycards.utilits.LanguageUtilits;
import com.fooda.mycards.R;
import com.google.android.material.tabs.TabLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;

import static com.example.mycards.utilits.Constants.TYPE;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView btSkip,btNext;
    Button btGetStarted;
    private String type="";
    private Bundle bundle;

    AdapterIntroViewPager mAdapter;

    Integer imgIntro[]={
            R.drawable.about1,
            R.drawable.about2,
            R.drawable.about3,
            R.drawable.about4,
            R.drawable.about5,
            R.drawable.about6,
            R.drawable.about7,
            R.drawable.about8,
            R.drawable.about9


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            SetThemes.setThemes(this);

        setContentView(R.layout.activity_intro);
        bundle = getIntent().getExtras();
type=bundle.getString(TYPE);


        viewPager       = findViewById(R.id.viewpager);
        tabLayout       = findViewById(R.id.tablayout);
        btSkip          = findViewById(R.id.btSkip);
        btNext          = findViewById(R.id.btNext);
        btGetStarted    = findViewById(R.id.btGetStarted);



        btGetStarted.setVisibility(View.GONE);
        final List<Integer> arrayList = new ArrayList<>();
        arrayList.add(imgIntro[0]);
        arrayList.add(imgIntro[1]);
        arrayList.add(imgIntro[2]);
        arrayList.add(imgIntro[3]);
        arrayList.add(imgIntro[4]);
        arrayList.add(imgIntro[5]);
        arrayList.add(imgIntro[6]);
        arrayList.add(imgIntro[7]);

        mAdapter    = new AdapterIntroViewPager(this,arrayList);
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==arrayList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(arrayList.size());
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position    =0;
                position        = viewPager.getCurrentItem();
                if (position<arrayList.size()){
                    position++;
                    viewPager.setCurrentItem(position);
                }

                if (position==arrayList.size()-1){
                    loadLastScreen();
                }
            }
        });

        btGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
           if(type.equals("intro")){
                i = new Intent(IntroActivity.this, LogInActivity.class);
           }
           else {
                i = new Intent(IntroActivity.this, HomeActivity.class);

           }
                Log.e("isFirstLunch",isFirstLunch()+"");
                startActivity(i);
                finish();
            }
        });
    }


    private void loadLastScreen() {
        btSkip.setVisibility(View.INVISIBLE);
        btNext.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        btGetStarted.setVisibility(View.VISIBLE);
    }
    public static void goToIntro(Context context) {
        context.startActivity(new Intent(context, IntroActivity.class));

    }

    private Boolean isFirstLunch(){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch", Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("isFirstLunch",false);
    }
}
