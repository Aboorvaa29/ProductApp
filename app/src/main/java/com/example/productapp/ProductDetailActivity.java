package com.example.productapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvProductTitle;
    private TextView tvProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvProductTitle = findViewById(R.id.tvProductTitle);
        tvProductDetail = findViewById(R.id.tvProductDetail);

        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            tvProductTitle.setText(product.getTitle());
            tvProductDetail.setText(product.getDescription());
        }
    }
}
