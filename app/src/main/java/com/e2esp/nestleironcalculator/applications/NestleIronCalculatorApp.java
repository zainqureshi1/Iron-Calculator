package com.e2esp.nestleironcalculator.applications;

import android.app.Application;
import android.content.Context;

import com.e2esp.nestleironcalculator.models.IronDetector;

/**
 * Created by farooq on 11-May-17.
 */

public class NestleIronCalculatorApp  extends Application {
    private static Context context;
    public IronDetector ironDetector;
    private String ageSlabSelected;

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


}
