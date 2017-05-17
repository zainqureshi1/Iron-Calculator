package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 16-May-17.
 */

public class Popup implements Parcelable {

    private String letsProceed;
    private String dontShowThisNoticeAgain;
    private String noMilkText;
    private int maxMilkLimit;
    private String maxMilkText;
    private int maxSolidLimit;
    private String maxSolidText;

    public Popup() {
    }

    public Popup(Parcel p) {

        this.letsProceed= p.readString();
        this.dontShowThisNoticeAgain = p.readString();
        this.noMilkText = p.readString();
        this.maxMilkLimit = Integer.parseInt( p.readString());
        this.maxMilkText= p.readString();
        this.maxSolidLimit = Integer.parseInt( p.readString());
        this.maxSolidText= p.readString();

    }

    public String getLetsProceed() {
        return letsProceed;
    }

    public void setLetsProceed(String letsProceed) {
        this.letsProceed = letsProceed;
    }

    public String getDontShowThisNoticeAgain() {
        return dontShowThisNoticeAgain;
    }

    public void setDontShowThisNoticeAgain(String dontShowThisNoticeAgain) {
        this.dontShowThisNoticeAgain = dontShowThisNoticeAgain;
    }

    public String getNoMilkText() {
        return noMilkText;
    }

    public void setNoMilkText(String noMilkText) {
        this.noMilkText = noMilkText;
    }

    public int getMaxMilkLimit() {
        return maxMilkLimit;
    }

    public void setMaxMilkLimit(int maxMilkLimit) {
        this.maxMilkLimit = maxMilkLimit;
    }

    public String getMaxMilkText() {
        return maxMilkText;
    }

    public void setMaxMilkText(String maxMilkText) {
        this.maxMilkText = maxMilkText;
    }

    public int getMaxSolidLimit() {
        return maxSolidLimit;
    }

    public void setMaxSolidLimit(int maxSolidLimit) {
        this.maxSolidLimit = maxSolidLimit;
    }

    public String getMaxSolidText() {
        return maxSolidText;
    }

    public void setMaxSolidText(String maxSolidText) {
        this.maxSolidText = maxSolidText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getLetsProceed() );
        dest.writeString( getDontShowThisNoticeAgain());
        dest.writeString( getNoMilkText());
        dest.writeString( String.valueOf(getMaxMilkLimit()));
        dest.writeString( getMaxMilkText());
        dest.writeString( String.valueOf(getMaxSolidLimit()));
        dest.writeString( getMaxSolidText());

    }

    public static final Parcelable.Creator<Instruction> CREATOR = new Parcelable.Creator<Instruction>() {
        public Instruction createFromParcel(Parcel p) {
            Instruction instruction = new Instruction(p);
            if (instruction == null) {
                throw new RuntimeException("Failed to unparcel Detail DetailInstructions");
            }
            return instruction;
        }

        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };

}
