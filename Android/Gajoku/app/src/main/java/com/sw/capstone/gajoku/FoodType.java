package com.sw.capstone.gajoku;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class FoodType {
    String typeName;
    int typeValue;

    ArrayList<FoodType> foodTypes = new ArrayList<>();

    FoodType(){
        foodTypes.add(new FoodType("한식", 0));
        foodTypes.add(new FoodType("중국집", 1));
        foodTypes.add(new FoodType("치킨", 2));
        foodTypes.add(new FoodType("피자", 3));
        foodTypes.add(new FoodType("분식", 4));
        foodTypes.add(new FoodType("보쌈족발", 5));
        foodTypes.add(new FoodType("도시락", 6));
        foodTypes.add(new FoodType("패스트푸드점", 7));
    }

    private FoodType(String typeName, int typeValue){
        this.typeName = typeName;
        this.typeValue = typeValue;
    }

    public int getTypeValue(String typeName){
        for(FoodType foodType: foodTypes){
            if(typeName.equals(foodType.typeName)){
                return foodType.typeValue;
            }
        }
        return -1;
    }

    public String getTypeName(int typeValue){
        for(FoodType foodType: foodTypes){
            if(typeValue == foodType.typeValue){
                return foodType.typeName;
            }
        }
        return "";
    }
}
