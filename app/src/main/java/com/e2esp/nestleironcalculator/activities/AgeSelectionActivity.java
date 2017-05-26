package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

    private Button btnClose;
    private Button btnNext;

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

        rbtnGroup = (RadioGroup) findViewById(R.id.rgrpAge);
        RadioButton button;

        for(int i = 0; i < ageSlabs.size(); i++) {
            button = new RadioButton(this);
            button.setId(i+1);
            button.setText(ageSlabs.get(i).getText());
            button.setTextColor(ContextCompat.getColorStateList(getBaseContext(), R.color.blue));
            button.setButtonDrawable(R.drawable.custom_btn_radio);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClick(v);
                }
            });
            rbtnGroup.addView(button);
        }

        layoutNotice = (LinearLayout) findViewById(R.id.layoutNotice);

        txtNoticeHeading = (TextView) findViewById(R.id.txtNoticeHeading);
        txtNotice = (TextView) findViewById(R.id.txtNotice);
        btnClose = (Button)findViewById(R.id.btnClose);
        btnNext = (Button)findViewById(R.id.btnNext);

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
        if (checkedItemId < 1) {
            Toast.makeText(getApplicationContext(), "Please select age.", Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<AgeSelection> ages = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getAge();
        for(AgeSelection age: ages)
            age.setSelected(false);

        //((NestleIronCalculatorApp) getApplicationContext()).setAgeSlabSelected(checkedItemId);
        ((ArrayList<AgeSelection>) ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getAge()).get(checkedItemId).setSelected(true);
        startActivity(new Intent(this, ProductsActivity.class));
    }

    private void onRadioButtonClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        layoutNotice.setVisibility(View.GONE);
        btnClose.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        for(int i=0; i <ageSlabs.size();i++) {
            if(ageSlabs.get(i).getText().equals(((RadioButton) view).getText() ))//match the current selected radiobutton text with age slab
            {
                if(ageSlabs.get(i).getTextHint()!= null) {
                    txtNoticeHeading.setText(ageSlabs.get(i).getTextHintTitle());
                    txtNotice.setText(ageSlabs.get(i).getTextHint());
                    layoutNotice.setVisibility(View.VISIBLE);
                    btnClose.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                }
            }
        }
    }

}
