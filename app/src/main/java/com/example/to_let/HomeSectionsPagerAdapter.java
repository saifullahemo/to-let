package com.example.to_let;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.to_let.ui.RoomsFragment;

public class HomeSectionsPagerAdapter extends FragmentPagerAdapter {
    public HomeSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new RoomsFragment();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Rooms";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }

}
