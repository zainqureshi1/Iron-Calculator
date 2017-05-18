package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farooq on 18-May-17.
 */

public class ProductsActivity extends Activity {

    Spinner spinnerCategories;
    ArrayList<Product> products  = null;
    ArrayList<Category> categories = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_products);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();


    }
    private void setView()
    {
        categories =  ((NestleIronCalculatorApp)getApplicationContext()).ironDetector.getCategories();

        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
        List<String> spinnerArray =  new ArrayList<String>();

        for(int i=0; i< categories.size(); i++)
        {
            spinnerArray.add(Html.fromHtml(categories.get(i).getCategoryName()).toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setSelection(0);


    }
}
