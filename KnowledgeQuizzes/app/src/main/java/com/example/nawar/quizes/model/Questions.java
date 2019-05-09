package com.example.nawar.quizes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Questions implements Parcelable {

    private Integer responseCode;
    private List<Result> results;

    public Questions(Parcel in) {

        results = in.createTypedArrayList(Result.CREATOR);
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeTypedList(results);
    }
}