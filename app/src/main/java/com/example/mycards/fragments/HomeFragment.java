package com.example.mycards.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.mycards.anim.SpannedGridLayoutManager;
import com.fooda.mycards.R;

import com.example.mycards.adaptors.HomeFragAdaptor;
import com.example.mycards.adaptors.HomeSliderAdapter;

import com.example.mycards.models.Page;
import com.example.mycards.utilits.Data;
import com.example.mycards.viewmodels.PageViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    Timer timer;
    int page_position = 0;
    Data data;
    private int dotscount;
    private ImageView[] dots;
    private RecyclerView allRecyclerView;
    private PageViewModel pageViewModel;
    private HomeFragAdaptor homeFragAdaptor;
    private RelativeLayout relativeLayout;

    public HomeFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


   private void iniVB(ViewPager mViewPager, LinearLayout linearLayout, List<Page> list) {


        HomeSliderAdapter viewPagerAdapter = new HomeSliderAdapter(getContext(), list);

        mViewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            linearLayout.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scheduleSlider();
    }


    public void scheduleSlider() {

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == dotscount) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                viewPager.setCurrentItem(page_position, true);
            }
        };

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 4000);
    }

    @Override
    public void onStop() {


      if(timer!=null) {
         // timer.cancel();
      }
        super.onStop();
        Log.e("on stop","onStop");
    }

    @Override
    public void onPause() {
        if(timer!=null) {
            //timer.cancel();
        }
        super.onPause();
      Log.e("onPause","onPause");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new Data();
        allRecyclerView = view.findViewById(R.id.home_frag_rv);
        homeFragAdaptor =new HomeFragAdaptor(getContext());
        homeFragAdaptor.setUnfinishedRQ(data.getHomeList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                }else if (position ==5){
                    return 2;
                }
                else if (position ==8){
                return 2;
            }
                else if (position ==15){
                    return 2;
                }
                else {
                    return 1;
                }
            }
        });
        allRecyclerView.setLayoutManager(gridLayoutManager);
        allRecyclerView.setAdapter(homeFragAdaptor);
       // homeFragAdaptor.notifyDataSetChanged();
       // allRecyclerView.scheduleLayoutAnimation();
        relativeLayout=view.findViewById(R.id.slideLayout);
        relativeLayout.setVisibility(View.GONE);
        timer = new Timer();

        pageViewModel= ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        pageViewModel.getCardsLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Page>>() {
            @Override
            public void onChanged(List<Page> pages) {
                Log.e("pages.size()",pages.size()+"");
                if (pages.size()>=1){
                    relativeLayout.setVisibility(View.VISIBLE);
                    iniVB(viewPager,sliderDotspanel,pages);

                }



            }
        });
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
        viewPager = view.findViewById(R.id.viewPager);
        sliderDotspanel = view.findViewById(R.id.SliderDots);
    }

}
