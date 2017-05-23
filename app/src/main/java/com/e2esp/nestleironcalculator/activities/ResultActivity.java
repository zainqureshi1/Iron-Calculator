package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
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

    private View viewDairyPercent;
    private View viewCerealPercent;
    private View viewFruitsPercent;
    private View viewMeatPercent;
    private View viewEmptyPercent;

    private TextView textViewResultTitle;
    private TextView textViewResultText;

    private RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_result);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();
        calculateTotalIronPercent();
        calculateCateogryIronPercents();
    }

    private void setView() {
        viewDairyPercent = findViewById(R.id.viewDairyPercent);
        viewCerealPercent = findViewById(R.id.viewCerealPercent);
        viewFruitsPercent = findViewById(R.id.viewFruitsPercent);
        viewMeatPercent = findViewById(R.id.viewMeatPercent);
        viewEmptyPercent = findViewById(R.id.viewEmptyPercent);

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
        int dairyPercent = 0;
        int cerealPercent = 0;
        int fruitPercent = 0;
        int meatPercent = 0;
        for (int i = 0; i < categories.size() && i < totalIron.size(); i++) {
            String catId = categories.get(i).getCategoryId();
            double catIron = totalIron.get(i);
            if (catId.contains("dairy")) {
                dairyPercent = (int) (catIron / totalIronValue * 100);
            } else if (catId.contains("cereal")) {
                cerealPercent = (int) (catIron / totalIronValue * 100);
            } else if (catId.contains("fruit")) {
                fruitPercent = (int) (catIron / totalIronValue * 100);
            } else if (catId.contains("meat")) {
                meatPercent = (int) (catIron / totalIronValue * 100);
            }
        }
        setBarPercents(dairyPercent, cerealPercent, fruitPercent, meatPercent);
    }

    private void setBarPercents(int dairy, int cereal, int fruits, int meat) {
        if (dairy + cereal + fruits + meat > 100) {
            return;
        }
        setWeightOnView(dairy/100.0f, viewDairyPercent);
        setWeightOnView(cereal/100.0f, viewCerealPercent);
        setWeightOnView(fruits/100.0f, viewFruitsPercent);
        setWeightOnView(meat/100.0f, viewMeatPercent);
        setWeightOnView((100 - dairy - cereal - fruits - meat)/100.0f, viewEmptyPercent);
    }

    private void setWeightOnView(float weight, View view) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.weight = weight;
        view.setLayoutParams(layoutParams);
    }

    private void checkPopupLimits() {
        if (((NestleIronCalculatorApp) getApplicationContext()).hasAddedMilk() == false) {
            String maxMilkText = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getPopups().get(0).getNoMilkText();
            DialogHandler.showDefectDialog(this, new SpannableString(Html.fromHtml(maxMilkText)), new OnDialogClickedListener() {
                @Override
                public void onDialogButtonClick(Dialog dialog, int buttonIndex) {
                    dialog.dismiss();
                }
            });
            return;
        }
    }

}
