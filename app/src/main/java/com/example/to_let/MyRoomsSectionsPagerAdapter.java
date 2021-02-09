package com.example.to_let;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.to_let.ui.MyRoomsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MyRoomsSectionsPagerAdapter extends FragmentPagerAdapter {
  public MyRoomsSectionsPagerAdapter(FragmentManager fragmentManager) {
    super(fragmentManager);
  }

  @Override
  public Fragment getItem(int position) {
    // getItem is called to instantiate the fragment for the given page.
    // Return a RoomsFragment (defined as a static inner class below).
    switch(position){
      case 0:
        return new MyRoomsFragment();
      default:
        break;
    }
    return null;
  }

  @Override
  public int getCount() {
    // Show 1 total pages.
    return 1;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return Integer.toString(R.string.title_activity_my_rooms);
      case 1:
        return "SECTION 2";
      case 2:
        return "SECTION 3";
    }
    return null;
  }
}
