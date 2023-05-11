package depauw.datle.eshop.ui.mainActivity.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.data.ActionCallback;
import depauw.datle.eshop.data.Result;
import depauw.datle.eshop.databinding.FragmentCartBinding;
import depauw.datle.eshop.ui.mainActivity.account.payments.PaymentsActivity;

public class CartFragment extends Fragment implements ActionCallback {
    private final String TAG = "CF";
    private FragmentCartBinding binding;
    private CartFragmentAdapter cartFragmentAdapter;
    private CartViewModel cartViewModel;
    private final View.OnClickListener button_checkout_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cartViewModel.checkout(CartFragment.this);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this, new CartViewModelFactory()).get(CartViewModel.class);
        cartViewModel.getProducts().observe(this, products -> {
            cartFragmentAdapter.setProducts(products);
            binding.buttonCheckout.setEnabled(products.size() > 0);
            binding.textViewSubtotal.setText(Helper.dollarize(Helper.getTotalCharge(products, true)));
        });
        binding = FragmentCartBinding.inflate(inflater, container, false);
        cartFragmentAdapter = new CartFragmentAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setAdapter(cartFragmentAdapter);
        binding.buttonCheckout.setOnClickListener(button_checkout_click_listener);

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActionSuccess(Result.Success success) {
        Toast.makeText(getContext(), "Thank you for your purchase!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionFailure(Result.Error error) {
        if(error.getErrorCode() == Result.Error.ERROR_NO_CARD) {
            Toast.makeText(getActivity(), "Please add a payment method", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getContext(), PaymentsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this.getContext(), "Sorry we cannot process your purchase at this time.", Toast.LENGTH_SHORT).show();
        }
    }
}