package com.example.uridongnefc;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReverseGeocoding {

    @SerializedName("status")
    Status StatusObject;

    @SerializedName("results")
    List<Result> results = new ArrayList<Result>();

    // Getter Methods
    public Status getStatus() {
        return StatusObject;
    }

    public List<Result> getResults() {
        return results;
    }

    // Setter Methods
    public void setStatus(Status statusObject) {
        this.StatusObject = statusObject;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ReverseGeocoding{" +
                "StatusObject=" + StatusObject +
                ", results=" + results +
                '}';
    }
}
