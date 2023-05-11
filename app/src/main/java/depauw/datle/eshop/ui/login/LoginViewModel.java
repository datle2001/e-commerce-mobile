package depauw.datle.eshop.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import depauw.datle.eshop.R;
import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.model.Session;
import depauw.datle.eshop.data.repository.UserRepository;

public class LoginViewModel extends ViewModel implements ActionCallback {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final UserRepository userRepository;

    LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        userRepository.login(email, password, this);
    }

    public void loginWithToken(String token) {
        userRepository.loginWithToken(token, this);
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        Session session = (Session) success.getData();
        loginResult.setValue(new LoginResult(new LoggedInUserView(
                session.getUser().getUsername(),
                session.getToken(),
                session.getUser().getEmail()
        )));
    }

    @Override
    public void onActionFailure(Result.Error error) {
        Log.e("LVM", error.toString());
        loginResult.setValue(new LoginResult(R.string.login_failed));
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}