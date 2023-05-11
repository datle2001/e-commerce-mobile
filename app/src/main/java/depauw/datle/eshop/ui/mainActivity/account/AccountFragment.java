package depauw.datle.eshop.ui.mainActivity.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.data.repository.UserRepository;
import depauw.datle.eshop.databinding.FragmentAccountBinding;
import depauw.datle.eshop.ui.login.LoginActivity;
import depauw.datle.eshop.ui.mainActivity.account.editTheme.EditThemeActivity;
import depauw.datle.eshop.ui.mainActivity.account.orderHistory.OrderHistoryActivity;
import depauw.datle.eshop.ui.mainActivity.account.payments.PaymentsActivity;
import depauw.datle.eshop.ui.mainActivity.account.personalInfo.PersonalInfoActivity;

public class AccountFragment extends Fragment implements ActionCallback, View.OnClickListener {
    private AccountViewModel accountViewModel;
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonSignout.setOnClickListener(this);
        binding.buttonPayment.setOnClickListener(this);
        binding.buttonThemes.setOnClickListener(this);
        binding.textViewAccountUsername.setText(
                UserRepository.getInstance(null).getSession().getUsername()
        );
        binding.buttonOrderHistory.setOnClickListener(this);
        binding.buttonPersonalInfo.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void removeAccessToken() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                getResources().getString(R.string.shared_pref),
                Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.token_pref));
        editor.apply();
    }
    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        Toast.makeText(this.getContext(), "You are logged out successfully", Toast.LENGTH_SHORT).show();
        removeAccessToken();
        toLoginActivity();
        /*
        CartRepository.getInstance(null).clearRepository();
        ProductRepository.getInstance(null).clearRepository();
        OrderRepository.getInstance(null).clearRepository();
        UserRepository.getInstance(null).clearRepository();*/
        Utils.reset();
    }

    @Override
    public void onActionFailure(Result.Error error) {
        Toast.makeText(this.getContext(), "Sorry you cannot log out now.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_payment:
                Intent intent = new Intent(binding.getRoot().getContext(), PaymentsActivity.class);
                startActivity(intent);
                break;
            case R.id.button_themes:
                intent = new Intent(binding.getRoot().getContext(), EditThemeActivity.class);
                startActivity(intent);
                break;
            case R.id.button_order_history:
                intent = new Intent(binding.getRoot().getContext(), OrderHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.button_personal_info:
                intent = new Intent(binding.getRoot().getContext(), PersonalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.button_signout:
                accountViewModel.logout(AccountFragment.this);
                break;
        }
    }
}