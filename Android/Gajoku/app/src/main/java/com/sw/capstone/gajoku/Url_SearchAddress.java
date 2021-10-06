package com.sw.capstone.gajoku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Url_SearchAddress {
    private final String url = "http://www.juso.go.kr/addrlink/addrLinkApi.do?";
    private final String confmKey = "U01TX0FVVEgyMDE4MDQxMTEzMjczMzEwNzgxMzE=";
    private final String resultType = "json";

    private int countPerPage = 5;
    private int currentPage;
    private String keyword;

    Url_SearchAddress(int currentPage, String keyword){
        this.currentPage = currentPage;
        this.keyword = keyword;
    }

    public void SetCountPerPage(int page){
        this.countPerPage = page;
    }

    public String getApiUrl(){
        String apiUrl = url;
        apiUrl += "currentPage=" + currentPage;
        apiUrl += "&countPerPage=" + countPerPage;
        apiUrl += "&keyword=" + keyword;
        apiUrl += "&confmKey=" + confmKey;
        apiUrl += "&resultType=" + resultType;

        return apiUrl;
    }

    public ArrayList<Address> jsonParse(String response){
        ArrayList<Address> addressList = new ArrayList<>();
        try {
            JSONObject res_string = new JSONObject(response);
            JSONObject res_result = res_string.getJSONObject("results");
            JSONArray res_juso = res_result.getJSONArray("juso");
            for (int i = 0; i < res_juso.length(); i++) {
                JSONObject each_juso = res_juso.getJSONObject(i);
                // 빌딩 이름
                String r_buildingName = each_juso.getString("bdNm");
                // 지번 주소
                String r_jibunAddress = each_juso.getString("jibunAddr");
                // 도로명 주소
                String r_roadAddress = each_juso.getString("roadAddr");
                // 시 이름 "군포시, 서울특별시"
                String r_siName = each_juso.getString("siNm");
                // 시, 군, 구 이름
                String r_si_gun_guName = each_juso.getString("sggNm");
                // 도로명 이름
                String r_roadName = each_juso.getString("rn");
                // 건물 본번
                String r_building_mainNum = each_juso.getString("buldMnnm");
                // 지번 본번 (번지)
                String r_bungi_mainNum = each_juso.getString("lnbrMnnm");
                // 지번 부번 (호)
                String r_bungi_subNum = each_juso.getString("lnbrSlno");
                // 산 여부
                String r_mountain = each_juso.getString("mtYn");

                Address address = new Address(
                        r_buildingName, r_jibunAddress, r_roadAddress, r_siName,
                        r_si_gun_guName, r_roadName, r_building_mainNum,
                        r_bungi_mainNum, r_bungi_subNum, r_mountain);
                addressList.add(address);
            }
        } catch (JSONException e){
            System.out.println("JSON parse error. / searchAddress");
        }

        return addressList;
    }
}
