package com.e2esp.nestleironcalculator.utils;

import android.content.Context;

import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.Product;

import java.util.ArrayList;

/**
 * Created by farooq on 22-May-17.
 */

public class Utility {


    public static void calculateIron(Context context) {

        double totalIron =0.0;
        ArrayList<Product> selectedProducts = ((NestleIronCalculatorApp) context.getApplicationContext()).getSelectedProducts();

        for(Product prod: selectedProducts)
        {
            totalIron += prod.getIronPerPortion() * ( Integer.parseInt(prod.getSelectedSize()) / Integer.parseInt(prod.getPortionSize()) );
        }

        ((NestleIronCalculatorApp) context.getApplicationContext()).setTotalIron(totalIron);

    }
}