package com.example.uridongnefc;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("code")
    private float code;

    @SerializedName("name")
    private String name;

    @SerializedName("message")
    private String message;


    // Getter Methods
    public float getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods
    public void setCode(float code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
