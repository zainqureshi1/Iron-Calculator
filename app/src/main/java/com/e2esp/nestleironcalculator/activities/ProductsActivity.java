package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.adapters.ProductRecyclerAdapter;
import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.callbacks.OnDialogClickedListener;
import com.e2esp.nestleironcalculator.callbacks.OnProductClickListener;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.IronDetector;
import com.e2esp.nestleironcalculator.models.Product;
import com.e2esp.nestleironcalculator.utils.Consts;
import com.e2esp.nestleironcalculator.utils.DialogHandler;
import com.e2esp.nestleironcalculator.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farooq on 18-May-17.
 */

public class ProductsActivity extends Activity {

    private Spinner spinnerCategories;
    private ArrayList<Category> categories = null;

    private RecyclerView recyclerViewProducts;
    private ArrayList<Product> products;
    private ProductRecyclerAdapter productsAdapter;

    private Button btnNextCategory;
    private Button btnCalculate;

    private LinearLayout ballTop;
    private LinearLayout ball;
    private LinearLayout ballBottom;

    private ImageView imgbo;

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
        List<String> spinnerArray = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            spinnerArray.add(Html.fromHtml(categories.get(i).getCategoryName()).toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setSelection(0);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                fillProducts(categories.get(position).getProducts());
                checkButtons();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Your code here
            }
        });

        recyclerViewProducts = (RecyclerView) findViewById(R.id.recyclerViewProducts);
        products = new ArrayList<>();
        productsAdapter = new ProductRecyclerAdapter(getBaseContext(), products, new OnProductClickListener() {
            @Override
            public void onPlusClick(Product product) {
                onImgPlusClick(product);
            }
            @Override
            public void onMinusClick(Product product) {
                onImgMinusClick(product);
            }
        });
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewProducts.setAdapter(productsAdapter);

        fillProducts(categories.get(0).getProducts());

        btnNextCategory = (Button) findViewById(R.id.btnNextCategory);
        btnNextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClick();
            }
        });
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCalculateButtonClick();
            }
        });
        ballTop = (LinearLayout) findViewById(R.id.ballTop);
        ball = (LinearLayout) findViewById(R.id.ball);
        ballBottom = (LinearLayout) findViewById(R.id.ballBottom);
        imgbo = (ImageView) findViewById(R.id.imgbo);
    }

    private void onNextButtonClick() {
        int position = spinnerCategories.getSelectedItemPosition();
        if (position < 0)  {
            position = 0;
        }
        if (position < spinnerCategories.getCount() - 1) {
            spinnerCategories.setSelection(position + 1);
            fillProducts(categories.get(position + 1).getProducts());
        }
    }

    private void onCalculateButtonClick() {
        startActivity(new Intent(this, ResultActivity.class));
    }

    private void checkButtons() {
        if (spinnerCategories.getSelectedItemPosition() == spinnerCategories.getCount() - 1)//if last category is selected
        {
            btnNextCategory.setVisibility(View.GONE);
        } else {
            btnNextCategory.setVisibility(View.VISIBLE);
        }
    }

    private void fillProducts(ArrayList<Product> newProducts) {
        products.clear();


        //products.addAll(newProducts);
        //check if this product is for 1+ years
        IronDetector ironDetector = (((NestleIronCalculatorApp) NestleIronCalculatorApp.getAppContext()).ironDetector);
        ArrayList<AgeSelection> ages = (ArrayList<AgeSelection>) ironDetector.getAge();

        for(Product prod : newProducts)
        {
            for (AgeSelection age : ages) {
                if (age.isSelected() ) {

                    if( (prod.getMinAge() <= 0 && prod.getMaxAge() <=0)
                            || ( age.isInRange(prod.getMinAge()) || age.isInRange(prod.getMaxAge()) ) )
                    {
                        products.add(prod);
                    }
                }

            }

        }




        productsAdapter.notifyDataSetChanged();
    }

    private void onImgPlusClick(Product product) {
        if (product.isSelected()) {
            double portionSize = product.getPortionSize() + product.getSelectedSize();
            product.setSelectedSize(portionSize);
        } else {
            product.setSelected(true);
        }

        productsAdapter.notifyDataSetChanged();
        moveBall();
        checkBo();
        checkPopupLimits();
    }

    private void onImgMinusClick(Product product) {
        if (product.isSelected()) {
            double portionSize = product.getSelectedSize() - product.getPortionSize();

            if (portionSize > 0) { //if some portion size selected then keep the product in list just update selected size
                product.setSelectedSize(portionSize);
            } else {
                product.setSelected(false);
            }
        }
        productsAdapter.notifyDataSetChanged();
        moveBall();
        checkBo();
        checkPopupLimits();
    }

    private void checkBo()
    {
        int bo = getApplicationContext().getResources().getIdentifier(Utility.showBo(), "drawable", "com.e2esp.nestleironcalculator");

        imgbo.setBackgroundResource(bo);


    }
    private void moveBall() {
        Utility.calculateIron(getBaseContext());
        double topPadding = Utility.calc_ball_top_padding();

        LinearLayout.LayoutParams top = (LinearLayout.LayoutParams) ballTop.getLayoutParams();
        top.weight = (float) topPadding;
        ballTop.setLayoutParams(top);

        LinearLayout.LayoutParams bottom = (LinearLayout.LayoutParams) ballBottom.getLayoutParams();
        bottom.weight = (float) (1 - Consts.ball_space - topPadding);
        ballBottom.setLayoutParams(bottom);
    }

    private void checkPopupLimits() {
        if (((NestleIronCalculatorApp) getApplicationContext()).hasReachedRequiredIronLimit()) {
            DialogHandler.showReachedIntakeDialog(this, new OnDialogClickedListener() {
                @Override
                public void onDialogButtonClick(Dialog dialog, int buttonIndex) {
                    dialog.dismiss();
                    if (buttonIndex == 0) {
                        onCalculateButtonClick();
                    }
                }
            });
            return;
        }

        if (((NestleIronCalculatorApp) getApplicationContext()).hasExceededMaxMilkLimit()) {
            String maxMilkText = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getPopups().get(0).getMaxMilkText();
            DialogHandler.showDefectDialog(this, new SpannableString(Html.fromHtml(maxMilkText)), new OnDialogClickedListener() {
                @Override
                public void onDialogButtonClick(Dialog dialog, int buttonIndex) {
                    dialog.dismiss();
                }
            });
            return;
        }

        if (((NestleIronCalculatorApp) getApplicationContext()).hasExceededMaxSolidFoodLimit()) {
            String maxSolidFoodText = ((NestleIronCalculatorApp) getApplicationContext()).ironDetector.getPopups().get(0).getMaxSolidText();
            DialogHandler.showDefectDialog(this, new SpannableString(Html.fromHtml(maxSolidFoodText)), new OnDialogClickedListener() {
                @Override
                public void onDialogButtonClick(Dialog dialog, int buttonIndex) {
                    dialog.dismiss();
                }
            });
            return;
        }
    }

}
