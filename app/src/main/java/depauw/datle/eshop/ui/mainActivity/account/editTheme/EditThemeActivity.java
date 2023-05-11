package depauw.datle.eshop.ui.mainActivity.account.editTheme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.databinding.ActivityEditThemeBinding;

public class EditThemeActivity extends AppCompatActivity {
    private ActivityEditThemeBinding binding;
    private final String tag = "ETA";
    private final View.OnClickListener button_theme_confirm_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utils.confirmTheme((Activity) binding.getRoot().getContext());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityEditThemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int radioButtonId = getIntent().getIntExtra(getString(R.string.radioButton_intent_pref), 0);

        if(radioButtonId > 0) {
            binding.radioGroupThemes.check(radioButtonId);
        }
        binding.radioGroupThemes.setOnCheckedChangeListener((radioGroup, i) -> Utils.changeToTheme(this, Utils.radioButtonId_theme.get(i), i));

        binding.buttonThemeConfirm.setOnClickListener(button_theme_confirm_click_listener);
    }
}