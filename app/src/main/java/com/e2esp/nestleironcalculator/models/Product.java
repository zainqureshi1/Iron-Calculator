package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by farooq on 15-May-17.
 */

public class Product  implements Parcelable {

    private String id;
    private String name;
    private String unit;
    private double portionSize;
    private double ironPer100mg;
    private double ironPerPortion;
    private String title;
    private double selectedSize;
    private boolean isSelected;
    private int minAge;
    private int maxAge;
    private boolean isIronRichFood;

    public Product() {

    }

    public Product(Parcel p) {
        this.id = p.readString();
        this.name = p.readString();
        this.unit = p.readString();
        this.portionSize = p.readDouble();
        this.ironPer100mg = p.readDouble();
        this.ironPerPortion = p.readDouble();
        this.title = p.readString();
        this.selectedSize = p.readDouble();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(double portionSize) {
        this.portionSize = portionSize;
    }

    public double getIronPer100mg() {
        return ironPer100mg;
    }

    public void setIronPer100mg(double ironPer100mg) {
        this.ironPer100mg = ironPer100mg;
    }

    public double getIronPerPortion() {
        return ironPerPortion;
    }

    public void setIronPerPortion(double ironPerPortion) {
        this.ironPerPortion = ironPerPortion;
    }

    public String getTitle() {
        if (title == null) {
            return "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(double selectedSize) {
        this.selectedSize = selectedSize;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isIronRichFood() {
        return isIronRichFood;
    }

    public void setIronRichFood(boolean ironRichFood) {
        isIronRichFood = ironRichFood;
    }

    public boolean isMilk() {
        return id.toLowerCase().contains("milk") || name.toLowerCase().contains("milk");
    }
    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }


    public boolean isSolidFood() {
        return unit.equals("g");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getId() );
        dest.writeString( getName());
        dest.writeString( getUnit());
        dest.writeDouble( getPortionSize());
        dest.writeDouble( getIronPer100mg());
        dest.writeDouble( getIronPerPortion());
        dest.writeString( getTitle());
        dest.writeDouble( getSelectedSize());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel p) {
            Product prod = new Product(p);
            if (prod == null) {
                throw new RuntimeException("Failed to unparcel Product");
            }
            return prod;
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

}
