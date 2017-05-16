package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by farooq on 11-May-17.
 */

public class IronDetector implements Parcelable {



    ArrayList<AgeSelection> age;
    ArrayList<Instruction> instructions;
    ArrayList<ArrowCalculationRange> arrowCalcRanges;
    ArrayList<Category> categories;

    Double RDA;
    String unit;
    String commaSign;
    String commaDigits;
    String RDAText;


    String calcIronText;

    public IronDetector()
    {

    }

    public IronDetector(Parcel p) {

        //this.age = p.readString();
        this.RDA = Double.parseDouble(p.readString());
        this.unit = p.readString();
        this.commaSign = p.readString();
        this.commaDigits = p.readString();
        this.RDAText = p.readString();
        this.calcIronText = p.readString();

    }
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<ArrowCalculationRange> getArrowCalculationRanges() {
        return arrowCalcRanges;
    }

    public void setArrowCalculationRanges(ArrayList<ArrowCalculationRange> ranges) {
        this.arrowCalcRanges = ranges;
    }

    public ArrayList<AgeSelection> getAge() {
        return age;
    }

    public void setAge(ArrayList<AgeSelection> age) {
        this.age = age;
    }

    public ArrayList<Instruction> getInstruction() {
        return instructions;
    }

    public void setInstruction(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public String getCalcIronText() {
        return calcIronText;
    }

    public void setCalcIronText(String calcIronText) {
        this.calcIronText = calcIronText;
    }


    public String getRDA() {
        if(RDA != null)
            return RDA.toString();
        else
            return "0";
    }

    public void setRDA(Double RDA) {
        this.RDA = RDA;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCommaSign() {
        return commaSign;
    }

    public void setCommaSign(String commaSign) {
        this.commaSign = commaSign;
    }

    public String getCommaDigits() {
        return commaDigits;
    }

    public void setCommaDigits(String commaDigits) {
        this.commaDigits = commaDigits;
    }

    public String getRDAText() {
        return RDAText;
    }

    public void setRDAText(String RDAText) {
        this.RDAText = RDAText;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getRDA().toString());
        dest.writeString(getUnit());
        dest.writeString(getCommaSign());
        dest.writeString(getCommaDigits());
        dest.writeString(getRDAText());
        dest.writeString(getCalcIronText());
    }

    public static final Parcelable.Creator<IronDetector> CREATOR = new Parcelable.Creator<IronDetector>() {
        public IronDetector createFromParcel(Parcel p) {
            IronDetector ironDetector = new IronDetector(p);
            if (ironDetector == null) {
                throw new RuntimeException("Failed to unparcel ironDetector");
            }
            return ironDetector;
        }
        public IronDetector[] newArray(int size) {
            return new IronDetector[size];
        }
    };
}
