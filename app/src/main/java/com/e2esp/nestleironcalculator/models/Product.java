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
    private String portionSize;
    private Double ironPer100mg;
    private Double ironPerPortion;
    private String title;
    private String selectedSize;
    private boolean isSelected;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private boolean isVisible;

    public Product()
    {

    }
    public Product(Parcel p) {

        this.id = p.readString();
        this.name = p.readString();
        this.unit = p.readString();
        this.portionSize = p.readString();
        this.ironPer100mg = Double.parseDouble(p.readString());
        this.ironPerPortion = Double.parseDouble(p.readString());
        this.title = p.readString();
        this.selectedSize = p.readString();

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

    public String getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(String portionSize) {
        this.portionSize = portionSize;
    }

    public Double getIronPer100mg() {
        return ironPer100mg;
    }

    public void setIronPer100mg(Double ironPer100mg) {
        this.ironPer100mg = ironPer100mg;
    }

    public Double getIronPerPortion() {
        return ironPerPortion;
    }

    public void setIronPerPortion(Double ironPerPortion) {
        this.ironPerPortion = ironPerPortion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
        dest.writeString( getPortionSize());
        dest.writeString( getIronPer100mg().toString());
        dest.writeString( getIronPerPortion().toString());
        dest.writeString( getTitle());
        dest.writeString( getSelectedSize());
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
