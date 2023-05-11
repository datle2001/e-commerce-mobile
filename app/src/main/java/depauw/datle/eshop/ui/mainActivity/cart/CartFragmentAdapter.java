package depauw.datle.eshop.ui.mainActivity.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import depauw.datle.eshop.R;
import depauw.datle.eshop.data.model.Product;

public class CartFragmentAdapter extends RecyclerView.Adapter<CartAdapterViewHolder> {
    private List<Product> products = new ArrayList();
    public CartFragmentAdapter() {}

    @NonNull
    @Override
    public CartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_cart_product, parent, false);
        return new CartAdapterViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapterViewHolder holder, int position) {
        holder.bind(products.get(position), position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
