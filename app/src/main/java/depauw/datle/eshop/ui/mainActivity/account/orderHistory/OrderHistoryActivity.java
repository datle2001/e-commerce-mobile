package depauw.datle.eshop.ui.mainActivity.account.orderHistory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.databinding.ActivityOrderHistoryBinding;

public class OrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.order_history));
        OrderHistoryViewModel orderHistoryViewModel = new ViewModelProvider(this, new OrderHistoryViewModelFactory()).get(OrderHistoryViewModel.class);
        ActivityOrderHistoryBinding binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerViewOrderHistory.setLayoutManager(
                new LinearLayoutManager(binding.getRoot().getContext())
        );
        OrderHistoryActivityAdapter orderHistoryActivityAdapter = new OrderHistoryActivityAdapter();
        binding.recyclerViewOrderHistory.setAdapter(orderHistoryActivityAdapter);
        orderHistoryViewModel.getOrderHistory();
        orderHistoryViewModel.getLiveOrders().observe(this, orderHistoryActivityAdapter::setOrders);

    }
}