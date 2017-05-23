package com.e2esp.nestleironcalculator.callbacks;

import com.e2esp.nestleironcalculator.models.Product;

/**
 * Created by farooq on 23-May-17.
 */

public interface OnProductClickListener {

    void onPlusClick(Product product);

    void onMinusClick(Product product);
}
