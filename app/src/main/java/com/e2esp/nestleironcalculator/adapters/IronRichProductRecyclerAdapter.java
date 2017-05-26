package com.e2esp.nestleironcalculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.models.Product;

import java.util.ArrayList;

/**
 * Created by farooq on 26-May-17.
 */

public class IronRichProductRecyclerAdapter extends RecyclerView.Adapter<IronRichProductRecyclerAdapter.ProductViewHolder> {

    //private Context context;
    private ArrayList<Product> products;
    private Context context;

    public IronRichProductRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public IronRichProductRecyclerAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ironrich_product_item, parent, false);
        return new IronRichProductRecyclerAdapter.ProductViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onBindViewHolder(IronRichProductRecyclerAdapter.ProductViewHolder holder, int position) {
        holder.bindView(products.get(position));
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewProduct);
            textViewName = (TextView) itemView.findViewById(R.id.textViewProductName);
        }

        public void bindView(Product product) {
            try {
                int resourceId = context.getResources().getIdentifier(product.getId().toLowerCase()+"_big", "drawable", "com.e2esp.nestleironcalculator");
                imageView.setImageResource(resourceId);
                textViewName.setText(product.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}

