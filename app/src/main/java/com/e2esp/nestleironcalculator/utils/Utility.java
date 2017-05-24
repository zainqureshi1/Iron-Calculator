package com.e2esp.nestleironcalculator.utils;

import android.content.Context;

import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.ArrowCalculationRange;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.IronDetector;
import com.e2esp.nestleironcalculator.models.Product;

import java.util.ArrayList;

/**
 * Created by farooq on 22-May-17.
 */

public class Utility {




    public static void calculateIron(Context context) {
        ArrayList<Double> totalIron = new ArrayList<>();
        double totalMilk = 0.0;
        double totalSolidFood = 0.0;

        ArrayList<Category> categories = ((NestleIronCalculatorApp) context.getApplicationContext()).ironDetector.getCategories();
        for(Category cat: categories) {
            double categoryIron = 0.0;
            for (Product prod : cat.getProducts()) {
                if(prod.isSelected()) {
                    int selectedPortions = (int) (prod.getSelectedSize() / prod.getPortionSize());
                    categoryIron += prod.getIronPerPortion() * selectedPortions;
                    if (prod.isMilk()) {
                        totalMilk += prod.getPortionSize() * selectedPortions;
                    }
                    if (prod.isSolidFood()) {
                        totalSolidFood += prod.getPortionSize() * selectedPortions;
                    }
                }
            }
            totalIron.add(categoryIron);
        }

        ((NestleIronCalculatorApp) context.getApplicationContext()).setCalculatedValues(totalIron, totalMilk, totalSolidFood);
    }

    public static double calc_ball_top_padding() {
        double ball_top_padding  = Consts.ball_max_padding;

        double totalIronValuePercentage = ((NestleIronCalculatorApp) NestleIronCalculatorApp.getAppContext()).getTotalIronValue()/ Consts.requiredIronIntake * 100;
        double top_padding_TotalPercent = 100 + (100 - (Consts.ball_max_padding * 100));

        double topspace = (Consts.ball_max_padding - (totalIronValuePercentage/top_padding_TotalPercent * 100)/100);

        ball_top_padding = ( topspace < 0 ? 0 :  topspace)  ;//iron_delta;// ball_position - ball_position * (  iron_delta > 1 ? 1 : iron_delta);
        return ball_top_padding;
    }

    public static String showBo()
    {
        String img_bo = "bo_breast_milk";//arrow_bo_25"


        double totalIron = ((NestleIronCalculatorApp) NestleIronCalculatorApp.getAppContext()).getTotalIronValue();

        if(totalIron > 0) {

            double RDA = 0.0;
            double bo_value = 0.0;
            int index = 0;

            IronDetector ironDetector = (((NestleIronCalculatorApp) NestleIronCalculatorApp.getAppContext()).ironDetector);
            ArrayList<AgeSelection> ages = (ArrayList<AgeSelection>) ironDetector.getAge();
            ArrayList<ArrowCalculationRange> ranges = ironDetector.getArrowCalcRanges();


            for (AgeSelection age : ages) {
                if (age.isSelected()) {
                    RDA = Double.parseDouble(age.getRDA());
                    break;
                }

            }
            bo_value = Math.min(totalIron / RDA, 1.0);

            for (ArrowCalculationRange range : ranges) {
                index += 25;
                if (range.isInRange(bo_value)) {
                    break;
                }

            }

            img_bo = "arrow_bo_"+index;


        }

        return  img_bo;
    }

}