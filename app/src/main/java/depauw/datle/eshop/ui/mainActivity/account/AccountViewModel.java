package depauw.datle.eshop.ui.mainActivity.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.repository.UserRepository;

public class AccountViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void logout(ActionCallback callback) {
        UserRepository.getInstance(null).logout(callback);
    }
}