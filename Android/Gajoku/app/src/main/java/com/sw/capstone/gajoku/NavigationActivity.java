package com.sw.capstone.gajoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.sw.capstone.gajoku.api.kakao.KakaoLoginActivity;
import com.sw.capstone.gajoku.http.RequestHttp;

public abstract class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FrameLayout view_stub;
    public DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.navigation_base);
        view_stub = (FrameLayout) findViewById(R.id.view_stub);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                //getActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }
        };

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        View stubView = inflater.inflate(layoutResID, view_stub, false);
        view_stub.addView(stubView, lp);
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if (id == R.id.nav_logout_fragment) {
            StoredUserSession storedUserSession = new StoredUserSession(this);
            storedUserSession.clearUserSession();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            /* Kakao Logout
            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                    // Restart current method.
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            */
        } else if (id == R.id.nav_address_fragment) {
            startActivity(new Intent(this, SearchAddressActivity.class));
        }
        if(id == R.id.nav_order_fragment) {
            startActivity(new Intent(this, MyOrderActivity.class));
            //startActivity(new Intent(this, KakaoLoginActivity.class));
            /*
            Url_Login loginUrl = new Url_Login("111", "111");
            RequestHttp requestHttp = new RequestHttp();
            requestHttp.requestGet(loginUrl.getApiUrl());
            */
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onResume(){
        super.onResume();
        StoredUserSession storedUserSession = new StoredUserSession(this);
        String storedId = storedUserSession.getUserSession();
        if(storedId.equals("")){
            this.finishAffinity();
        }
    }
}
