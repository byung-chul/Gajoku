package com.sw.capstone.gajoku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class RestaurantActivity extends NavigationActivity implements View.OnClickListener {

    protected ViewPager restaurantListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        ImageView navigationIcon = findViewById(R.id.imageView_menuIcon_restaurant);
        navigationIcon.setOnClickListener(this);

        final String menuType = getIntent().getStringExtra("MenuType");
        final HorizontalScrollView scrollView = findViewById(R.id.scrollView_restaurant_button);

        FoodType foodType = new FoodType();

        restaurantListView = (ViewPager) findViewById(R.id.viewPager_restaurant);
        restaurantListView.setAdapter(new PageAdapter(getSupportFragmentManager()));

        final RadioGroup menuTypeList = (RadioGroup) findViewById(R.id.radioGroup_restaurant);

        int startPosition = foodType.getTypeValue(menuType);
        menuTypeList.check(getButtonId(startPosition));
        restaurantListView.setCurrentItem(startPosition);

        menuTypeList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                switch(checkedId) {
                    case R.id.radio_restaurent_korean:
                        restaurantListView.setCurrentItem(0); break;
                    case R.id.radio_restaurent_chinese:
                        restaurantListView.setCurrentItem(1); break;
                    case R.id.radio_restaurent_chicken:
                        restaurantListView.setCurrentItem(2); break;
                    case R.id.radio_restaurent_pizza:
                        restaurantListView.setCurrentItem(3); break;
                    case R.id.radio_restaurent_bunsik:
                        restaurantListView.setCurrentItem(4); break;
                    case R.id.radio_restaurent_bojok:
                        restaurantListView.setCurrentItem(5); break;
                    case R.id.radio_restaurent_dosirak:
                        restaurantListView.setCurrentItem(6); break;
                    case R.id.radio_restaurent_fastfood:
                        restaurantListView.setCurrentItem(7); break;
                }
            }
        });


        restaurantListView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                scrollView.post(new Runnable(){
                    @Override
                    public void run(){
                        int current_pos = findViewById(getButtonId(position)).getLeft() - 150;
                        scrollView.smoothScrollTo(current_pos, 0);
                    }
                });
                menuTypeList.check(getButtonId(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class PageAdapter extends FragmentStatePagerAdapter{
        public PageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            Integer index;
            switch (position){
                case 0:  index = 0; break;
                case 1:  index = 1; break;
                case 2:  index = 2; break;
                case 3:  index = 3; break;
                case 4:  index = 4; break;
                case 5:  index = 5; break;
                case 6:  index = 6; break;
                case 7:  index = 7; break;
                default: index = 0; break;
            }
            return RestaurantListPager.newInstance(index);
        }

        @Override
        public int getCount(){
            return 8;
        }
    }

    public int getButtonId(int type){
        switch(type){
            case 0: return R.id.radio_restaurent_korean;
            case 1: return R.id.radio_restaurent_chinese;
            case 2: return R.id.radio_restaurent_chicken;
            case 3: return R.id.radio_restaurent_pizza;
            case 4: return R.id.radio_restaurent_bunsik;
            case 5: return R.id.radio_restaurent_bojok;
            case 6: return R.id.radio_restaurent_dosirak;
            case 7: return R.id.radio_restaurent_fastfood;
            default:return R.id.radio_restaurent_korean;
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.imageView_menuIcon_restaurant:
                super.drawerLayout.openDrawer(Gravity.START);
                break;
        }
    }
}
