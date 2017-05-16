package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 15-May-17.
 */

public class DetailInstructions implements Parcelable {

    private int ins_Number;
    private String instructionText;

    public DetailInstructions() {
    }

    public DetailInstructions(Parcel p) {

        this.ins_Number = Integer.parseInt(p.readString());
        this.instructionText = p.readString();
    }

    public int getIns_Number() {
        return ins_Number;
    }

    public void setIns_Number(int ins_Number) {
        this.ins_Number = ins_Number;
    }

    public String getIntructionText() {
        return instructionText;
    }

    public void setIntructionText(String intructionText) {
        this.instructionText = intructionText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( String.valueOf(getIns_Number()) );
        dest.writeString(getIntructionText());
    }

    public static final Parcelable.Creator<DetailInstructions> CREATOR = new Parcelable.Creator<DetailInstructions>() {
        public DetailInstructions createFromParcel(Parcel p) {
            DetailInstructions detailInstructions = new DetailInstructions(p);
            if (detailInstructions == null) {
                throw new RuntimeException("Failed to unparcel DetailInstructions");
            }
            return detailInstructions;
        }

        public DetailInstructions[] newArray(int size) {
            return new DetailInstructions[size];
        }
    };


}
