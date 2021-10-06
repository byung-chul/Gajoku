package com.sw.capstone.gajoku;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends NavigationActivity implements
    MenuListPager.OnMenuItemSelected, View.OnClickListener{

    protected ViewPager menuListPager;
    Restaurant detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final int restaurantId = getIntent().getIntExtra("restaurantId", 0);
        detail = new Restaurant(restaurantId);

        ImageView navigationIcon = findViewById(R.id.imageView_menuIcon_menu);
        navigationIcon.setOnClickListener(this);

        TextView name = findViewById(R.id.textView_restaurantName);
        name.setText(detail.name);

        TextView bucketButton = findViewById(R.id.textView_menu_bucket);
        bucketButton.setOnClickListener(this);
        TextView callButton = findViewById(R.id.textView_menu_phonecall);
        callButton.setOnClickListener(this);

        menuListPager = (ViewPager) findViewById(R.id.viewPager_menu);
        menuListPager.setAdapter(new MenuActivity.PageAdapter(getSupportFragmentManager()));

        final RadioGroup menuTypeList = (RadioGroup) findViewById(R.id.radioGroup_menu);

        menuTypeList.check(0);
        menuListPager.setCurrentItem(0);

        menuTypeList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_menu_info:
                        menuListPager.setCurrentItem(0);
                        break;
                    case R.id.radio_menu_menu:
                        menuListPager.setCurrentItem(1);
                        break;
                    case R.id.radio_menu_reservation:
                        menuListPager.setCurrentItem(2);
                        break;
                }
            }
        });

        menuListPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                menuTypeList.check(getButtonId(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MenuInformationPager.newInstance(detail);
                case 1:
                    return MenuListPager.newInstance(detail.menu);
                case 2:
                    return MenuReservationPager.newInstance(detail.id);
                default:
                    return MenuInformationPager.newInstance(detail);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public int getButtonId(int type) {
        switch (type) {
            case 0:
                return R.id.radio_menu_info;
            case 1:
                return R.id.radio_menu_menu;
            case 2:
                return R.id.radio_menu_reservation;
            default:
                return R.id.radio_menu_info;
        }
    }

    @Override
    public void onMenuSelected(Menu menu){
        StoredBucket storedBucket = new StoredBucket(this);
        Bucket bucket = new Bucket(detail.name, menu.name, menu.price, 1);
        storedBucket.storeBucket(bucket);

        Intent intent = new Intent(this, BucketListActivity.class);
        intent.putExtra("restaurantId", detail.id);
        intent.putExtra("restaurantName", detail.name);
        intent.putExtra("restaurantLeast", detail.least);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.textView_menu_bucket:
                Intent intent = new Intent(this, BucketListActivity.class);
                intent.putExtra("restaurantId", detail.id);
                intent.putExtra("restaurantName", detail.name);
                intent.putExtra("restaurantLeast", detail.least);
                startActivity(intent);
                break;
            case R.id.textView_menu_phonecall:
                Intent phonecall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + detail.phone.replaceAll("-","")));
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                }
                else
                {
                    startActivity(phonecall);
                }
                break;
            case R.id.imageView_menuIcon_menu:
                super.drawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        StoredBucket storedBucket = new StoredBucket(this);
        storedBucket.resetBucket(new ArrayList<Bucket>());

        return;
    }
}