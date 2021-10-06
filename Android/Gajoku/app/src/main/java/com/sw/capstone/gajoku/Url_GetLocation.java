package com.sw.capstone.gajoku;


public class Url_GetLocation {
    private final String serverUrl = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/login/detail?";
    /*
    private final String tMapUrl = "https://api2.sktelecom.com/tmap/geo/geocoding?";
    private final String version = "1";
    private final String appkey = "c5a1cb6b-8b70-4984-900f-7a1908178078";
    private final String callback = "application/javascript";
    */

    // 모든 String을 UTF-8로 변환해 넣어야 한다.
    /*
    private String city_do; // 시 , 도 명칭. - siName
    private String gu_gun;  // 군/구 명칭. 없는 경우 필수가 아님 - si_gun_guName
    private String dong;    // 도로명 주소. roadName + building_mainNum
    private String bungi;   // 부번 있으면 jibun_mainNum-jibun_subNum 없으면 jibun_mainNum, mtYn이 1이라면 앞에 '산'
    */
    private String jibunAddr;

    /*
    private final String addressFlag = "F02";
    private final String coordType = "WGS84GEO";
    */

    Url_GetLocation(String jibunAddr){
        this.jibunAddr = jibunAddr;
    }

    /*
    Url_GetLocation(String siNm, String sggNm, String rn, String buldMnnm,
                    String lnbrMnnm, String lnbrSlno, String mtYn){
        this.city_do = siNm;
        this.gu_gun = sggNm;
        if(buldMnnm.equals("")){
            this.dong = rn;
        } else {
            this.dong = rn + buldMnnm;
        }
        this.bungi = "";
        if(mtYn.equals("1")){
            this.bungi += "산";
        }
        if(lnbrSlno.equals("")){
            this.bungi += lnbrMnnm;
        } else {
            this.bungi += lnbrMnnm + "-" + lnbrSlno;
        }

        // UTF-8 Encoding
        try {
            this.city_do = URLEncoder.encode(this.city_do, "UTF-8");
            this.gu_gun = URLEncoder.encode(this.gu_gun, "UTF-8");
            this.dong = URLEncoder.encode(this.dong, "UTF-8");
            this.bungi = URLEncoder.encode(this.bungi, "UTF-8");
        } catch (Exception e){
            System.out.println("Encoding error");
        }
    }
    */

    public String getApiUrl(){
        /*
        String apiUrl = tMapUrl;
        apiUrl += "version=" + version;
        apiUrl += "&callback=" + callback;
        apiUrl += "&appkey=" + appkey;
        apiUrl += "&city_do=" + city_do;
        apiUrl += "&gu_gun=" + gu_gun;
        apiUrl += "&dong=" + dong;
        apiUrl += "&bungi=" + bungi;
        apiUrl += "&addressFlag=" + addressFlag;
        apiUrl += "&coordType=" + coordType;
        */

        String apiUrl = serverUrl;
        apiUrl += "address=" + jibunAddr;

        return apiUrl;
    }

    public String postApiUrlParameter(){
        return "";
    }
}
