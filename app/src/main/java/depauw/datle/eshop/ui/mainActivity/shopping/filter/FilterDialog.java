package depauw.datle.eshop.ui.mainActivity.shopping.filter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.R;
import depauw.datle.eshop.databinding.DialogShoppingFilterBinding;
import depauw.datle.eshop.ui.mainActivity.shopping.ShoppingViewModel;

public class FilterDialog extends Dialog {
    private DialogShoppingFilterBinding binding;
    private ShoppingViewModel shoppingViewModel;
    private View.OnClickListener button_shopping_filter_apply_click_listener = view -> {
        if(Filters.getInstance().isPriceMaxInvalid()) {
            binding.editTextShoppingFilterPriceMax.setError("Max price must be greater or equal to min price.");
            Toast.makeText(binding.getRoot().getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
            return;
        }

        shoppingViewModel.filterProducts();
        Helper.hideKeyboard(binding.getRoot().getContext(), binding.getRoot().getWindowToken());
        this.dismiss();
    };
    private View.OnKeyListener editText_key_listener = (view, i, keyEvent) -> {
        EditText editText = (EditText)view;
        String priceText = editText.getText().toString();

        if(editText.getId() == R.id.editText_shopping_filter_price_min) {
            if(priceText.isEmpty()) {
                Filters.getInstance().setPriceMin(0);
            } else if(Helper.isValidPrice(priceText)) {
                float priceMin = Float.parseFloat(priceText);
                Filters.getInstance().setPriceMin(priceMin);
            }
        }
        if(editText.getId() == R.id.editText_shopping_filter_price_max) {
            if(priceText.isEmpty()) {
                Filters.getInstance().setPriceMax(Float.MAX_VALUE);
            } else if(Helper.isValidPrice(priceText)) {
                float priceMax = Float.parseFloat(priceText);
                Filters.getInstance().setPriceMax(priceMax);
            }
        }
        return false;
    };
    private View.OnClickListener button_shopping_filter_reset_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Filters.getInstance().setPriceMin(0);
            Filters.getInstance().setPriceMax(Float.MAX_VALUE);
            binding.editTextShoppingFilterPriceMin.setText("");
            binding.editTextShoppingFilterPriceMax.setText("");
        }
    };

    public FilterDialog(@NonNull Context context, ShoppingViewModel shoppingViewModel) {
        super(context);
        this.shoppingViewModel = shoppingViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogShoppingFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextShoppingFilterPriceMin.setOnKeyListener(editText_key_listener);
        binding.editTextShoppingFilterPriceMax.setOnKeyListener(editText_key_listener);
        binding.buttonShoppingFilterApply.setOnClickListener(button_shopping_filter_apply_click_listener);
        binding.buttonShoppingFilterReset.setOnClickListener(button_shopping_filter_reset_click_listener);
        if(Filters.getInstance().getPriceMin() > 0) {
            binding.editTextShoppingFilterPriceMin.setText(
                    String.valueOf(Filters.getInstance().getPriceMin())
            );
        }
        if(Filters.getInstance().getPriceMax() < Float.MAX_VALUE) {
            binding.editTextShoppingFilterPriceMax.setText(
                    String.valueOf(Filters.getInstance().getPriceMax())
            );
        }
    }
}
