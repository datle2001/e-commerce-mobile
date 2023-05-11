package depauw.datle.eshop.ui.mainActivity.account.orderHistory.orderDetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.data.model.Order;
import depauw.datle.eshop.databinding.ActivityDetailOrderBinding;
import depauw.datle.eshop.ui.mainActivity.account.orderHistory.OrderHistoryAdapterViewHolder;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        OrderDetailViewModel orderDetailViewModel = new ViewModelProvider(this, new OrderDetailViewModelFactory()).get(OrderDetailViewModel.class);
        ActivityDetailOrderBinding binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.title_order_detail);
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(OrderHistoryAdapterViewHolder.ORDER_KEY);
        orderDetailViewModel.getOrderProductList(order.getId());

        binding.recyclerViewOrderDetailOrderList.setLayoutManager(
                new LinearLayoutManager(this)
        );
        OrderDetailActivityAdapter orderDetailActivityAdapter = new OrderDetailActivityAdapter();
        binding.recyclerViewOrderDetailOrderList.setAdapter(orderDetailActivityAdapter);
        binding.textViewOrderDetailOrderDate.setText(Helper.formatDate(order.getCreatedAt()));
        binding.textViewOrderDetailOrderId.setText(Helper.addPoundKey(order.getId()));
        orderDetailViewModel.getLiveOrderProductList().observe(this, orderProductList -> {
            binding.textViewOrderDetailOrderTotal.setText(
                    Helper.dollarize(Helper.getTotalCharge(orderProductList, false))
            );
            orderDetailActivityAdapter.setOrderProductList(orderProductList);
        });
    }
}