package depauw.datle.eshop.ui.mainActivity.account.orderHistory.orderDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.repository.OrderRepository;

public class OrderDetailViewModel extends ViewModel implements ActionCallback {
    private final MutableLiveData<List<Product>> liveOrderProductList = new MutableLiveData<>();
    private final OrderRepository orderRepository;
    private int orderID;
    public OrderDetailViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void getOrderProductList(int orderID) {
        this.orderID = orderID;
        orderRepository.getOrderProductList(this, orderID);
    }

    public LiveData<List<Product>> getLiveOrderProductList() {
        return liveOrderProductList;
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        liveOrderProductList.postValue((List<Product>) success.getData());
    }

    @Override
    public void onActionFailure(Result.Error error) {
        getOrderProductList(orderID);
    }
}
