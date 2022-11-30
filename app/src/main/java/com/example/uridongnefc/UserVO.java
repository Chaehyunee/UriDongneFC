package com.example.uridongnefc;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserVO implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("pw")
    private String pw;

    @SerializedName("name")
    private String name;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("region")
    private String region;

    @SerializedName("roll")
    private Boolean seller_auth;

    @SerializedName("profile_flag")
    private Boolean profile_flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getSeller_auth() {
        return seller_auth;
    }

    public void setSeller_auth(Boolean seller_auth) {
        this.seller_auth = seller_auth;
    }

    public Boolean getProfile_flag() {
        return profile_flag;
    }

    public void setProfile_flag(Boolean profile_flag) {
        this.profile_flag = profile_flag;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", seller_auth=" + seller_auth +
                ", profile_flag=" + profile_flag +
                '}';
    }
}
