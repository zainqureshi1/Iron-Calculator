package com.e2esp.nestleironcalculator.utils;

import android.content.Context;

import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.Product;

import java.util.ArrayList;

/**
 * Created by farooq on 22-May-17.
 */

public class Utility {

    public static double ball_top_padding = .79;
    public static double ball_bottom_padding = .2;
    public static double ball_position = 1 - (ball_bottom_padding +  ball_top_padding);


    public static void calculateIron(Context context) {

        double totalIron =0.0;

        ArrayList<Category> categories= ((NestleIronCalculatorApp) context.getApplicationContext()).ironDetector.getCategories();

        for(Category cat: categories) {
            for (Product prod : cat.getProducts()) {
                if(prod.isSelected())
                    totalIron += prod.getIronPerPortion() * (Integer.parseInt(prod.getSelectedSize()) / Integer.parseInt(prod.getPortionSize()));
            }
        }

        ((NestleIronCalculatorApp) context.getApplicationContext()).setTotalIron(totalIron);




    }
    public static double calc_ball_top_padding() {
        double iron_delta = ((NestleIronCalculatorApp) NestleIronCalculatorApp.getAppContext()).getTotalIron() / 9.3;

        ball_top_padding = .79 - iron_delta;// ball_position - ball_position * (  iron_delta > 1 ? 1 : iron_delta);
        return ball_top_padding;
    }
}