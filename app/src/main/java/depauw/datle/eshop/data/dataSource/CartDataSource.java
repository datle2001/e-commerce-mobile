package depauw.datle.eshop.data.dataSource;

import android.util.Log;

import org.json.JSONObject;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.RetrofitClient;
import depauw.datle.eshop.data.WebInterface;
import depauw.datle.eshop.data.repository.CartRepository;
import depauw.datle.eshop.data.repository.UserRepository;
import depauw.datle.eshop.data.repository.ProductRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDataSource {
    public void checkout(ActionCallback callback) {
        String tag = "CartDS";
        if(!UserRepository.getInstance(null).getSession().hasCard()) {
            callback.onActionFailure(new Result.Error(Result.Error.ERROR_NO_CARD));
            return;
        }
        WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
        Call<JSONObject> call = webInterface.checkout(
                UserRepository.getInstance(null).getSession().getToken(),
                CartRepository.getInstance(null).getLiveProducts().getValue()
        );
        try {
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()) {
                        CartRepository.getInstance(null).clearCart();
                        ProductRepository.getInstance(null).clearProductList();
                        callback.onActionSuccess(new Result.Success(response.body()));
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    Log.e(tag, t.toString());
                    callback.onActionFailure(new Result.Error(new Exception(t)));
                }
            });
        } catch (Exception e) {
            Log.e(tag, e.toString());
            callback.onActionFailure(new Result.Error(e));
        }
    }
}
