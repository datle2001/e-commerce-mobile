package depauw.datle.eshop.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.databinding.ActivityLoginBinding;
import depauw.datle.eshop.ui.mainActivity.MainActivity;
import depauw.datle.eshop.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;
    private LoginViewModel loginViewModel;
    ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContract<>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Intent input) {
            return input;
        }

        @Override
        public ActivityResult parseResult(int resultCode, @Nullable Intent intent) {
            // Parse the activity result
            return new ActivityResult(resultCode, intent);
        }
    };

    // Create an activity result launcher
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(contract, result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // Handle the activity result
            Intent data = result.getData();
            String email = data.getStringExtra("email");
            String token = data.getStringExtra("token");

            binding.edittextEmailLogin.setText(email);
            // start login
            binding.loading.setVisibility(View.VISIBLE);
            loginViewModel.loginWithToken(token);
        }
    });

    private final View.OnClickListener registerButtonClickListener = view -> {
        Intent intent = new Intent(view.getContext(), RegisterActivity.class);
        launcher.launch(intent);
    };
    private final View.OnClickListener loginButtonClickListener = view -> {
        binding.loading.setVisibility(View.VISIBLE);
        loginViewModel.login(
                binding.edittextEmailLogin.getText().toString().trim(),
                binding.edittextPasswordLogin.getText().toString()
        );
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        //check and login with user credentials are in shared storage
        checkLoginUser();

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            binding.buttonLogin.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailAddressError() != null) {
                binding.edittextEmailLogin.setError(getString(loginFormState.getEmailAddressError()));
            }
            if (loginFormState.getPasswordError() != null) {
                binding.edittextPasswordLogin.setError(getString(loginFormState.getPasswordError()));
            }
        });
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            binding.loading.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }

            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
                saveCredentials(loginResult);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(binding.edittextEmailLogin.getText().toString(),
                        binding.edittextPasswordLogin.getText().toString());
            }
        };
        binding.edittextEmailLogin.addTextChangedListener(afterTextChangedListener);
        binding.edittextPasswordLogin.addTextChangedListener(afterTextChangedListener);
        binding.edittextPasswordLogin.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                        binding.edittextEmailLogin.getText().toString(),
                        binding.edittextPasswordLogin.getText().toString());
            }
            return false;
        });

        binding.buttonLogin.setOnClickListener(loginButtonClickListener);
        binding.buttonToRegister.setOnClickListener(registerButtonClickListener);
    }

    private void checkLoginUser() {
        sharedPreferences = getSharedPreferences(
                getResources().getString(R.string.shared_pref),
                Context.MODE_PRIVATE
        );

        String token = sharedPreferences.getString(getString(R.string.token_pref), "");
        if(!token.isEmpty()) {
            binding.loading.setVisibility(View.VISIBLE);
            loginViewModel.loginWithToken(token);
        } else {
            String email = sharedPreferences.getString(getString(R.string.email_pref), "");
            binding.edittextEmailLogin.setText(email);
        }
    }
    private void saveCredentials(LoginResult loginResult) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.email_pref), loginResult.getSuccess().getEmail());
        editor.putString(getString(R.string.token_pref), loginResult.getSuccess().getToken());

        editor.apply();
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + ' ' + model.getDisplayName() + '!';
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}