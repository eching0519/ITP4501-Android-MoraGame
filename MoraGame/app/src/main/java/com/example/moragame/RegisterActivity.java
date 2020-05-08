package com.example.moragame;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Calendar;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName,etDob,etPhone,etEmail;
    private Button btnRegister,btnRemoveInfo;
    private TextView tvMessage;
    private Intent intent = null;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etDob = findViewById(R.id.etDob);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnRemoveInfo = findViewById(R.id.btnRemoveInfo);
        tvMessage = findViewById(R.id.tvMessage);

        try {
            Intent intent = getIntent();
            boolean viewRegisteredInfo = intent.getBooleanExtra("view",false);
            settings = getSharedPreferences(NOTES, MODE_PRIVATE);
            if(!viewRegisteredInfo && !settings.getString("name","").equals("")) {
                register();
            } else if (viewRegisteredInfo) {
                setEditText();
                btnRemoveInfo.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        etDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDatePickerDialog(v);
                }
            }
        });
    }

    private void showDatePickerDialog(View v) {
        Calendar calendar = Calendar.getInstance();
        int todayYear, todayMonth, todayDay;
        if(etDob.getText().toString().isEmpty()) {
            todayYear = calendar.get(Calendar.YEAR);
            todayMonth = calendar.get(Calendar.MONTH)+1;
            todayDay = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            String[] date = etDob.getText().toString().split("-");
            todayYear = Integer.parseInt(date[0]);
            todayMonth = Integer.parseInt(date[1]);
            todayDay = Integer.parseInt(date[2]);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateTime = String.format("%d-%d-%d", year,month+1,dayOfMonth);
                etDob.setText(dateTime);
                etPhone.requestFocus();
            }
        }, todayYear, todayMonth-1, todayDay);
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#D71C60"));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#D71C60"));
    }

    public void onClick(View view) {
        if(view.getId()==R.id.btnRemoveInfo) {
            clearInfo();
        }

        if(view.getId() == R.id.btnRegister) {
            String name = etName.getText().toString();
            String dob = etDob.getText().toString();
            String phone = etPhone.getText().toString();
            String email = etEmail.getText().toString();

            if (name.length() <= 0 || dob.length() <= 0 || phone.length() <= 0 || email.length() <= 0) {
                tvMessage.setText("Please complete the form.");
                return;
            }

            tvMessage.setText("");
            storeInfo(name, dob, phone, email);
            register();
        }
    }

    private void clearInfo() {
        try{
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();

            etName.setText("");
            etDob.setText("");
            etPhone.setText("");
            etEmail.setText("");
            editor.commit();

        } catch (Exception e){

        }
    }

    private void storeInfo(String name, String dob, String phone, String email) {
        try {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name",name);
            editor.putString("birth", dob);
            editor.putString("phone", phone);
            editor.putString("email", email);
            editor.apply();
            editor.commit();
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void register() {
        intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void setEditText() {
        String name = settings.getString("name","");
        etName.setText(name);

        String dob = settings.getString("birth", "");
        etDob.setText(dob);

        String phone = settings.getString("phone","");
        etPhone.setText(phone);

        String email = settings.getString("email","");
        etEmail.setText(email);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
