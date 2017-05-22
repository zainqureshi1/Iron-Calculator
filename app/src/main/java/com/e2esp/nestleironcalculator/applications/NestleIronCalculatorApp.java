package com.e2esp.nestleironcalculator.applications;

import android.app.Application;
import android.content.Context;

import com.e2esp.nestleironcalculator.models.IronDetector;
import com.e2esp.nestleironcalculator.models.Product;

import java.util.ArrayList;

/**
 * Created by farooq on 11-May-17.
 */

public class NestleIronCalculatorApp  extends Application {
    private static Context context;
    public IronDetector ironDetector;
    private String ageSlabSelected;
    private ArrayList<Product> selectedProducts = new ArrayList<Product>();
    private double totalIron;

    public void onCreate() {
        super.onCreate();
        NestleIronCalculatorApp.context = getApplicationContext();
        ironDetector = new IronDetector();

    }

    public static Context getAppContext() {
        return NestleIronCalculatorApp.context;
    }

    public String getAgeSlabSelected() {
        return ageSlabSelected;
    }

    public void setAgeSlabSelected(String ageSlabSelected) {
        this.ageSlabSelected = ageSlabSelected;
    }
    public ArrayList<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(ArrayList<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }
    public double getTotalIron() {
        return totalIron;
    }

    public void setTotalIron(double totalIron) {
        this.totalIron = totalIron;
    }



}
