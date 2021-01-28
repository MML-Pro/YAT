package com.test.yatfirstexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nameED, emailEd, passwordEd, rePasswordEd;
    Button singUpButton;
    DBHelper dbHelper;
    private FirebaseAuth mAuth;
    public static final String TAG = "RegistrationActivity";
    private SharedPreferences sharedPreferences;
    private String masterKey;
    private byte[] key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameED = findViewById(R.id.nameEditText);
        emailEd = findViewById(R.id.emailEditText);
        passwordEd = findViewById(R.id.singupPasswordEditText);
        rePasswordEd = findViewById(R.id.reSingupPasswordEditText);

        singUpButton = findViewById(R.id.singUpButton);
        mAuth = FirebaseAuth.getInstance();


        singUpButton.setOnClickListener(view -> {
            final String name = nameED.getText().toString();
            final String email = emailEd.getText().toString();
            final String password = passwordEd.getText().toString();
            final String repassword = passwordEd.getText().toString();

//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "createUserWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            // ...
//                        }
//                    });


            if (TextUtils.isEmpty(name) ||
                    TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(repassword)) {

                Toast.makeText(RegistrationActivity.this,
                        "please complete all data", Toast.LENGTH_LONG).show();
            } else {
                dbHelper = DBHelper.getInstance(RegistrationActivity.this);

                sharedPreferences = getSharedPreferences("encryptedPassword", MODE_PRIVATE);
                if (sharedPreferences.contains("masterKey")) {
                    masterKey = sharedPreferences.getString("masterKey", null);
                    key = Base64.decode(masterKey, Base64.DEFAULT);
                } else {
                    key = EncryptAES.generateKey("RandomSeed".getBytes());
                   String encKey = Base64.encodeToString(key,Base64.DEFAULT);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("masterKey", encKey);
                    editor.apply();

                }
                String encryptedPassword = EncryptAES.StringEncryption(key, password.getBytes());

                dbHelper.addUser(name, email, encryptedPassword);
//                Toast.makeText(RegistrationActivity.this, "Sucess", Toast.LENGTH_LONG).show();
            }

        });
    }
}