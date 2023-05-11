package depauw.datle.eshop.ui.mainActivity.account.payments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import depauw.datle.eshop.data.RetrofitClient;
import depauw.datle.eshop.data.WebInterface;
import depauw.datle.eshop.data.model.Card;
import depauw.datle.eshop.data.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentsViewModel {
    private final String tag = "PVM";
    public PaymentsViewModel() {}
    private final MutableLiveData isCardAdded = new MutableLiveData(false);
    public LiveData<Boolean> isCardAdded() {
        return isCardAdded;
    }
    public void addCard(Card card) {
        //get all information
        //send to api to store and validate
        try {
            WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
            Call<Card> call = webInterface.addCard(UserRepository.getInstance(null).getSession().getToken(), card);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Card> call, Response<Card> response) {
                    if (response.isSuccessful()) {
                        isCardAdded.postValue(true);
                        UserRepository.getInstance(null).getSession().setUserCard(response.body());
                    } else {
                        try {
                            Log.e(tag, "onResponse fail: " + response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Card> call, Throwable t) {
                    Log.e(tag, t.toString());
                }
            });
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
        }
    }
}
