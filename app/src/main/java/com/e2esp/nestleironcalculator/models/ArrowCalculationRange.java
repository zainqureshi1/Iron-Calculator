package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 15-May-17.
 */

public class ArrowCalculationRange implements Parcelable {

    private Double min;
    private Double max;
    private Double position;
    private String title;
    private String resultText;

    public ArrowCalculationRange() {
    }

    public ArrowCalculationRange(Parcel p) {

        this.min = Double.parseDouble( p.readString());
        this.max = Double.parseDouble(p.readString());
        this.position = Double.parseDouble(p.readString());
        this.title = p.readString();
        this.resultText = p.readString();

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

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getMin().toString() );
        dest.writeString( getMax().toString());
        dest.writeString( getPosition().toString());
        dest.writeString( getTitle());
        dest.writeString( getResultText());
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
