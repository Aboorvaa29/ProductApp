package com.example.productapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private static final String TAG = "ProductListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchProducts();
    }

    private void fetchProducts() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productList = response.body().getProducts();
                    Log.d(TAG, "Products fetched successfully");
                    productAdapter = new ProductAdapter(productList, product -> {
                        Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                        intent.putExtra("product", product);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Log.e(TAG, "Response unsuccessful or body is null");
                    Toast.makeText(ProductListActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e(TAG, "Failed to fetch products: " + t.getMessage());
                Toast.makeText(ProductListActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
