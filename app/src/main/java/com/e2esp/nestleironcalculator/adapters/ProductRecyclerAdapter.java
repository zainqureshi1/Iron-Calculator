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
import com.e2esp.nestleironcalculator.callbacks.OnProductClickListener;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.Product;
import com.e2esp.nestleironcalculator.utils.Utility;


/**
 * Created by farooq on 05-May-17.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    //private Context context;
    private ArrayList<Product> products;
    private Context context;
    private OnProductClickListener onProductClickListener;

    //public ProductRecyclerAdapter(Context context, ArrayList<Product> products, OnProductClickListener onProductClickListener) {
        public ProductRecyclerAdapter(Context context, ArrayList<Product> products,OnProductClickListener onProductClickListener ) {
        this.context = context;
        this.products = products;
        this.onProductClickListener = onProductClickListener;
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

            int radiochecked = context.getResources().getIdentifier("radio_checked", "drawable", "com.e2esp.nestleironcalculator");
            int radioUnChecked = context.getResources().getIdentifier("radio_unchecked", "drawable", "com.e2esp.nestleironcalculator");

            if(product.isSelected())
                imgselect.setImageResource(radiochecked);
            else
                imgselect.setImageResource(radioUnChecked);

            txtPortionSize.setText( String.valueOf (product.getSelectedSize()) );

            if (position % 2 == 1) {
                topView.setBackgroundColor(context.getResources().getColor(R.color.grey));
            } else {
                topView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }


            int collapseImageId = context.getResources().getIdentifier("collapse", "drawable", "com.e2esp.nestleironcalculator");
            int expandImageId = context.getResources().getIdentifier("expand", "drawable", "com.e2esp.nestleironcalculator");

            String visibleProduct = ((NestleIronCalculatorApp)context.getApplicationContext()).visibleProductId;
            if(product != null && product.getId() != null && product.getId().equals(visibleProduct))
            {
                prodDetailLayout.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                imgExpand.setImageResource(expandImageId);
            }
            else {

                prodDetailLayout.setVisibility(View.GONE);
                txtTitle.setVisibility(View.GONE);
                imgExpand.setImageResource(collapseImageId);


            }



            txtProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProductClick(product);
                }
            });


            imgPlus.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    onProductClickListener.onPlusClick(product);

                }
            });

            imgMinus.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    onProductClickListener.onMinusClick(product);
                }
            });

        }


        private void onProductClick(Product product)
        {

            if(product.getId().equals(((NestleIronCalculatorApp)context.getApplicationContext()).visibleProductId))
                ((NestleIronCalculatorApp)context.getApplicationContext()).visibleProductId = "";
            else
                ((NestleIronCalculatorApp)context.getApplicationContext()).visibleProductId = product.getId();
            notifyDataSetChanged();

//            int collapseImageId = context.getResources().getIdentifier("collapse", "drawable", "com.e2esp.nestleironcalculator");
//            int expandImageId = context.getResources().getIdentifier("expand", "drawable", "com.e2esp.nestleironcalculator");
//            if(prodDetailLayout.getVisibility() == View.VISIBLE) {
//                prodDetailLayout.setVisibility(View.GONE);
//                txtTitle.setVisibility(View.GONE);
//                imgExpand.setImageResource(collapseImageId);
//                product.setVisible(true);
//            }
//            else {
//                prodDetailLayout.setVisibility(View.VISIBLE);
//                txtTitle.setVisibility(View.VISIBLE);
//                imgExpand.setImageResource(expandImageId);
//                visibleProduct = "";
//                product.setVisible(false);
//            }
//

        }

    }
}

