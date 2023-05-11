package depauw.datle.eshop.ui.mainActivity.account.orderHistory.orderDetail;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.data.model.Product;

public class OrderDetailAdapterViewHolder extends RecyclerView.ViewHolder {

    private final View view;
    public OrderDetailAdapterViewHolder(View view) {
        super(view);
        this.view = view;
    }

    public void bind(Product orderProduct) {
        Product product = Helper.findProductByID(orderProduct.getId());
        TextView textViewOrderDetailProductName = view.findViewById(R.id.textView_order_detail_product_name);
        textViewOrderDetailProductName.setText(product.getName());

        TextView textViewOrderDetailProductPrice = view.findViewById(R.id.textView_order_detail_product_price);
        textViewOrderDetailProductPrice.setText(Helper.dollarize(product.getPrice()));

        TextView textViewOrderDetailProductQuantity = view.findViewById(R.id.textView_order_detail_product_quantity);
        textViewOrderDetailProductQuantity.setText(String.valueOf(orderProduct.getQuantityPurchase()));

        TextView textViewOrderDetailProductTotal = view.findViewById(R.id.textView_order_detail_product_total);
        textViewOrderDetailProductTotal.setText(Helper.dollarize(orderProduct.getQuantityPurchase() * product.getPrice()));

        ImageView imageViewOrderDetailProductPhoto = view.findViewById(R.id.imageView_order_detail_product_photo);
        Bitmap photo = product.getPhoto();
        if(photo != null) {
            imageViewOrderDetailProductPhoto.setImageBitmap(photo);
        }
    }
}
