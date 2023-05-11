package depauw.datle.eshop.data.dataSource;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.RetrofitClient;
import depauw.datle.eshop.data.WebInterface;
import depauw.datle.eshop.data.model.LoggedInUser;
import depauw.datle.eshop.data.model.Session;
import depauw.datle.eshop.data.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class UserDataSource {
    private final String TAG = "UDS";

    public void login(String email, String password, ActionCallback callback) {
        try {
            WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
            Call<Session> call = webInterface.login(new LoggedInUser(email, password));

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Session> call, Response<Session> response) {
                    if (response.isSuccessful()) {
                        callback.onActionSuccess(new Result.Success(response.body()));
                    } else {
                        Log.e(TAG, response.message());
                        callback.onActionFailure(new Result.Error(new Exception(response.message())));
                    }
                }

                @Override
                public void onFailure(Call<Session> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    callback.onActionFailure(new Result.Error(new Exception(t)));
                }
            });
        } catch (Exception e) {
            callback.onActionFailure(new Result.Error(e));
        }
    }

    public void logout(String token, ActionCallback callback) {
        try {
            WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
            Call<JSONObject> call = webInterface.logout(token);

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if(response.isSuccessful()) {
                        callback.onActionSuccess(new Result.Success(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    callback.onActionFailure(new Result.Error(Result.Error.ERROR_LOG_OUT_FAIL));
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loginWithToken(String token, ActionCallback callback) {
        try {
            WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
            Call<Session> call = webInterface.loginWithToken(token);

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Session> call, Response<Session> response) {
                    if (response.isSuccessful()) {
                        callback.onActionSuccess(new Result.Success(response.body()));
                    } else {
                        try {
                            Log.e(TAG, response.errorBody().string());
                            callback.onActionFailure(
                                    new Result.Error(new Exception(response.errorBody().string()))
                            );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Session> call, Throwable t) {
                    Log.e(TAG + "onFailure1", t.getMessage());
                    callback.onActionFailure(new Result.Error(new Exception(t)));
                }
            });
        } catch (Exception e) {
            callback.onActionFailure(new Result.Error(e));
        }
    }

    public void updateUser(ActionCallback callback, LoggedInUser user) {
        try {
            WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
            Session session = UserRepository.getInstance(null).getSession();
            Call<JSONObject> call = webInterface.updateUser(
                    session.getToken(),
                    session.getUserId(),
                    user
            );

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()) {
                        callback.onActionSuccess(new Result.Success(response.body()));
                    } else {
                        Log.e(TAG, response.message());
                        callback.onActionFailure(
                                new Result.Error(new Exception(response.message()))
                        );
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    callback.onActionFailure(new Result.Error(new Exception(t)));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            callback.onActionFailure(new Result.Error(e));
        }
    }
}