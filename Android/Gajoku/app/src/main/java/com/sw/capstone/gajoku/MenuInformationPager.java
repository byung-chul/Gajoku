package com.sw.capstone.gajoku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuInformationPager extends Fragment {
    View view;

    public MenuInformationPager(){
        super();
    }

    public static Fragment newInstance(Restaurant detail){
        MenuInformationPager f = new MenuInformationPager();
        Bundle bundle = new Bundle();
        bundle.putString("Name", detail.name);
        bundle.putString("Phone", detail.phone);
        bundle.putString("Address", detail.address);
        bundle.putInt("Least", detail.least);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle bundle = getArguments();
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        String address = bundle.getString("Address");
        int least = bundle.getInt("Least");

        view = inflater.inflate(R.layout.pager_information, container, false);

        TextView nameTextView = view.findViewById(R.id.textView_info_name);
        TextView phoneTextView = view.findViewById(R.id.textView_info_phone);
        TextView addressTextView = view.findViewById(R.id.textView_info_address);
        TextView leastTextView = view.findViewById(R.id.textView_info_least);

        nameTextView.setText(name);
        phoneTextView.setText(phone);
        addressTextView.setText(address);
        leastTextView.setText(least + " 원");

        return view;
    }
}
