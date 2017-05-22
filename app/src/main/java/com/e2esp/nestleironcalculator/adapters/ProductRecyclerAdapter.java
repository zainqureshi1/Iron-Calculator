package com.e2esp.nestleironcalculator.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.Product;


/**
 * Created by farooq on 05-May-17.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    //private Context context;
    private ArrayList<Product> products;
    private Context context;
    //private OnProductClickListener onProductClickListener;

    //public ProductRecyclerAdapter(Context context, ArrayList<Product> products, OnProductClickListener onProductClickListener) {
        public ProductRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;



      //  this.o
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bindView(products.get(position), position);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView txtProduct;
        private ImageView imgselect;
        private ImageView imgprod;
        private ImageView imgMinus;
        private TextView txtPortionSize;
        private TextView txtUnit;
        private ImageView imgPlus;
        private TextView txtTitle;
        private LinearLayout prodDetailLayout;
        private ImageView imgExpand;
        int expandImageId;
        int collapseImageId;


        public ProductViewHolder(View itemView) {
            super(itemView);
            topView = itemView;
            txtProduct= (TextView) itemView.findViewById(R.id.txtProduct);
            imgselect = (ImageView) itemView.findViewById(R.id.imgselect);
            imgprod = (ImageView) itemView.findViewById(R.id.imgprod);
            imgMinus = (ImageView) itemView.findViewById(R.id.imgMinus);
            imgPlus = (ImageView) itemView.findViewById(R.id.imgPlus);
            imgExpand = (ImageView) itemView.findViewById(R.id.imgExpand);

            txtPortionSize = (TextView) itemView.findViewById(R.id.txtPortionSize);
            txtUnit = (TextView) itemView.findViewById(R.id.txtUnit);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            prodDetailLayout = (LinearLayout) itemView.findViewById(R.id.prodDetailLayout);



        }

        public void bindView(final Product product, int position) {

            txtProduct.setText(product.getName());
            txtPortionSize.setText(product.getPortionSize());
            txtUnit.setText(product.getUnit());
            txtTitle.setText(product.getTitle());

            try {
                int resourceId = context.getResources().getIdentifier(product.getId(), "drawable", "com.e2esp.nestleironcalculator");
                imgprod.setImageResource(resourceId);
            }
            catch (Exception ex)
            {

            }

            if (position % 2 == 1) {
                topView.setBackgroundColor(context.getResources().getColor(R.color.grey));
            } else {
                topView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }


            txtProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProductClick();
                }
            });

            imgPlus.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    onImgPlusClick(product);
                }
            });

            imgMinus.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    onImgMinusClick(product);
                }
            });

        }

        private void onImgPlusClick(Product product)
        {
            int portionSize = 0;
            int resourceId = context.getResources().getIdentifier("radio_checked", "drawable", "com.e2esp.nestleironcalculator");
            imgselect.setImageResource(resourceId);


            ArrayList<Product> selectedProducts = ((NestleIronCalculatorApp) context.getApplicationContext()).getSelectedProducts();

            boolean prodExist = false;
            for(Product currProd : selectedProducts) {
                if(currProd.getId().equals(product.getId()))
                {
                    portionSize =Integer.parseInt( currProd.getPortionSize()) + Integer.parseInt(currProd.getSelectedSize());
                    currProd.setSelectedSize( String.valueOf (portionSize) );
                    prodExist = true;
                    break;
                }

            }

            if(!prodExist)
            {
                portionSize = Integer.parseInt(product.getPortionSize());

                Product prod = new Product();
                prod.setId(product.getId());
                prod.setPortionSize(product.getPortionSize());
                prod.setSelectedSize(product.getPortionSize());
                prod.setIronPerPortion(product.getIronPerPortion());
                selectedProducts.add(prod);


            }

            txtPortionSize.setText( String.valueOf (portionSize) );

            ((NestleIronCalculatorApp) context.getApplicationContext()).setSelectedProducts(selectedProducts);


        }

        private void onImgMinusClick(Product product)
        {
            ArrayList<Product> selectedProducts = ((NestleIronCalculatorApp) context.getApplicationContext()).getSelectedProducts();

            boolean removeProd = false;
            int currentProdIndex = -1;

            for(Product currProd : selectedProducts) {
                currentProdIndex++;
                if(currProd.getId().equals(product.getId()))
                {
                    int portionSize =  Integer.parseInt(currProd.getSelectedSize()) - Integer.parseInt( currProd.getPortionSize());
                    if(portionSize > 0) { //if some portion size selected then keep the product in list just update selected size
                        currProd.setSelectedSize(String.valueOf(portionSize));
                        txtPortionSize.setText( String.valueOf (portionSize) );

                        removeProd = false;
                        break;
                    }
                    else
                    {
                        txtPortionSize.setText( product.getPortionSize() );
                        removeProd = true;
                    }
                }

            }
            if(removeProd) {
                selectedProducts.remove(currentProdIndex);

                int resourceId = context.getResources().getIdentifier("radio_unchecked", "drawable", "com.e2esp.nestleironcalculator");
                imgselect.setImageResource(resourceId);
            }

            ((NestleIronCalculatorApp) context.getApplicationContext()).setSelectedProducts(selectedProducts);
        }

        private void onProductClick()
        {
            int collapseImageId = context.getResources().getIdentifier("collapse", "drawable", "com.e2esp.nestleironcalculator");
            int expandImageId = context.getResources().getIdentifier("expand", "drawable", "com.e2esp.nestleironcalculator");

            if(prodDetailLayout.getVisibility() == View.VISIBLE) {
                prodDetailLayout.setVisibility(View.GONE);
                txtTitle.setVisibility(View.GONE);
                imgExpand.setImageResource(collapseImageId);
            }
            else {
                prodDetailLayout.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                imgExpand.setImageResource(expandImageId);
            }

        }

    }
}

