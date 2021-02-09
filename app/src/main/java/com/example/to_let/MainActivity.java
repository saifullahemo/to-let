package com.example.to_let;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.to_let.ui.RoomsFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private HomeSectionsPagerAdapter mHomeSectionsPagerAdapter;
    private RoomCardRecyclerViewAdapter roomCardRecyclerViewAdapter;
    private List<ParseObject> mRooms;
    private DrawerLayout drawerLayout;
    private BottomNavigationView navigationView;
    //private NavigationView navigationView;
    private ViewPager mViewPager;
    private DrawerLayout mDrawer;
    private AdView mAdView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //tracking number of times app is opened by user
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mHomeSectionsPagerAdapter = new HomeSectionsPagerAdapter(getSupportFragmentManager());
        mRooms = new ArrayList<>();
        roomCardRecyclerViewAdapter = new RoomCardRecyclerViewAdapter(this, mRooms);
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mHomeSectionsPagerAdapter);

        //drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

       // navigationView.setNavigationItemSelectedListener(this);
       // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawerLayout.setDrawerListener(toggle);
        //sync the toggle state
      //  toggle.syncState();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //@Override
                // public boolean onNavigationItemSelected(MenuItem item) {
                //switch between the selected items and act accordingly
                int selection = item.getItemId();
                //the fragments to load on selection
                Fragment fragment = null;
                Class fragmentClass = null;
                switch (selection){
                    case R.id.nav_browse_rooms:
                        //launch rooms fragments
                        Toast.makeText(MainActivity.this, "Fetching rooms", Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        RoomsFragment roomsFragment = new RoomsFragment();
                        fragmentTransaction.add(R.id.container, roomsFragment, "fragment");
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_my_rooms:
                /*try {

                    //fragment = new MyRoomsFragment();
                }catch (Exception e){
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
                        //check if logged in
                        if(ParseUser.getCurrentUser() == null){
                            //ask to login
                            Toast.makeText(MainActivity.this, "Please login to access your rooms", Toast.LENGTH_LONG).show();
                            //login dialog
                            showLoginDialog();
                        }else {
                            //launch my rooms fragment
                            Toast.makeText(MainActivity.this, "My rooms", Toast.LENGTH_LONG).show();
                            //direct to users' rooms
                            Intent myRoomsIntent = new Intent(MainActivity.this, MyRoomsActivity.class);
                            startActivity(myRoomsIntent);
                        }
                        break;
         /*   case R.id.nav_share:
                //share the app
                Toast.makeText(this, "Sharing the app", Toast.LENGTH_LONG).show();

                Intent shareAppIntent = new Intent();
                shareAppIntent.setAction(Intent.ACTION_SEND);
                shareAppIntent.putExtra(Intent.EXTRA_TEXT, "Get the Rooms App \nhttp://rooms.co.zw");
                shareAppIntent.setType("text/plain");
                Intent chooser = Intent.createChooser(shareAppIntent, "Share App Via:");
                //verify whether there is an app capable of handling the intent
                if (shareAppIntent.resolveActivity(getPackageManager()) != null){
                   startActivity(chooser);
                }
                break;
            case R.id.nav_feedback:
                //launch contact us activity/fragment
                Toast.makeText(this, "Give us a shout", Toast.LENGTH_LONG).show();
                break;
                */
                    case R.id.nav_contact_us:
                        //launch contact us
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        Intent aboutIntent = new Intent(MainActivity.this, ContactUsActivity.class);
                        startActivity(aboutIntent);
                        break;
                    case R.id.action_add_listing:
                        Intent addListingIntent = new Intent(MainActivity.this, AddRoomActivity.class);
                        startActivity(addListingIntent);
                        break;
                }

                //highlight the selected item
                //item.setChecked(true);

                //close the drawer
//                    drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

  /*      FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new listing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(MainActivity.this, "1 of 4", Toast.LENGTH_LONG).show();
                Intent addListingIntent = new Intent(MainActivity.this, AddRoomActivity.class);
                startActivity(addListingIntent);
            }
        });
*/
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

       mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded () {
            // Code to be executed when an ad finishes loading.
        }

        @Override
        public void onAdFailedToLoad (LoadAdError adError){
            // Code to be executed when an ad request fails.
        }

        @Override
        public void onAdOpened () {
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
        }

        @Override
        public void onAdClicked () {
            // Code to be executed when the user clicks on an ad.
        }

        @Override
        public void onAdLeftApplication () {
            // Code to be executed when the user has left the app.
        }

        @Override
        public void onAdClosed () {
            // Code to be executed when the user is about to return
            // to the app after tapping on an ad.
        }
    });


}


    private void navToLogin() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        //intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final ParseQuery<ParseUser> query = ParseUser.getQuery();

        
        //get the search view and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        //assumes the current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);
       // searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                roomCardRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                roomCardRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onSearchRequested() {

        //pauseSomeStuff();

        //roomCardRecyclerViewAdapter.getFilter().filter(query);
        return super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the RoomsFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ParseUser currentUser = ParseUser.getCurrentUser();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_add_listing:
               Intent addListingIntent = new Intent(this, AddRoomActivity.class);
               startActivity(addListingIntent);
                break;
            case R.id.action_sign_in:
                if(currentUser == null){
                    //navigate to login
                    Log.i("SignIn: ", "Attempting sign-in");
                    navToLogin();
                }else{
                    //tell user is already signed in
                    Toast.makeText(this, "You are already logged in as " + currentUser.getUsername(), Toast.LENGTH_LONG).show();
                }
                break;
         /*   case R.id.action_sign_out:
                //log current user out
                //check if there is a user then log her out
                if(currentUser != null){
                    //log em out
                    Toast.makeText(this, "Logging out", Toast.LENGTH_LONG).show();
                    ParseUser.logOut();
                    navToLogin();
                }else{
                    Toast.makeText(this, "Not logged in, please log in", Toast.LENGTH_LONG).show();
                    showLoginDialog();
                }
                break;*/
            /*case R.id.action_filter_rooms:
                Intent filterRoomsIntent = new Intent(this, FilterActivity.class);
                startActivity(filterRoomsIntent);
                break;*/
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                this.finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }
/*
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //switch between the selected items and act accordingly
        int selection = item.getItemId();
        //the fragments to load on selection
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (selection){
            case R.id.nav_browse_rooms:
                //launch rooms fragments
                Toast.makeText(this, "Loading.. rooms", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RoomsFragment roomsFragment = new RoomsFragment();
                fragmentTransaction.add(R.id.container, roomsFragment, "fragment");
                fragmentTransaction.commit();
                break;

            case R.id.nav_my_rooms:
               /* try {

                    //fragment = new MyRoomsFragment();
                }catch (Exception e){
                    e.printStackTrace();
                }
               // FragmentManager fragmentManager = getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                //check if logged in*/
/*
               if(ParseUser.getCurrentUser() == null){
                    //ask to login
                    Toast.makeText(this, "Please login to access your rooms", Toast.LENGTH_LONG).show();
                    //login dialog
                    showLoginDialog();
                }else {
                    //launch my rooms fragment
                    Toast.makeText(this, "My rooms", Toast.LENGTH_LONG).show();
                    //direct to users' rooms
                    Intent myRoomsIntent = new Intent(this, MyRoomsActivity.class);
                    startActivity(myRoomsIntent);
                }
                break;
         /*   case R.id.nav_share:
                //share the app
                Toast.makeText(this, "Sharing the app", Toast.LENGTH_LONG).show();

                Intent shareAppIntent = new Intent();
                shareAppIntent.setAction(Intent.ACTION_SEND);
                shareAppIntent.putExtra(Intent.EXTRA_TEXT, "Get the Rooms App \nhttp://rooms.co.zw");
                shareAppIntent.setType("text/plain");
                Intent chooser = Intent.createChooser(shareAppIntent, "Share App Via:");
                //verify whether there is an app capable of handling the intent
                if (shareAppIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(chooser);
                }
                break;
            case R.id.nav_feedback:
                //launch contact us activity/fragment
                Toast.makeText(this, "Give us a shout", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_contact_us:

                if(ParseUser.getCurrentUser() == null){

                    Toast.makeText(this, "Please login to access your profile", Toast.LENGTH_LONG).show();
                    showLoginDialog();
                }else {
                    //launch contact us
                    Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show();
                    Intent aboutIntent = new Intent(this, ContactUsActivity.class);
                    startActivity(aboutIntent);
                    break;
                }
        }

        //highlight the selected item
        //item.setChecked(true);

        //close the drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

*/
    private void showLoginDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.show(fragmentManager, "fragment_alert");
    }

}
