package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 15-May-17.
 */

public class ArrowCalculationRange implements Parcelable {

    private double min;
    private double max;
    private String title;
    private String text;

    public ArrowCalculationRange() {
    }

    public ArrowCalculationRange(Parcel p) {
        this.min = p.readDouble();
        this.max = p.readDouble();
        this.title = p.readString();
        this.text = p.readString();

    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getRangeText() {
        return (int)(min*100) + " - " + (int)(max*100) + "%";
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

    public boolean isInRange(double value) {
        return value >= min && value <= max;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(getMin());
        dest.writeDouble(getMax());
        dest.writeString(getTitle());
        dest.writeString(getText());
    }

    public static final Parcelable.Creator<ArrowCalculationRange> CREATOR = new Parcelable.Creator<ArrowCalculationRange>() {
        public ArrowCalculationRange createFromParcel(Parcel p) {
            ArrowCalculationRange range= new ArrowCalculationRange(p);
            if (range == null) {
                throw new RuntimeException("Failed to unparcel ArrowCalculationRange");
            }
            return range;
        }

        public ArrowCalculationRange[] newArray(int size) {
            return new ArrowCalculationRange[size];
        }
    };
}
