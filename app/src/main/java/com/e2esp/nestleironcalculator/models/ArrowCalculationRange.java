package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 15-May-17.
 */

public class ArrowCalculationRange implements Parcelable {

    private double min;
    private double max;
    private double position;
    private String title;
    private String resultText;

    public ArrowCalculationRange() {
    }

    public ArrowCalculationRange(Parcel p) {
        this.min = p.readDouble();
        this.max = p.readDouble();
        this.position = p.readDouble();
        this.title = p.readString();
        this.resultText = p.readString();

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

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
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
        dest.writeDouble(getPosition());
        dest.writeString(getTitle());
        dest.writeString(getResultText());
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
