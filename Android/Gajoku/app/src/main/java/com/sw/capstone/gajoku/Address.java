package com.sw.capstone.gajoku;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Address {
    String buildingName; // 빌딩 이름
    String jibunAddress; // 지번 주소
    String roadAddress; // 도로명 주소
    String siName; // 시 이름 "군포시, 서울특별시"
    String si_gun_guName; // 시,군,구 이름
    String roadName; // 도로명 이름
    String building_mainNum; // 건물 본번
    String jibun_mainNum; // 지번 본번 (번지)
    String jibun_subNum; // 지번 부번 (호) 부번이 없는 경우 = 0
    String mountain; // 산 여부 (0 = 대지, 1 = 산)

    Address(String buildingName, String jibunAddress, String roadAddress, String siNm,
            String sggNm, String rn, String buldMnnm, String lnbrMnnm, String lnbrSlno,
            String mtYn){
        if(buildingName.equals("")){
            this.buildingName = jibunAddress;
        } else {
            this.buildingName = buildingName;
        }
        this.jibunAddress = jibunAddress;
        this.roadAddress = roadAddress;
        this.siName = siNm;
        this.si_gun_guName = sggNm;
        this.roadName = rn;
        this.building_mainNum = buldMnnm;
        this.jibun_mainNum = lnbrMnnm;
        this.jibun_subNum = lnbrSlno;
        this.mountain = mtYn;
    }
}
