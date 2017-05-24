package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.callbacks.OnDialogClickedListener;
import com.e2esp.nestleironcalculator.models.ArrowCalculationRange;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.utils.Consts;
import com.e2esp.nestleironcalculator.utils.DialogHandler;

import java.util.ArrayList;

/**
 * Created by Zain on 5/22/2017.
 */

public class ResultActivity extends Activity {

    private View[] percentViews;
    private View emptyPercentView;

    private TextView textViewResultTitle;
    private TextView textViewResultText;

    private RecyclerView productsRecyclerView;

    private double[] finalWeights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_result);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();
        calculateTotalIronPercent();
        calculateCateogryIronPercents();
        checkPopupLimits();
    }

    private void setView() {
        percentViews = new View[4];
        percentViews[0] = findViewById(R.id.viewDairyPercent);
        percentViews[1] = findViewById(R.id.viewCerealPercent);
        percentViews[2] = findViewById(R.id.viewFruitsPercent);
        percentViews[3] = findViewById(R.id.viewMeatPercent);
        emptyPercentView = findViewById(R.id.viewEmptyPercent);

        for (View view: percentViews) {
            setWeightOnView(0.0f, view);
        }
        setWeightOnView(1.0f, emptyPercentView);

        textViewResultTitle = (TextView) findViewById(R.id.textViewResultTitle);
        textViewResultText = (TextView) findViewById(R.id.textViewResultText);

        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    private void calculateTotalIronPercent() {
        double totalIronValue = ((NestleIronCalculatorApp) getApplicationContext()).getTotalIronValue();
        double percent = Math.min(1.0, totalIronValue / Consts.requiredIronIntake);

        ArrayList<ArrowCalculationRange> ranges = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getArrowCalcRanges();
        for (int i = 0; i < ranges.size(); i++) {
            ArrowCalculationRange range = ranges.get(i);
            if (range.isInRange(percent)) {
                setDataForRange(range);
                break;
            }
        }
    }

    private void setDataForRange(ArrowCalculationRange range) {
        String resultTitle = range.getRangeText() + " " + range.getTitle();
        textViewResultTitle.setText(Html.fromHtml(resultTitle), TextView.BufferType.SPANNABLE);
        String resultText = range.getResultText();
        textViewResultText.setText(Html.fromHtml(resultText), TextView.BufferType.SPANNABLE);
    }

    private void calculateCateogryIronPercents() {
        ArrayList<Category> categories = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getCategories();
        ArrayList<Double> totalIron = ((NestleIronCalculatorApp) getApplicationContext()).getTotalIron();
        double totalIronValue = Math.max(((NestleIronCalculatorApp) getApplicationContext()).getTotalIronValue(), Consts.requiredIronIntake);
        double dairyPercent = 0;
        double cerealPercent = 0;
        double fruitPercent = 0;
        double meatPercent = 0;
        for (int i = 0; i < categories.size() && i < totalIron.size(); i++) {
            String catId = categories.get(i).getCategoryId();
            double catIron = totalIron.get(i);
            if (catId.contains("dairy")) {
                dairyPercent = catIron / totalIronValue;
            } else if (catId.contains("cereal")) {
                cerealPercent = catIron / totalIronValue;
            } else if (catId.contains("fruit")) {
                fruitPercent = catIron / totalIronValue;
            } else if (catId.contains("meat")) {
                meatPercent = catIron / totalIronValue;
            }
        }

        dairyPercent = Math.max(0, dairyPercent);
        cerealPercent = Math.max(0, cerealPercent);
        fruitPercent = Math.max(0, fruitPercent);
        meatPercent = Math.max(0, meatPercent);
        if (dairyPercent + cerealPercent + fruitPercent + meatPercent > 1) {
            return;
        }
        finalWeights = new double[4];
        finalWeights[0] = dairyPercent;
        finalWeights[1] = cerealPercent;
        finalWeights[2] = fruitPercent;
        finalWeights[3] = meatPercent;
    }

    private void checkPopupLimits() {
        if (((NestleIronCalculatorApp) getApplicationContext()).hasAddedMilk() == false) {
            String maxMilkText = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getPopups().get(0).getNoMilkText();
            DialogHandler.showDefectDialog(this, new SpannableString(Html.fromHtml(maxMilkText)), new OnDialogClickedListener() {
                @Override
                public void onDialogButtonClick(Dialog dialog, int buttonIndex) {
                    dialog.dismiss();
                    startAnimatingResultBar();
                }
            });
            return;
        }
        startAnimatingResultBar();
    }

    private void startAnimatingResultBar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recursiveWeightIncrease(0, 0.0f);
            }
        }, 100);
    }

    /*
     * Animate the increase in result bar category by category
     */
    private void recursiveWeightIncrease(final int categoryIndex, final float weight) {
        if (categoryIndex < finalWeights.length) {
            if (weight <= finalWeights[categoryIndex]) {
                setWeightOnView(weight, percentViews[categoryIndex]);
                float emptyWeight = 1.0f;
                for (int i = 0; i < categoryIndex; i++) {
                    emptyWeight -= finalWeights[i];
                }
                emptyWeight -= weight;
                setWeightOnView(emptyWeight, emptyPercentView);
                Log.i("Weight", "categoryIndex: "+categoryIndex+" weight: "+weight+" emptyWeight: "+emptyWeight);
                emptyPercentView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recursiveWeightIncrease(categoryIndex, weight + 0.1f);
                    }
                }, 10);
            } else {
                recursiveWeightIncrease(categoryIndex + 1, 0.0f);
            }
        }
    }

    private void setWeightOnView(float weight, View view) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.weight = weight;
        view.setLayoutParams(layoutParams);
    }

}
