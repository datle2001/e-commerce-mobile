package depauw.datle.eshop.ui.mainActivity.account.orderHistory.orderDetail;

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

public class OrderDetailActivityAdapter extends RecyclerView.Adapter<OrderDetailAdapterViewHolder>{
    private List<Product> orderProductList = new ArrayList<>();
    @NonNull
    @Override
    public OrderDetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_order_detail_product, parent, false);

        return new OrderDetailAdapterViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapterViewHolder holder, int position) {
        holder.bind(orderProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderProductList.size();
    }

    public void setOrderProductList(List<Product> orderProductList) {
        this.orderProductList = orderProductList;
        notifyDataSetChanged();
    }
}