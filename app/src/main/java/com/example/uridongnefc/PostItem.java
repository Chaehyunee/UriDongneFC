package com.example.uridongnefc;

public class PostItem {
    private String name;
    private String content;
    private String day;
    private String time;
    private int viewType;
    Object object;

    public PostItem(String name, String content, String day, String time, int viewType) {
        this.name = name;
        this.content = content;
        this.day = day;
        this.time = time;
        this.viewType = viewType;
    }

    public PostItem(int viewType, Object object) {
        this.viewType = viewType;
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public int getViewType() {
        return viewType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
