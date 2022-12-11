package com.example.uridongnefc;

public class PostItem {
    private String name;
    private String title;
    private String day;
    private String time;
    private String age;
    private int viewType;
    Object object;
    private String documentID;

    public PostItem(String documentID, String name, String title, String day, String timeorage, int viewType) {
        this.name = name;
        this.title = title;
        this.day = day;
        this.viewType = viewType;
        this.documentID = documentID;

        if (viewType==ViewType.TEAM_ACCOUNT) { // Team 계정이면 time 저장, age 없음
            this.time = timeorage;
            this.age = "";
        } else  {
            this.age = timeorage;
            this.time = "";
        }
    }

    public PostItem(int viewType, Object object) {
        this.viewType = viewType;
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public String getAge() {
        return age;
    }

    public int getViewType() {
        return viewType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setAge(String age) {
        this.age = age;
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

    @Override
    public String toString() {
        return "PostItem{" +
                "document ID='" + documentID + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", age='" + age + '\'' +
                ", viewType=" + viewType +
                ", object=" + object +
                '}';
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

}
