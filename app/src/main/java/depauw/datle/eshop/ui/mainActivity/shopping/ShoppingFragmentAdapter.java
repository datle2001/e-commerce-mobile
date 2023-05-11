package depauw.datle.eshop.ui.mainActivity.shopping;

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

public class ShoppingFragmentAdapter extends RecyclerView.Adapter<ShoppingAdapterViewHolder> {
    private List<Product> products = new ArrayList<>();

    public ShoppingFragmentAdapter() {}

    @NonNull
    @Override
    public ShoppingAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_shopping_product, parent, false);

        return new ShoppingAdapterViewHolder(contactView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapterViewHolder holder, int position) {
        holder.bind(products.get(position));
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
