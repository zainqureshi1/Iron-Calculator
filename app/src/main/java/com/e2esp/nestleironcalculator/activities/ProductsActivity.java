package com.e2esp.nestleironcalculator.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.adapters.ProductRecyclerAdapter;
import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.callbacks.OnDialogClickedListener;
import com.e2esp.nestleironcalculator.callbacks.OnProductClickListener;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.Product;
import com.e2esp.nestleironcalculator.utils.Consts;
import com.e2esp.nestleironcalculator.utils.DialogHandler;
import com.e2esp.nestleironcalculator.utils.Utility;

import java.util.ArrayList;

/**
 * Created by farooq on 18-May-17.
 */

public class ProductsActivity extends Activity {

    private ArrayList<Category> categories;
    private TabLayout categoryTabs;

    private RecyclerView recyclerViewProducts;
    private ArrayList<Product> products;
    private ProductRecyclerAdapter productsAdapter;

    private ImageButton btnNextCategory;
    private ImageButton btnCalculate;

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

        categoryTabs = (TabLayout) findViewById(R.id.categoryTabs);
        addCategoryTabs();
        setTabSelection(0);

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

        btnNextCategory = (ImageButton) findViewById(R.id.btnNextCategory);
        btnNextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClick();
            }
        });
        btnCalculate = (ImageButton) findViewById(R.id.btnCalculate);
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

    private void addCategoryTabs() {
        categoryTabs.removeAllTabs();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            TabLayout.Tab tab = categoryTabs.newTab();
            View tabView = inflater.inflate(R.layout.category_tab, null);
            TextView tabText = (TextView) tabView.findViewById(R.id.textViewTab);
            tabText.setText(Html.fromHtml(category.getCategoryName()));
            if (category.getCategoryId().contains("dairy")) {
                tabView.setBackgroundResource(R.drawable.tab_bg_blue);
            } else if (category.getCategoryId().contains("cereal")) {
                tabView.setBackgroundResource(R.drawable.tab_bg_orange);
            } else if (category.getCategoryId().contains("fruit")) {
                tabView.setBackgroundResource(R.drawable.tab_bg_green);
            } else if (category.getCategoryId().contains("meat")) {
                tabView.setBackgroundResource(R.drawable.tab_bg_red);
            }
            tab.setCustomView(tabView);
            categoryTabs.addTab(tab, i);
        }

        categoryTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = categoryTabs.getSelectedTabPosition();
                setTabSelection(position);
                fillProducts(categories.get(position).getProducts());
                checkButtons();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setTabSelection(int selection) {
        for (int i = 0; i < categoryTabs.getTabCount(); i++) {
            View tabView = categoryTabs.getTabAt(i).getCustomView();
            TextView tabText = (TextView) tabView.findViewById(R.id.textViewTab);
            tabText.setTypeface(null, i == selection ? Typeface.BOLD : Typeface.NORMAL);
        }
    }

    private void onNextButtonClick() {
        int position = categoryTabs.getSelectedTabPosition();
        if (position < 0) {
            position = 0;
        }
        if (position < categoryTabs.getTabCount() - 1) {
            categoryTabs.getTabAt(position + 1).select();
            fillProducts(categories.get(position + 1).getProducts());
        }
    }

    private void onCalculateButtonClick() {
        startActivity(new Intent(this, ResultActivity.class));
    }

    private void checkButtons() {
        if (categoryTabs.getSelectedTabPosition() == categoryTabs.getTabCount() - 1)//if last category is selected
        {
            btnNextCategory.setVisibility(View.GONE);
        } else {
            btnNextCategory.setVisibility(View.VISIBLE);
        }
    }

    private void fillProducts(ArrayList<Product> newProducts) {
        products.clear();

        //check if this product is for 1+ years
        AgeSelection selectedAge = ((NestleIronCalculatorApp) NestleIronCalculatorApp.getAppContext()).ironDetector.getSelectedAge();
        if (selectedAge == null) {
            return;
        }
        for (Product prod : newProducts) {
            if ((prod.getMinAge() <= 0 && prod.getMaxAge() <= 0)
                    || (selectedAge.isInRange(prod.getMinAge()) || selectedAge.isInRange(prod.getMaxAge()))) {
                products.add(prod);
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

    private void checkBo() {
        int bo = getApplicationContext().getResources().getIdentifier(Utility.showBo(), "drawable", "com.e2esp.nestleironcalculator");
        imgbo.setImageResource(bo);
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
