package com.sw.capstone.gajoku;

public class Url_SignUp {
    public final String url = "http://gajoku-env-1.remcewpict.ap-northeast-2.elasticbeanstalk.com/login";

    private String id;
    private String password;
    private String phone;

    Url_SignUp(String id, String password, String phone){
        this.id = id;
        this.password = password;
        this.phone = phone;
    }

    public String getPostDataParameter(){
        String dataParameter = "";
        dataParameter += "id=" + id;
        dataParameter += "&password=" + password;
        dataParameter += "&phone_number=" + phone;

        return dataParameter;
    }

}
