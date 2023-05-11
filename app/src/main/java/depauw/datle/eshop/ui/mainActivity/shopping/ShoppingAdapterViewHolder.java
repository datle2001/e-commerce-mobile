package depauw.datle.eshop.ui.mainActivity.shopping;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.repository.CartRepository;
import depauw.datle.eshop.ui.mainActivity.shopping.productDetail.ProductDetailActivity;

public class ShoppingAdapterViewHolder extends RecyclerView.ViewHolder {
    public static final String PRODUCT_KEY = "product";
    private final TextView textViewName;
    private final TextView textViewPrice;
    private final ImageView imageViewPhoto;
    private final Button buttonAddToCart;
    private final View.OnClickListener product_view_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra(PRODUCT_KEY, product);
            startActivity(context, intent, null);
        }
    };
    private Product product;
    private final Context context;
    private final View.OnClickListener button_add_to_cart_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean addSuccess = CartRepository.getInstance(null).addProduct(product.duplicate());
            String message = addSuccess ? product.getName() + " is added" : "Sorry, this product has run out of stock";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };

    public ShoppingAdapterViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        textViewName = view.findViewById(R.id.textView_shopping_product_name);
        imageViewPhoto = view.findViewById(R.id.imageView_shopping_product_photo);
        textViewPrice = view.findViewById(R.id.textView_shopping_product_price);
        buttonAddToCart = view.findViewById(R.id.button_add_to_cart);
        buttonAddToCart.setOnClickListener(button_add_to_cart_clickListener);
        view.setOnClickListener(product_view_clickListener);
    }

    public void bind(Product product) {
        this.product = product;
        textViewName.setText(product.getName());
        textViewPrice.setText(Helper.dollarize(product.getPrice()));
        buttonAddToCart.setEnabled(product.getQuantityAvailable() > 0);
        Bitmap photo = product.getPhoto();

        if(photo != null) {
            imageViewPhoto.setImageBitmap(photo);
        }

    }
}
