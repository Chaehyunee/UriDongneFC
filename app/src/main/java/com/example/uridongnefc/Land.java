package com.example.uridongnefc;

import com.google.gson.annotations.SerializedName;

public class Land {

    @SerializedName("number1")
    private String number1;

    @SerializedName("number2")
    private String number2;

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }
}
