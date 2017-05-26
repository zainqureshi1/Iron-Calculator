package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.utils.ParseXMLData;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by farooq on 11-May-17.
 */

public class AgeSelectionActivity extends Activity {

    private LinearLayout layoutNotice;
    private RadioGroup rbtnGroup;
    private ArrayList<AgeSelection> ageSlabs = null;

    private TextView txtNotice;
    private TextView txtNoticeHeading;

    private ImageButton btnClose;
    private ImageButton btnNext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_ageselection);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();
    }

    private void setView() {
        try {
            ParseXMLData parseXMLData = new ParseXMLData();
            parseXMLData.parse(getApplicationContext());
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if( ((NestleIronCalculatorApp)getApplicationContext()).ironDetector != null)
            ageSlabs =  ((NestleIronCalculatorApp)getApplicationContext()).ironDetector.getAge();

        int colorBlue = ContextCompat.getColor(this, R.color.blue);
        int marginTiny = getResources().getDimensionPixelSize(R.dimen.margin_tiny);
        rbtnGroup = (RadioGroup) findViewById(R.id.rgrpAge);
        for(int i = 0; i < ageSlabs.size(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setText(ageSlabs.get(i).getText());
            button.setTextColor(colorBlue);
            button.setButtonDrawable(R.drawable.custom_btn_radio);
            button.setPadding(marginTiny, 0, 0, 0);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, marginTiny);
            rbtnGroup.addView(button, params);
        }
        rbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                onRadioButtonChecked(checkedId);
            }
        });

        layoutNotice = (LinearLayout) findViewById(R.id.layoutNotice);

        txtNoticeHeading = (TextView) findViewById(R.id.txtNoticeHeading);
        txtNotice = (TextView) findViewById(R.id.txtNotice);
        btnClose = (ImageButton)findViewById(R.id.btnClose);
        btnNext = (ImageButton)findViewById(R.id.btnNext);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseButtonClick();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClick();
            }
        });
    }

    private void onCloseButtonClick() {
        /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //yes button clicked
                        Toast.makeText(getApplicationContext(), "YESSSS", Toast.LENGTH_LONG).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Toast.makeText(getApplicationContext(), "Noooo", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();*/

       this.finishAffinity();
    }

    private void onNextButtonClick() {
        int checkedItemId = rbtnGroup.getCheckedRadioButtonId();
        if (checkedItemId < 0) {
            Toast.makeText(getApplicationContext(), "Please select age.", Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<AgeSelection> ages = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getAge();
        for(int i = 0; i < ages.size(); i++) {
            ages.get(i).setSelected(i == checkedItemId);
        }
        ((NestleIronCalculatorApp) getApplicationContext()).clearSelections();
        startActivity(new Intent(this, ProductsActivity.class));
    }

    private void onRadioButtonChecked(int index) {
        layoutNotice.setVisibility(View.GONE);
        btnClose.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        if (index < ageSlabs.size()) {
            AgeSelection selectedAge = ageSlabs.get(index);
            if(selectedAge.getTextHint() != null) {
                txtNoticeHeading.setText(selectedAge.getTextHintTitle());
                txtNotice.setText(selectedAge.getTextHint());
                layoutNotice.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
            }
        }
    }

}
