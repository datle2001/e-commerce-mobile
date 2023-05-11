package depauw.datle.eshop.ui.mainActivity.shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.model.Product;
import depauw.datle.eshop.data.repository.ProductRepository;
import depauw.datle.eshop.ui.mainActivity.shopping.filter.Filters;

public class ShoppingViewModel extends ViewModel implements ActionCallback {
    private final String tag = "SVM";
    private final MutableLiveData<List<Product>> liveProducts = new MutableLiveData();
    private ProductRepository productRepository;
    public ShoppingViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void getProductList() {
        productRepository.getProductList(this);
    }

    public LiveData<List<Product>> getLiveProducts() {
        return liveProducts;
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        liveProducts.postValue((List<Product>) success.getData());
    }

    @Override
    public void onActionFailure(Result.Error error) {
        getProductList();
    }

    public void filterProducts() {
        List<Product> allProducts = ProductRepository.getInstance(null).getProducts();
        List<Product> matchedProducts = new ArrayList<>();
        String keyword = Filters.getInstance().getKeyword();
        float priceMin = Filters.getInstance().getPriceMin();
        float priceMax = Filters.getInstance().getPriceMax();

        for(Product product : allProducts) {
            if(product.getName().toLowerCase().contains(keyword)
                    && product.getPrice() >= priceMin
                    && product.getPrice() <= priceMax)
            {
                matchedProducts.add(product);
            }
        }

        liveProducts.postValue(matchedProducts);
    }
}