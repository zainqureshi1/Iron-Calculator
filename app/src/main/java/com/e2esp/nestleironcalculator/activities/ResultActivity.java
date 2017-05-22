package com.e2esp.nestleironcalculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;

public class ResultActivity extends AppCompatActivity {

    private View viewDairyPercent;
    private View viewCerealPercent;
    private View viewFruitsPercent;
    private View viewMeatPercent;
    private View viewEmptyPercent;

    private TextView textViewFinalResult;
    private TextView textViewFinalResultMessage;

    private RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setView();
    }

    private void setView() {
        viewDairyPercent = findViewById(R.id.viewDairyPercent);
        viewCerealPercent = findViewById(R.id.viewCerealPercent);
        viewFruitsPercent = findViewById(R.id.viewFruitsPercent);
        viewMeatPercent = findViewById(R.id.viewMeatPercent);
        viewEmptyPercent = findViewById(R.id.viewEmptyPercent);

        textViewFinalResult = (TextView) findViewById(R.id.textViewFinalResult);
        textViewFinalResultMessage = (TextView) findViewById(R.id.textViewFinalResultMessage);

        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
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

}
