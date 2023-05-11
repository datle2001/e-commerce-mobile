package depauw.datle.eshop.data.repository;

import android.util.Log;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.dataSource.UserDataSource;
import depauw.datle.eshop.data.model.LoggedInUser;
import depauw.datle.eshop.data.model.Session;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository implements ActionCallback {
    private static volatile UserRepository userRepository;
    private UserDataSource userDataSource;
    private Session session;
    private ActionCallback actionCallback;

    private UserRepository(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public static UserRepository getInstance(UserDataSource userDataSource) {
        if (userRepository == null) {
            userRepository = new UserRepository(userDataSource);
        }
        return userRepository;
    }

    public void logout(ActionCallback callback) {
        userDataSource.logout(session.getToken(), callback);
    }

    public void setLoggedInSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void login(String email, String password, ActionCallback callback) {
        this.actionCallback = callback;
        userDataSource.login(email, password, this);
    }

    public void loginWithToken(String token, ActionCallback callback) {
        this.actionCallback = callback;
        userDataSource.loginWithToken(token, this);
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        setLoggedInSession((Session) success.getData());
        this.actionCallback.onActionSuccess(success);
    }

    @Override
    public void onActionFailure(Result.Error error) {
        Log.e("LR", error.toString());
        this.actionCallback.onActionFailure(error);
    }

    public void clearRepository() {
        session = null;
        userRepository = null;
        userDataSource = null;
    }

    public void updateUser(ActionCallback callback, LoggedInUser user) {
        userDataSource.updateUser(callback, user);
    }
}