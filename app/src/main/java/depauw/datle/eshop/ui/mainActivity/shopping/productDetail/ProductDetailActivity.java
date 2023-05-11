package depauw.datle.eshop.ui.mainActivity.shopping.productDetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.databinding.ActivityDetailProductBinding;
import depauw.datle.eshop.ui.mainActivity.shopping.ShoppingAdapterViewHolder;

public class ProductDetailActivity extends AppCompatActivity {
    private final String TAG = "PDA";
    private ActivityDetailProductBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra(ShoppingAdapterViewHolder.PRODUCT_KEY);
        String availMessage = product.getQuantityAvailable() > 0 ? "In stock" : "Not available";

        setContentView(binding.getRoot());
        setTitle(product.getName());
        binding.imageViewProductDetailPhoto.setImageBitmap(product.getPhoto());
        binding.textViewProductDetailName.setText(product.getName());
        binding.textViewProductDetailPrice.setText(Helper.dollarize(product.getPrice()));
        binding.textViewProductDetailDescription.setText(product.getDescription());
        binding.textViewProductDetailAvailability.setText(availMessage);
    }
}