package com.test.yatfirstexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainSignInActivity extends AppCompatActivity {
    private EditText emailED, passwordED;
    private Button singIn;
    private TextView newUserSingUpTV;
    private DBHelper dbHelper;
    private TextView textView;
    private FirebaseAuth mAuth;
    public static final String TAG = "MainSignInActivity";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailED = findViewById(R.id.emailEditText);
        passwordED = findViewById(R.id.passwordEditText);
        newUserSingUpTV = findViewById(R.id.newUserSingUpTV);
        singIn = findViewById(R.id.singInButton);
//        dbHelper = new DBHelper(this);

        dbHelper = DBHelper.getInstance(this);

//        mAuth = FirebaseAuth.getInstance();


        singIn.setOnClickListener( view -> {

                if (TextUtils.isEmpty(emailED.getText()) ||
                        TextUtils.isEmpty(passwordED.getText())) {
                    Toast.makeText(MainSignInActivity.this,
                            "please complete all data", Toast.LENGTH_LONG).show();
                } else {

                    String email = emailED.getText().toString();
                    String password = passwordED.getText().toString();
                    String dpass = "User Password";
                    
                    sharedPreferences = getSharedPreferences("encryptedPassword",MODE_PRIVATE);
                    String key = sharedPreferences.getString("masterKey","masterKey");

                    Cursor cursor = dbHelper.getUserInfo(email);
                    if(cursor.moveToFirst()){
                       dpass = cursor.getString(cursor.getColumnIndex("password"));

                    }

                    Log.e(TAG, "key is" + key);

                    int keyLen = key.getBytes().length;
                    Log.e(TAG,"KeyLength :"+ keyLen);

                    String decryptedPassword = EncryptAES.StringDecryption(key.getBytes(), dpass);

                    Log.e(TAG, "Password is" + decryptedPassword);

                    String dbUtils = DatabaseUtils.dumpCursorToString(cursor);

                    Log.e(TAG,"DatabaseUtils :" + dbUtils);

                    if (dbHelper.credentialChecker(email, decryptedPassword) && dpass.equals(decryptedPassword)) {
                        Toast.makeText(MainSignInActivity.this,
                                "login is true", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainSignInActivity.this, RestaurantActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainSignInActivity.this,
                                "credential is wrong", Toast.LENGTH_LONG).show();
                    }
                }
            });


        newUserSingUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSignInActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}