package com.sw.capstone.gajoku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuListPager extends Fragment implements
   MenuRecyclerAdapter.OnMenuSelectedListener{

    private OnMenuItemSelected mOnMenuItemSelected;

    ArrayList<Menu> menuList;

    RecyclerView recyclerView;
    MenuRecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    public MenuListPager(){
        super();
    }

    public static Fragment newInstance(ArrayList<Menu> menuArrayList){
        MenuListPager f = new MenuListPager();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("menuList", menuArrayList);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle bundle = getArguments();
        try {
            menuList = bundle.getParcelableArrayList("menuList");
        } catch (NullPointerException e){
            menuList = new ArrayList<>();
        }

        view = inflater.inflate(R.layout.pager_menu, container, false);
        final TextView text_noMenu = view.findViewById(R.id.textView_noMenu);
        final ImageView image_noMenu = view.findViewById(R.id.imageView_noMenu);

        if(menuList.size() != 0) {
            recyclerView = (RecyclerView) view.findViewById(R.id.menu_recyclerView);
            layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerAdapter = new MenuRecyclerAdapter(menuList, getContext());
            recyclerAdapter.setOnMenuSelectedListener(MenuListPager.this);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            text_noMenu.setVisibility(View.VISIBLE);
            image_noMenu.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity = null;
        if(context instanceof Activity){
            activity = (Activity) context;
        }

        try{
            mOnMenuItemSelected = (OnMenuItemSelected) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuItemSelected");
        }
    }

    @Override
    public void onMenuSelected(int position){
        Menu menu = menuList.get(position);

        mOnMenuItemSelected.onMenuSelected(menu);
    }

    public interface OnMenuItemSelected {
        public void onMenuSelected(Menu menu);
    }
}