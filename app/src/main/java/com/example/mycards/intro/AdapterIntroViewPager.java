package com.example.mycards.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.fooda.mycards.R;

import java.util.List;

public class AdapterIntroViewPager extends PagerAdapter {

    Context context;
    List<Integer> mIntegerList;

    public AdapterIntroViewPager(Context context, List<Integer> mIntegerList) {
        this.context = context;
        this.mIntegerList = mIntegerList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_intro_screen,null);

        ImageView imgIntro = v.findViewById(R.id.imgIntro);

        imgIntro.setImageResource(mIntegerList.get(position));

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return mIntegerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
}
