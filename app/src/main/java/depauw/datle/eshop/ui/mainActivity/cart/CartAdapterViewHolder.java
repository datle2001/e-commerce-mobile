package depauw.datle.eshop.ui.mainActivity.cart;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.repository.CartRepository;

public class CartAdapterViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewCartName;
    private final TextView textViewCartPrice;
    private final TextView textViewTotal;
    private final ImageView imageViewCartPhoto;
    private final TextView editTextQuantity;
    private Product product;
    private int position;
    private final View.OnClickListener imageView_remove_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CartRepository.getInstance(null).removeProduct(product);
        }
    };

    private final TextView.OnEditorActionListener edittext_quantity_actionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if(i != EditorInfo.IME_ACTION_DONE) {
                return true;
            }

            String quantityText = textView.getText().toString();

            if(quantityText.isEmpty()) {
                textView.setError("Quantity cannot be empty");
                return true;
            }

            if(quantityText.equals("0")) {
                textView.clearFocus();
                CartRepository.getInstance(null).removeProduct(product);
                return false;
            }

            int newQuantity = Integer.parseInt(quantityText);
            int quantityAvailable = product.getQuantityAvailable();

            if(newQuantity <= quantityAvailable) {
                textView.clearFocus();
                CartRepository.getInstance(null).changeQuantity(position, newQuantity);
                return false;
            }

            Toast.makeText(
                    textView.getContext(),
                    String.format("Sorry we only have %d in stock", quantityAvailable),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    };
    public CartAdapterViewHolder(View view) {
        super(view);
        textViewCartName = view.findViewById(R.id.textView_order_detail_product_name);
        imageViewCartPhoto = view.findViewById(R.id.imageView_order_detail_product_photo);
        textViewCartPrice = view.findViewById(R.id.textView_order_detail_product_price);
        textViewTotal = view.findViewById(R.id.textView_order_detail_product_total);
        editTextQuantity = view.findViewById(R.id.editText_quantity);
        editTextQuantity.setOnEditorActionListener(edittext_quantity_actionListener);
        ImageView imageViewRemove = view.findViewById(R.id.imageView_remove);
        imageViewRemove.setOnClickListener(imageView_remove_clickListener);
    }

    public void bind(Product product, int position) {
        this.position = position;
        this.product = product;
        textViewCartName.setText(product.getName());
        textViewCartPrice.setText(Helper.dollarize(product.getPrice()));
        textViewTotal.setText(Helper.dollarize(product.getPrice()* product.getQuantityPurchase()));
        editTextQuantity.setText(String.valueOf(product.getQuantityPurchase()));
        imageViewCartPhoto.setImageBitmap(product.getPhoto());
    }
}
