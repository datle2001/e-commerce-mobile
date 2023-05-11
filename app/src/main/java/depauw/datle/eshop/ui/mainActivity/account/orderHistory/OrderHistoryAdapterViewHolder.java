package depauw.datle.eshop.ui.mainActivity.account.orderHistory;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.data.model.Order;
import depauw.datle.eshop.ui.mainActivity.account.orderHistory.orderDetail.OrderDetailActivity;

public class OrderHistoryAdapterViewHolder extends RecyclerView.ViewHolder {
    public static final String ORDER_KEY = "orderProduct";
    private final TextView textViewOrderID;
    private final TextView textViewOrderDate;

    private Order order;
    private Context context;
    private final View.OnClickListener viewHolder_click_listener = view -> {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ORDER_KEY, order);
        startActivity(context, intent, null);
    };

    public OrderHistoryAdapterViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        textViewOrderID = view.findViewById(R.id.textView_order_id);
        textViewOrderDate = view.findViewById(R.id.textView_order_date);
        view.setOnClickListener(viewHolder_click_listener);
    }

    public void bind(Order order) {
        this.order = order;
        textViewOrderID.setText(Helper.addPoundKey(order.getId()));
        textViewOrderDate.setText(Helper.formatDate(order.getCreatedAt()));
    }
}
