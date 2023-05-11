package depauw.datle.eshop.ui.mainActivity.account.personalInfo;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import depauw.datle.eshop.R;
import depauw.datle.eshop.Utils;
import depauw.datle.eshop.data.repository.UserRepository;
import depauw.datle.eshop.databinding.ActivityPersonalInfoBinding;

public class PersonalInfoActivity extends AppCompatActivity {
    private static final String TAG = "PIA";
    String tag = "PIA";
    private ActivityPersonalInfoBinding binding;
    private PersonalInfoViewModel personalInfoViewModel;
    private View.OnClickListener button_personal_info_save_changes = view -> {
        personalInfoViewModel.saveUserInfo();
    };
    private View.OnClickListener imageButton_personal_info_dob = view -> {
        DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext());
        String dob = UserRepository.getInstance(null).getSession().getDob();

        if(dob != null && !dob.isEmpty()) {
            LocalDate dateTime = LocalDate.parse(dob);
            datePickerDialog.getDatePicker().updateDate(dateTime.getYear(), dateTime.getMonthValue()-1, dateTime.getDayOfMonth());
        }
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            personalInfoViewModel.setLiveDob(LocalDate.of(i,i1+1,i2).format(DateTimeFormatter.ISO_LOCAL_DATE));
        });
    };
    private View.OnKeyListener editText_personal_info_username_key_listener = (view, i, keyEvent) -> {
        String username = ((EditText) view).getText().toString();
        personalInfoViewModel.setLiveUsername(username);
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);

        super.onCreate(savedInstanceState);
        personalInfoViewModel = new ViewModelProvider(this, new PersonalInfoViewModelFactory()).get(PersonalInfoViewModel.class);;
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        disableEditFields();
        personalInfoViewModel.getLiveIsEditingEnabled().observe(this, isEditingEnabled -> {
            if(isEditingEnabled) {
                enableEditFields();
            } else {
                disableEditFields();
            }
        });
        personalInfoViewModel.getIsUpdateSuccess().observe(this, isUpdateSuccess -> {
            if(isUpdateSuccess) {
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                disableEditFields();
            } else {
                Toast.makeText(this, "Sorry we cannot update your information now", Toast.LENGTH_SHORT).show();
            }
        });
        personalInfoViewModel.getLiveDob().observe(this, dob -> {
            if(dob == null || dob.isBlank()) return;
            LocalDate dateTime = LocalDate.parse(dob);
            binding.textViewPersonalInfoDob.setText(dateTime.format(DateTimeFormatter.ofPattern("MM-dd-uuuu")));
        });
        binding.editTextPersonalInfoUsername.setText(UserRepository.getInstance(null).getSession().getUsername());

        ArrayAdapter<CharSequence> sexSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sexes, R.layout.item_spinner_sex_dropdown);
        binding.spinnerPersonalInfoSex.setAdapter(sexSpinnerAdapter);
        binding.spinnerPersonalInfoSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                personalInfoViewModel.setLiveSex(((TextView)view).getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        String dob = UserRepository.getInstance(null).getSession().getDob();

        if(dob != null && !dob.isEmpty()) {
            LocalDate dateTime = LocalDate.parse(dob);
            binding.textViewPersonalInfoDob.setText(dateTime.format(DateTimeFormatter.ofPattern("MM-dd-uuuu")));
        }

        String sex = UserRepository.getInstance(null).getSession().getSex();

        if(sex != null && !sex.isEmpty() && !sex.equals("Not selected")) {
            binding.spinnerPersonalInfoSex.setSelection(sex.equals("Male") ? 1 : 2);
        }
        binding.buttonPersonalInfoSaveChanges.setOnClickListener(button_personal_info_save_changes);
        binding.imageButtonPersonalInfoDob.setOnClickListener(imageButton_personal_info_dob);
        binding.editTextPersonalInfoUsername.setOnKeyListener(editText_personal_info_username_key_listener);
    }

    private void enableEditFields() {
        getSupportActionBar().hide();
        binding.spinnerPersonalInfoSex.setBackgroundTintMode(PorterDuff.Mode.ADD);
        binding.spinnerPersonalInfoSex.setEnabled(true);
        binding.editTextPersonalInfoUsername.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
        binding.editTextPersonalInfoUsername.setEnabled(true);
        binding.imageButtonPersonalInfoDob.setVisibility(View.VISIBLE);
        binding.textViewPersonalInfoDob.setAlpha(1f);
        binding.buttonPersonalInfoSaveChanges.setVisibility(View.VISIBLE);
    }

    private void disableEditFields() {
        binding.spinnerPersonalInfoSex.setEnabled(false);
        binding.spinnerPersonalInfoSex.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        binding.editTextPersonalInfoUsername.setEnabled(false);
        binding.editTextPersonalInfoUsername.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        binding.imageButtonPersonalInfoDob.setVisibility(View.INVISIBLE);
        binding.textViewPersonalInfoDob.setAlpha(0.5f);
        binding.buttonPersonalInfoSaveChanges.setVisibility(View.INVISIBLE);
        getSupportActionBar().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_bar_personal_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.personal_info_edit:
                personalInfoViewModel.setLiveIsEditingEnabled(true);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}