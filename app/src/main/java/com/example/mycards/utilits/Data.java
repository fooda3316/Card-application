package com.example.mycards.utilits;



import com.fooda.mycards.R;
import com.example.mycards.models.Bank;
import com.example.mycards.models.Branch;
import com.example.mycards.models.Card;
import com.example.mycards.models.Category;
import com.example.mycards.models.Home;

import java.util.ArrayList;
import java.util.List;

public class Data {


    private List<Home> homeList=new ArrayList<>();


    public List<Home> getHomeList() {
        homeList.add(new Home("google", true,R.drawable.border_google));
        homeList.add(new Home("itunes", true,R.drawable.border_itunes));
        homeList.add(new Home("pubg", false,R.drawable.border_osn));
        homeList.add(new Home("freefire", false,R.drawable.border_freefire));
        homeList.add(new Home("razer", false,R.drawable.border_razer));
        homeList.add(new Home("playstation", false,R.drawable.border_playstation));
        homeList.add(new Home("gamestop", false,R.drawable.border_gamestop));
        homeList.add(new Home("steam", true,R.drawable.border_steam));
        homeList.add(new Home("starzplay", false,R.drawable.border_starzplay));
        homeList.add(new Home("xbox", true,R.drawable.border_xbox));
        homeList.add(new Home("amizon", false,R.drawable.border_amizon));
        homeList.add(new Home("netflix", false,R.drawable.border_netflix));
        homeList.add(new Home("ebay", false,R.drawable.border_ebay));
        homeList.add(new Home("spotify", false,R.drawable.border_spotify));
        homeList.add(new Home("osn", false,R.drawable.border_osn));
        homeList.add(new Home("mobilelegend", false,R.drawable.border_mobilelegend));
        homeList.add(new Home("fortnit", false,R.drawable.border_fortnit));
        homeList.add(new Home("minecraft", false,R.drawable.border_minecraft));
        homeList.add(new Home("imvu", false,R.drawable.border_imvu));
        homeList.add(new Home("hulu", false,R.drawable.border_hulu));

//


        return homeList;
    }



}
