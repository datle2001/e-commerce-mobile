package depauw.datle.eshop.ui.mainActivity.account.payments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import depauw.datle.eshop.Helper;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.data.model.Card;
import depauw.datle.eshop.databinding.ActivityPaymentsBinding;

//allow 1 card per person
public class PaymentsActivity extends AppCompatActivity {
    private final String tag = "PA";
    private ActivityPaymentsBinding binding;
    private PaymentsViewModel paymentsViewModel;
    private final View.OnKeyListener editText_card_number_key_listener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            String cardNumber = binding.editTextCardNumber.getText().toString();
            int cardNumberLength = cardNumber.length();

            if(i == KeyEvent.KEYCODE_DEL) {
                if(cardNumberLength == 0) {
                    return true;
                }
                if(cardNumber.charAt(cardNumberLength-1) == ' ') {
                    cardNumber = cardNumber.substring(0, cardNumberLength-1);
                    binding.editTextCardNumber.setText(cardNumber);
                    binding.editTextCardNumber.setSelection(cardNumber.length());
                }
            } else if(cardNumberLength == 4 || cardNumberLength == 9 || cardNumberLength == 14) {
                binding.editTextCardNumber.setText(cardNumber + " ");
                binding.editTextCardNumber.setSelection(cardNumberLength+1);
            }

            return false;
        }
    };
    private final View.OnKeyListener editText_effect_date_key_listener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            String effDate = binding.editTextEffectDate.getText().toString();
            int effDateLength = effDate.length();

            if(i == KeyEvent.KEYCODE_DEL) {
                if(effDateLength == 0) {
                    return true;
                }
                if(effDate.charAt(effDateLength-1) == '/') {
                    effDate = effDate.substring(0, effDateLength-1);
                    binding.editTextEffectDate.setText(effDate);
                    binding.editTextEffectDate.setSelection(effDate.length());
                }
            } else if(effDateLength == 2) {
                if(!Helper.isValidMonth(Integer.parseInt(effDate))) {
                    Toast.makeText(
                            binding.getRoot().getContext(),
                            "Month is invalid",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                binding.editTextEffectDate.setText(effDate + "/");
                binding.editTextEffectDate.setSelection(effDateLength+1);
            }

            return false;
        }
    };

    private final View.OnClickListener button_add_payment_click_listener = view -> {
        String cardholderName = binding.editTextCardholderName.getText().toString();
        String cardNumber = binding.editTextCardNumber.getText().toString();
        String effDate = binding.editTextEffectDate.getText().toString();
        int cvc = Integer.parseInt(binding.editTextCvc.getText().toString());
        Card card = new Card(cardholderName, cardNumber, effDate, cvc);
        paymentsViewModel.addCard(card);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        paymentsViewModel = new PaymentsViewModel();

        paymentsViewModel.isCardAdded().observe(this, isCardAdded -> {
            if(isCardAdded) {
                Toast.makeText(this, "Card added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        binding.editTextCardNumber.setOnKeyListener(editText_card_number_key_listener);
        binding.editTextEffectDate.setOnKeyListener(editText_effect_date_key_listener);
        binding.buttonAddPayment.setOnClickListener(button_add_payment_click_listener);
    }
}