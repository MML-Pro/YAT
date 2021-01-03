package com.test.yatfirstexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText emailED, passwordED;
    private Button singIn;
    private TextView newUserSingUpTV;
    private DBHelper dbHelper;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailED = findViewById(R.id.emailEditText);
        passwordED = findViewById(R.id.passwordEditText);
        newUserSingUpTV = findViewById(R.id.newUserSingUpTV);
        singIn = findViewById(R.id.singInButton);
        dbHelper = new DBHelper(this);

        singIn.setOnClickListener(view -> {
            String email = emailED.getText().toString();
            String password = passwordED.getText().toString();
            if (dbHelper.credentialChecker(email, password)) {
                Toast.makeText(MainActivity.this,
                        "login is true", Toast.LENGTH_LONG).show();

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        "com.hedaia.omar.resturantapp.myper") != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{"com.hedaia.omar.resturantapp.myper"}, 1);
                } else {
                    Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                    startActivity(intent);
                }



            } else
                Toast.makeText(MainActivity.this,
                        "credential is wrong", Toast.LENGTH_LONG).show();

        });

        newUserSingUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}