package com.example.shop;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Integer> productIDs;
    private ArrayList<Uri> productImageURIs;
    private ArrayList<String> productNames;
    private ArrayList<String> productCategories;
    private ArrayList<Double> productPrices;
    private ArrayList<String> productSellerUsernames;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name;
        private TextView category;
        private TextView price;
        private TextView seller;

        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            image = view.findViewById(R.id.product_image);
            name = view.findViewById(R.id.product_name);
            category = view.findViewById(R.id.product_category);
            price = view.findViewById(R.id.product_price);
            seller = view.findViewById(R.id.product_seller);

            linearLayout = view.findViewById(R.id.product_row_linear_layout);
        }

//        public TextView getTextView() {
//            return textView;
//        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     //* @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(Context context, ArrayList<Integer> productIDs,
                         ArrayList<Uri> productImageURIs, ArrayList<String> productNames,
                         ArrayList<String> productCategories, ArrayList<Double> productPrices,
                         ArrayList<String> productSellerUsernames){
        this.context = context;
        this.productIDs = productIDs;
        this.productImageURIs = productImageURIs;
        this.productNames = productNames;
        this.productCategories = productCategories;
        this.productPrices = productPrices;
        this.productSellerUsernames = productSellerUsernames;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.product_row, parent, false);

        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.image.setImageURI(productImageURIs.get(position));
        viewHolder.name.setText(productNames.get(position));
        viewHolder.category.setText(productCategories.get(position));
        viewHolder.price.setText(String.valueOf(productPrices.get(position)) + " $");
        viewHolder.seller.setText(productSellerUsernames.get(position));

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeleteUpdateProductActivity.class);
                intent.putExtra("productID", productIDs.get(position));
                intent.putExtra("imageURIString", productImageURIs.get(position).toString());
                intent.putExtra("name", productNames.get(position));
                intent.putExtra("category", productCategories.get(position));
                intent.putExtra("price", productPrices.get(position));
                intent.putExtra("username", productSellerUsernames.get(position));
                context.startActivity(intent);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productNames.size();
    }
}
