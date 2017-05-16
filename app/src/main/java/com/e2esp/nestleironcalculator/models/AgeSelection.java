package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 11-May-17.
 */

public class AgeSelection  implements Parcelable {

    private String ageSlab;
    private Double RDA;
    private String text;
    private String textHint;
    private String textHintTitle;

    public AgeSelection()
    {

    }

    public AgeSelection(Parcel p) {

        this.ageSlab = p.readString();
        this.RDA = Double.parseDouble(p.readString());
        this.text = p.readString();
        this.textHint = p.readString();

    }

    public AgeSelection(String ageSlab, Double RDA, String text, String textHint) {
        this.ageSlab = ageSlab;
        this.RDA = RDA;
        this.text = text;
        this.textHint = textHint;
    }


    public String getAgeSlab() {
        return ageSlab;
    }

    public void setAgeSlab(String ageSlab) {
        this.ageSlab = ageSlab;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextHint() {
        return textHint;
    }

    public void setTextHint(String textHint) {
        this.textHint = textHint;
    }

    public String getTextHintTitle() {
        return textHintTitle;
    }

    public void setTextHintTitle(String textHintTitle) {
        this.textHintTitle = textHintTitle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAgeSlab());
        dest.writeString(getRDA().toString());
        dest.writeString(getText());
        dest.writeString(getTextHint());
    }
    public static final Parcelable.Creator<AgeSelection> CREATOR = new Parcelable.Creator<AgeSelection>() {
        public AgeSelection createFromParcel(Parcel p) {
            AgeSelection ageSelection = new AgeSelection(p);
            if (ageSelection == null) {
                throw new RuntimeException("Failed to unparcel ageSelection");
            }
            return ageSelection;
        }
        public AgeSelection[] newArray(int size) {
            return new AgeSelection[size];
        }
    };
}
