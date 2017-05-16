package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;

public class MainActivity extends Activity {

    Button btnGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();

        //lnkIronCalculator.setClickable(true);
        //lnkIronCalculator.setMovementMethod(LinkMovementMethodentMethod.getInstance());
        //String text = "<a href='http://www.google.com'> Google </a>";
        //lnkIronCalculator.setText(Html.fromHtml(text));
    }
    private void setView()
    {
        btnGo =  (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbtnGoClick();
            }
        });

    }
    private void onbtnGoClick()
    {
        startActivity(new Intent(this, DairyActivity.class));
    }
}
