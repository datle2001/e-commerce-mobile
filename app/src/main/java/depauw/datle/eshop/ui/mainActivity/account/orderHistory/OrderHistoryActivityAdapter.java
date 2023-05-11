package depauw.datle.eshop.ui.mainActivity.account.orderHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import depauw.datle.eshop.R;
import depauw.datle.eshop.data.model.Order;

public class OrderHistoryActivityAdapter extends RecyclerView.Adapter<OrderHistoryAdapterViewHolder>{
    private List<Order> orderList = new ArrayList<>();
    @NonNull
    @Override
    public OrderHistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_order_history_order, parent, false);

        return new OrderHistoryAdapterViewHolder(contactView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapterViewHolder holder, int position) {
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrders(List<Order> orderProducts) {
        this.orderList = orderProducts;
        notifyDataSetChanged();
    }
}