package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by farooq on 15-May-17.
 */

public class Instruction implements Parcelable {

    ArrayList<DetailInstructions> detailInstructions;
    private String checkIntake;
    private String legalCopyAsterisk;
    private String legalCopy;
    private String tryItNow;
    private String dontShowThisNoticeAgain;

    public Instruction() {
    }

    public Instruction(Parcel p) {

        this.checkIntake = p.readString();
        this.legalCopyAsterisk = p.readString();
        this.legalCopy = p.readString();
        this.tryItNow = p.readString();
        this.dontShowThisNoticeAgain = p.readString();

    }

    public String getCheckIntake() {
        return checkIntake;
    }

    public void setCheckIntake(String checkIntake) {
        this.checkIntake = checkIntake;
    }
    public ArrayList<DetailInstructions> getDetailInstruction() {
        return detailInstructions;
    }

    public void setDetailInstruction(ArrayList<DetailInstructions> detailInstructions) {
        this.detailInstructions = detailInstructions;
    }
    public String getLegalCopyAsterisk() {
        return legalCopyAsterisk;
    }

    public void setLegalCopyAsterisk(String legalCopyAsterisk) {
        this.legalCopyAsterisk = legalCopyAsterisk;
    }

    public String getLegalCopy() {
        return legalCopy;
    }

    public void setLegalCopy(String legalCopy) {
        this.legalCopy = legalCopy;
    }
    public String getTryItNow() {
        return tryItNow;
    }

    public void setTryItNow(String tryItNow) {
        this.tryItNow = tryItNow;
    }

    public String getDontShowThisNoticeAgain() {
        return dontShowThisNoticeAgain;
    }

    public void setDontShowThisNoticeAgain(String dontShowThisNoticeAgain) {
        this.dontShowThisNoticeAgain = dontShowThisNoticeAgain;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getCheckIntake() );
        dest.writeString( getLegalCopyAsterisk());
        dest.writeString( getLegalCopy());
        dest.writeString( getTryItNow());
        dest.writeString( getDontShowThisNoticeAgain());
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
