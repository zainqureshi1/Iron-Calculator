package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 16-May-17.
 */

public class Range implements Parcelable {

    private Double min;
    private Double max;
    private String title;
    private String text;

    public Range()
    {}

    public Range(Parcel p) {

        this.min = Double.parseDouble(p.readString());
        this.max = Double.parseDouble(p.readString());
        this.title = p.readString();
        this.text = p.readString();

    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getMin().toString() );
        dest.writeString( getMax().toString());
        dest.writeString( getTitle());
        dest.writeString( getText());
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(Parcel p) {
            Result result = new Result(p);
            if (result == null) {
                throw new RuntimeException("Failed to unparcel Result");
            }
            return result;
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

}
