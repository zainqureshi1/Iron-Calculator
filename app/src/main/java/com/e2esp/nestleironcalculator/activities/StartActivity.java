package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;

/**
 * Created by farooq on 25-May-17.
 */

public class StartActivity extends Activity {

    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_start);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();


    }
    private void setView() {

        btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbtnGoClick();
            }
        });
    }

    private void onbtnGoClick() {
        startActivity(new Intent(this, AgeSelectionActivity.class));
    }

}
