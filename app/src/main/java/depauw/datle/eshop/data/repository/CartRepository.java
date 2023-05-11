package depauw.datle.eshop.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.dataSource.CartDataSource;
import depauw.datle.eshop.data.model.Product;

public class CartRepository {
    private static volatile CartRepository cartRepository = null;
    private CartDataSource cartDataSource;
    private final MutableLiveData<List<Product>> liveProducts = new MutableLiveData<>(new ArrayList<>());
    private CartRepository(CartDataSource cartDataSource) {this.cartDataSource = cartDataSource;}

    public static CartRepository getInstance(CartDataSource cartDataSource) {
        if(cartRepository == null) {
            cartRepository = new CartRepository(cartDataSource);
        }
        return cartRepository;
    }

    public MutableLiveData<List<Product>> getLiveProducts() {
        return liveProducts;
    }

    public boolean addProduct(Product product) {
        boolean added = false;
        List<Product> currentProducts = liveProducts.getValue();

        for(int i = 0; i<currentProducts.size(); i++) {
            Product currentProduct = currentProducts.get(i);

            if(currentProduct.getId() == product.getId()) {
                int quantityPurchase = currentProduct.getQuantityPurchase();

                if(quantityPurchase == product.getQuantityAvailable()) {
                    return false;
                }

                currentProduct.setQuantityPurchase(quantityPurchase+ 1);
                added = true;
                break;
            }
        }

        if(!added) {
            product.setQuantityPurchase(1);
            currentProducts.add(product);
        }

        return true;
    }

    public void removeProduct(Product product) {
        liveProducts.getValue().remove(product);
        updateCart();
    }

    public void changeQuantity(int position, int newQuantity) {
        liveProducts.getValue().get(position).setQuantityPurchase(newQuantity);
        updateCart();
    }

    private void updateCart() {
        liveProducts.postValue(liveProducts.getValue());
    }
    public void checkout(ActionCallback callback) {
        if(cartDataSource == null) {
            Log.e("CR", "DS is null");
            return;
        }
        cartDataSource.checkout(callback);
    }

    public void clearCart() {
        liveProducts.setValue(new ArrayList<>());
    }

    public void clearRepository() {
        cartRepository = null;
        cartDataSource = null;
        clearCart();
    }
}