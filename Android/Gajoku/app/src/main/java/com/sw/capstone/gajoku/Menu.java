package com.sw.capstone.gajoku;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {
    String name;
    int price;

    Menu(String name, int price){
        this.name = name;
        this.price=  price;
    }

    public Menu(Parcel in){
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>(){
        public Menu createFromParcel(Parcel in){
            return new Menu(in);
        }

        public Menu[] newArray(int size){
            return new Menu[size];
        }
    };

    public void readFromParcel(Parcel in){
        name = in.readString();
        price = in.readInt();
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }
}
