package com.test.yatfirstexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nameED, emailEd, passwordEd, rePasswordEd;
    Button singUpButton;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameED = findViewById(R.id.nameEditText);
        emailEd = findViewById(R.id.emailEditText);
        passwordEd = findViewById(R.id.singupPasswordEditText);
        rePasswordEd = findViewById(R.id.reSingupPasswordEditText);

        singUpButton = findViewById(R.id.singUpButton);


        singUpButton.setOnClickListener(view -> {
            final String name = nameED.getText().toString();
            final String email = emailEd.getText().toString();
            final String password = passwordEd.getText().toString();
            final String repassword = passwordEd.getText().toString();


            if ( TextUtils.isEmpty(name) ||
                    TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(repassword)) {

                Toast.makeText(RegistrationActivity.this,
                        "please complete all data", Toast.LENGTH_LONG).show();
            } else{
                dbHelper = new DBHelper(this);


                dbHelper.addUser(name, email, password);
                Toast.makeText(RegistrationActivity.this, "Sucess", Toast.LENGTH_LONG).show();
            }

        });
    }
}