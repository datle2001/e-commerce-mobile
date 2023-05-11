package depauw.datle.eshop.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.data.RetrofitClient;
import depauw.datle.eshop.data.WebInterface;
import depauw.datle.eshop.data.model.LoggedInUser;
import depauw.datle.eshop.data.model.Session;
import depauw.datle.eshop.databinding.ActivityRegisterBinding;
import depauw.datle.eshop.ui.mainActivity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private final String TAG = "RegisterActivity";
    private final View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Helper.hideKeyboard(binding.getRoot().getContext(), binding.getRoot().getWindowToken());
            String email = binding.edittextEmailRegister.getText().toString();
            String password = binding.edittextPasswordRegister.getText().toString();
            String username = binding.edittextUsernameRegister.getText().toString();

            if(!isDataValid(email, password, username)) {
                Toast.makeText(RegisterActivity.this, "Please fill out the information", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
                Call<Session> call = webInterface.register(new LoggedInUser(username, email, password));

                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Session> call, @NonNull Response<Session> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                            intent.putExtra("email", email);
                            intent.putExtra("token", response.body().getToken());
                            setResult(RESULT_OK, intent);
                            finish();
                        } else if (response.errorBody() != null) {
                            try {
                                JSONObject messageObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(
                                        RegisterActivity.this,
                                                messageObject.getString("message"),
                                        Toast.LENGTH_LONG)
                                        .show();
                            } catch (IOException | JSONException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    getString(R.string.unknown_error),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Session> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    };



    private boolean isDataValid(String email, String password, String username) {
        return !email.isEmpty() && !password.isEmpty() && !username.isEmpty();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonRegister.setOnClickListener(registerButtonClickListener);
    }
}