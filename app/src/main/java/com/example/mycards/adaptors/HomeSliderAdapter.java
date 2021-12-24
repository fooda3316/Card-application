package com.example.mycards.adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.fooda.mycards.R;
import com.example.mycards.models.Page;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeSliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images;
    private List<Page> categories;

    public HomeSliderAdapter(Context context, List<Page> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_home_slider, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(categories.get(position).getUri());
             //   categories.get(position);
               // Log.e("onClick","onClick "+position);
            }
        });
        Log.e("image","http://onlinesd.store/billing/ImageUploadApi/uploads/pages/"+categories.get(position).getImage());
        Picasso.get().load("http://onlinesd.store/billing/ImageUploadApi/uploads/pages/"+categories.get(position).getImage()).error(R.drawable.no_image).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
            }
        });
     //   imageView.setImageResource(images[position]);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
    public void openPage(String uri){
        try {
            Intent openPage = new Intent(Intent.ACTION_VIEW);
            openPage.setData(Uri.parse(uri));
            context.startActivity(openPage);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}