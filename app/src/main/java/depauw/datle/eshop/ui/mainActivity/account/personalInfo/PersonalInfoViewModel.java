package depauw.datle.eshop.ui.mainActivity.account.personalInfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.model.LoggedInUser;
import depauw.datle.eshop.data.repository.UserRepository;

public class PersonalInfoViewModel extends ViewModel implements ActionCallback {
    private static final String TAG = "PIVM";
    private final UserRepository userRepository;
    private MutableLiveData<Boolean> liveIsEditingEnabled = new MutableLiveData<>(false);
    private MutableLiveData<String> liveDob = new MutableLiveData<>();
    private MutableLiveData<String> liveSex = new MutableLiveData<>();
    private MutableLiveData<String> liveUsername = new MutableLiveData<>();
    private MutableLiveData<Boolean> liveIsUpdateSuccess = new MutableLiveData<>();
    public PersonalInfoViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        LoggedInUser user = UserRepository.getInstance(null).getSession().getUser();
        liveDob.postValue(user.getDob());
        liveSex.postValue(user.getSex());
        liveUsername.postValue(user.getUsername());
    }

    public void setLiveSex(String sex) {
        this.liveSex.postValue(sex);
    }

    public void setLiveUsername(String username) {
        this.liveUsername.postValue(username);
    }

    public MutableLiveData<String> getLiveDob() {
        return liveDob;
    }

    public void setLiveDob(String liveDob) {
        this.liveDob.postValue(liveDob);
    }

    public MutableLiveData<Boolean> getLiveIsEditingEnabled() {
        return liveIsEditingEnabled;
    }

    public void setLiveIsEditingEnabled(Boolean liveIsEditingEnabled) {
        this.liveIsEditingEnabled.postValue(liveIsEditingEnabled);
    }
    public LiveData<Boolean> getIsUpdateSuccess() {
        return liveIsUpdateSuccess;
    }
    public void saveUserInfo() {
        Log.i(TAG, liveUsername.getValue());
       LoggedInUser user =  new LoggedInUser(liveUsername.getValue(), liveDob.getValue(), liveSex.getValue(), true);
        userRepository.updateUser(this, user);
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        UserRepository.getInstance(null).getSession().getUser().setDob(liveDob.getValue());
        UserRepository.getInstance(null).getSession().getUser().setSex(liveSex.getValue());
        UserRepository.getInstance(null).getSession().getUser().setUsername(liveUsername.getValue());
        liveIsUpdateSuccess.postValue(true);
    }

    @Override
    public void onActionFailure(Result.Error error) {
        liveIsUpdateSuccess.postValue(false);
    }
}
