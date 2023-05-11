package depauw.datle.eshop.data.repository;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.dataSource.OrderDataSource;

public class OrderRepository {
    private static volatile OrderRepository orderRepository = null;
    private OrderDataSource orderDataSource;
    private OrderRepository(OrderDataSource orderDataSource) {this.orderDataSource = orderDataSource;}

    public static OrderRepository getInstance(OrderDataSource orderDataSource) {
        if(orderRepository == null) {
            orderRepository = new OrderRepository(orderDataSource);
        }
        return orderRepository;
    }

    public void getOrderHistory(ActionCallback callbackVM) {
        orderDataSource.getOrderHistory(callbackVM);
    }

    public void getOrderProductList(ActionCallback callbackVM, int orderID) {
        orderDataSource.getOrderProductList(callbackVM, orderID);
    }

    public void clearRepository() {
        orderDataSource = null;
        orderRepository = null;
    }
}
