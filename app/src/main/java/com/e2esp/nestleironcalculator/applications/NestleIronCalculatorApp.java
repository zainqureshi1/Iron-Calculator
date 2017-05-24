package com.e2esp.nestleironcalculator.applications;

import android.app.Application;
import android.content.Context;

import com.e2esp.nestleironcalculator.models.IronDetector;
import com.e2esp.nestleironcalculator.utils.Consts;

import java.util.ArrayList;

/**
 * Created by farooq on 11-May-17.
 */

public class NestleIronCalculatorApp  extends Application {
    private static Context context;
    public IronDetector ironDetector;
    //private int ageSlabSelected;
    private ArrayList<Double> totalIron;
    private double totalMilk;
    private double totalSolidFood;
    public String visibleProductId = "";

    public void onCreate() {
        super.onCreate();
        NestleIronCalculatorApp.context = getApplicationContext();
        ironDetector = new IronDetector();
        totalIron = new ArrayList<>();
    }

    public static Context getAppContext() {
        return NestleIronCalculatorApp.context;
    }

//    public int getAgeSlabSelected() {
//        return ageSlabSelected;
//    }
//
//    public void setAgeSlabSelected(int ageSlabSelected) {
//        this.ageSlabSelected = ageSlabSelected;
//    }

    public void setCalculatedValues(ArrayList<Double> totalIron, double totalMilk, double totalSolidFood) {
        this.totalIron.clear();
        this.totalIron.addAll(totalIron);
        this.totalMilk = totalMilk;
        this.totalSolidFood = totalSolidFood;
    }

    public ArrayList<Double> getTotalIron() {
        return totalIron;
    }

    public double getTotalIronValue() {
        double totalValue = 0.0;
        for (Double categoryIron : totalIron) {
            totalValue += categoryIron;
        }
        return totalValue;
    }

    public boolean hasReachedRequiredIronLimit() {
        return getTotalIronValue() >= Consts.requiredIronIntake;
    }

    public boolean hasExceededMaxMilkLimit() {
        try {
            return totalMilk > ironDetector.getPopups().get(0).getMaxMilkLimit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean hasExceededMaxSolidFoodLimit() {
        try {
            return totalSolidFood > ironDetector.getPopups().get(0).getMaxSolidLimit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean hasAddedMilk() {
        return totalMilk > 0;
    }

}
