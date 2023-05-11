package depauw.datle.eshop.ui.mainActivity.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.repository.CartRepository;

public class CartViewModel extends ViewModel {
    private CartRepository cartRepository;
    public CartViewModel(CartRepository cartRepository) {this.cartRepository = cartRepository;}

    public LiveData<List<Product>> getProducts() {
        return cartRepository.getLiveProducts();
    }
    public void checkout(ActionCallback callback) {
        cartRepository.checkout(callback);
    }
}