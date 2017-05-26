package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by farooq on 11-May-17.
 */

public class IronDetector implements Parcelable {

    private ArrayList<AgeSelection> age;
    private ArrayList<Instruction> instructions;
    private ArrayList<ArrowCalculationRange> arrowCalcRanges;
    private ArrayList<Category> categories;
    private ArrayList<Result> results;
    private ArrayList<Popup> popups;

    private Double RDA;
    private String unit;
    private String commaSign;
    private String commaDigits;
    private String RDAText;

    String calcIronText;

    public IronDetector() {

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

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<AgeSelection> getAge() {
        return age;
    }

    public AgeSelection getSelectedAge() {
        AgeSelection selectedAge = null;
        for (AgeSelection ageSelection : age) {
            if (ageSelection.isSelected()) {
                selectedAge = ageSelection;
                break;
            }
        }
        return selectedAge;
    }

    public void setAge(ArrayList<AgeSelection> age) {
        this.age = age;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<ArrowCalculationRange> getArrowCalcRanges() {
        return arrowCalcRanges;
    }

    public void setArrowCalcRanges(ArrayList<ArrowCalculationRange> arrowCalcRanges) {
        this.arrowCalcRanges = arrowCalcRanges;
    }

    public ArrayList<Popup> getPopups() {
        return popups;
    }

    public void setPopups(ArrayList<Popup> popups) {
        this.popups = popups;
    }


    public String getCalcIronText() {
        return calcIronText;
    }

    public void setCalcIronText(String calcIronText) {
        this.calcIronText = calcIronText;
    }


    public String getRDA() {
        if (RDA != null)
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
