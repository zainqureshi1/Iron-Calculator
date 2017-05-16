package com.e2esp.nestleironcalculator.applications;

import android.app.Application;
import android.content.Context;

/**
 * Created by farooq on 11-May-17.
 */

public class NestleIronCalculatorApp  extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        NestleIronCalculatorApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return NestleIronCalculatorApp.context;
    }
}
