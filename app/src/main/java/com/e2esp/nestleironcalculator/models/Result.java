package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by farooq on 16-May-17.
 */

public class Result implements Parcelable {

    private String header;
    private String estimatedIron;
    private String RDAHint;
    private String richFoodsText;

    private ArrayList<Range> ranges;

    public Result()
    {}

    public Result(Parcel p) {

        this.header = p.readString();
        this.estimatedIron = p.readString();
        this.RDAHint = p.readString();
        this.richFoodsText = p.readString();

    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getEstimatedIron() {
        return estimatedIron;
    }

    public void setEstimatedIron(String estimatedIron) {
        this.estimatedIron = estimatedIron;
    }

    public String getRDAHint() {
        return RDAHint;
    }

    public void setRDAHint(String RDAHint) {
        this.RDAHint = RDAHint;
    }

    public String getRichFoodsText() {
        return richFoodsText;
    }

    public void setRichFoodsText(String richFoodsText) {
        this.richFoodsText = richFoodsText;
    }

    public ArrayList<Range> getRanges() {
        return ranges;
    }

    public void setRanges(ArrayList<Range> ranges) {
        this.ranges = ranges;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getHeader() );
        dest.writeString( getEstimatedIron());
        dest.writeString( getRDAHint());
        dest.writeString( getRichFoodsText());
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
