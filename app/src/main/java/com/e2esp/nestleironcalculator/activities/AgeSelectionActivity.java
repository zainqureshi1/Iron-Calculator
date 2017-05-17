package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.utils.ParseXMLData;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by farooq on 11-May-17.
 */

public class AgeSelectionActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_ageselection);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();


    }
    private void setView()
    {
        try {
            ParseXMLData parseXMLData = new ParseXMLData();
            parseXMLData.parse();
        }
        catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
