package depauw.datle.eshop.ui.mainActivity.account.orderHistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.model.Order;
import depauw.datle.eshop.data.repository.OrderRepository;

public class OrderHistoryViewModel extends ViewModel implements ActionCallback {
    private MutableLiveData<List<Order>> liveOrders = new MutableLiveData<>();
    private OrderRepository orderRepository;
    public OrderHistoryViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void getOrderHistory() {
        orderRepository.getOrderHistory(this);
    }

    public LiveData<List<Order>> getLiveOrders() {
        return liveOrders;
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        liveOrders.postValue((List<Order>) success.getData());
    }

    @Override
    public void onActionFailure(Result.Error error) {
        getOrderHistory();
    }
}
