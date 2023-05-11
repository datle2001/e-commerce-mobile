package depauw.datle.eshop.data.repository;

import java.util.ArrayList;
import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.dataSource.ProductDataSource;
import depauw.datle.eshop.data.model.Product;

public class ProductRepository implements ActionCallback{
    private static volatile ProductRepository productRepository = null;
    private ProductDataSource productDataSource;
    private List<Product> productList = new ArrayList<>();
    private ActionCallback callbackVM;
    private ProductRepository(ProductDataSource productDataSource) {this.productDataSource = productDataSource;}

    public static ProductRepository getInstance(ProductDataSource productDataSource) {
        if(productRepository == null) {
            productRepository = new ProductRepository(productDataSource);
        }
        return productRepository;
    }

    public void getProductList(ActionCallback callbackVM) {
        if(productList.size() == 0) {
            this.callbackVM = callbackVM;
            productDataSource.getProductList(this);

        } else {
            callbackVM.onActionSuccess(new Result.Success(productList));
        }

    }

    public List<Product> getProducts() {
        return productList;
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        productList = (List<Product>) success.getData();
        callbackVM.onActionSuccess(success);
    }

    @Override
    public void onActionFailure(Result.Error error) {
        callbackVM.onActionFailure(error);
    }

    public void clearProductList() {
        productList.clear();
    }
    public void clearRepository() {
        productRepository = null;
        productDataSource = null;
        clearProductList();
    }
}
