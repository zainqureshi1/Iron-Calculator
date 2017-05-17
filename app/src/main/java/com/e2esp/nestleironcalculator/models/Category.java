package com.e2esp.nestleironcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by farooq on 15-May-17.
 */

public class Category  implements Parcelable {

    private String categoryId;
    private String categoryName;
    private ArrayList<Product> products;

    public Category()
    {}

    public Category(Parcel p) {

        this.categoryId = p.readString();
        this.categoryName= p.readString();

    }
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( getCategoryId() );
        dest.writeString( getCategoryName());
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
