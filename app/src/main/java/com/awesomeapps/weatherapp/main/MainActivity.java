package com.awesomeapps.weatherapp.main;

import android.Manifest;
import android.location.Location;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awesomapps.weatherapp.Fragments.DailyFragment;
import com.awesomapps.weatherapp.Fragments.HourlyFragment;
import com.awesomapps.weatherapp.Utils.LocationGetter;
import com.awesomapps.weatherapp.delegates.FragmentMessage;

import java.util.ArrayList;
import java.util.List;

import weatherapp.awesomeapps.com.weatherapp.R;

public class MainActivity extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    FragmentMessage fMsg;
    android.support.v4.app.FragmentManager fm;
    android.support.v4.app.Fragment hourlyFrag, dailyFrag;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    private FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_FRIENDS = "friends";
    public static String CURRENT_TAG = TAG_HOME;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    // toolbar titles respected to selected nav menu item
    private String[] fragmentTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "access fine location "+ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION));
        Log.d(TAG, "access fine location "+ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED)
        {
            String[] abc = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,abc,1);
        }
        else
            initializeFragments();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 1)
        {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED)
            {
                initializeFragments();
            }
            else
                return;
        }
    }

    void initializeFragments()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hourlyFrag =new HourlyFragment();
        dailyFrag = new DailyFragment();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        LocationGetter lg = new LocationGetter();
        final Location location = lg.getLocation(this);
        Bundle bundle = new Bundle();
        bundle.putDouble("longitude",location.getLongitude());
        bundle.putDouble("latitude",location.getLatitude());
        ((HourlyFragment)hourlyFrag).sendFragmentMessage(bundle);

        // load toolbar titles from string resources
        fragmentTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment( hourlyFrag, "HOURLY");
        adapter.addFragment(dailyFrag, "DAILY");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    private void addFragment(int fragmentId, android.support.v4.app.Fragment frag)
//    {
//        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//        ft.add(fragmentId, frag);
//        ft.commit();
//        Log.d("ADDING FRAG ", frag.toString());
//        checkFragments();
//    }
//
//    private void addFragmentOnStack(int fragmentId, android.support.v4.app.Fragment frag)
//    {
//        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//        ft.add(fragmentId, frag);
//        ft.addToBackStack(null);
//        ft.commit();
//    }
//
//    private void replaceFragment(android.support.v4.app.Fragment fragToReplace)
//    {
//        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragcontainer, fragToReplace);
//        ft.commit();
//        checkFragments();
//    }
//
//    private void removeFragment(int fragmentId)
//    {
//        android.support.v4.app.Fragment fragment = fm.findFragmentById(fragmentId);
//        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//        System.out.println("REMOVING FRAG " + fragment.toString());
//        ft.remove(fragment);
//        ft.commit();
//        checkFragments();
//    }
//
//    void checkFragments()
//    {
//        Log.d("CHILD COUNT ", "" + ((FrameLayout) (findViewById(R.id.fragcontainer))).getChildCount());
//        for (int i = 0; i < ((FrameLayout) (findViewById(R.id.fragcontainer))).getChildCount(); i++)
//        {
//            Log.w("children == >> ", "" + ((FrameLayout) (findViewById(R.id.fragcontainer))).getChildAt(i).getTag());
//        }
//    }

}
