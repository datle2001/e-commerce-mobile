package depauw.datle.eshop.data.dataSource;

import android.util.Log;

import java.util.List;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.RetrofitClient;
import depauw.datle.eshop.data.WebInterface;
import depauw.datle.eshop.data.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource {
    public void getProductList(ActionCallback callback) {
        String tag = "ProductDS";
        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        Call<List<Product>> call = webInterface.getProducts();
        try {
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        callback.onActionSuccess(new Result.Success(response.body()));
                    } else {
                        Log.e(tag, "onResponse fail: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.e(tag, t.toString());
                    callback.onActionFailure(new Result.Error(Result.Error.ERROR_GET_PRODUCTS));
                }
            });
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            callback.onActionFailure(new Result.Error(e));
        }
    }
}
