package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.adapters.ProductRecyclerAdapter;
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
    ArrayList<Product> products  = new ArrayList<>();
    ArrayList<Category> categories = null;
    RecyclerView recyclerViewProducts;
    ProductRecyclerAdapter productsAdapter;
    Button btnNextCategory;
    Button btnCalculate;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_products);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nestle_header);

        setView();


    }
    private void setView() {
        categories = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getCategories();

        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
        List<String> spinnerArray = new ArrayList<String>();

        for (int i = 0; i < categories.size(); i++) {
            spinnerArray.add(Html.fromHtml(categories.get(i).getCategoryName()).toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setSelection(0);


        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                products =   categories.get(position).getProducts();
                fillProducts();
                checkButtons();
            }


        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // Your code here
        }
    });

        fillProducts();

        btnNextCategory = (Button) findViewById(R.id.btnNextCategory);
        btnNextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClick();
            }
        });
        btnCalculate = (Button) findViewById(R.id.btnCalculate);


    }

    private void onNextButtonClick()
    {
        int position = spinnerCategories.getSelectedItemPosition();

        if(position < spinnerCategories.getCount() ) {

            spinnerCategories.setSelection(position + 1);
            products = categories.get(position+1).getProducts();
            fillProducts();
        }

    }

    private void checkButtons() {

        if (spinnerCategories.getSelectedItemPosition() == spinnerCategories.getCount()-1)//if last category is selected
        {
            btnCalculate.setVisibility(View.VISIBLE);
            btnNextCategory.setVisibility(View.GONE);

        }
      /*  else if (spinnerCategories.getSelectedItemPosition() >0)
        {
            btnCalculate.setVisibility(View.VISIBLE);
        }
        else if (spinnerCategories.getSelectedItemPosition() == 0)//first item is selected
        {
            btnCalculate.setVisibility(View.GONE);
            btnNextCategory.setVisibility(View.VISIBLE);
        }*/

    }

    private void fillProducts()
    {
        recyclerViewProducts = (RecyclerView) findViewById(R.id.recyclerViewProducts);

        if(products.size() <= 0)
            products = categories.get(0).getProducts();

        productsAdapter = new ProductRecyclerAdapter(getBaseContext(),products);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewProducts.setAdapter(productsAdapter);


        //if( spinnerCategories.getCount())
          //  gvEvents.setAdapter(new GridAdapter(((EventManagementApplication)getApplicationContext()).globalEventsArray));


    }

}
