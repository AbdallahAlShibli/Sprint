/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cc.omansoftware.sprint;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView RegistrationPage;
    private EditText email, password;
    FirebaseAuth FA;
    FirebaseUser FU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


            RegistrationPage = findViewById(R.id.idRegistrationPage);
            loginButton = findViewById(R.id.idLoginLogin);
            email = findViewById(R.id.idEmailLogin);
            password = findViewById(R.id.idPasswordLogin);


            FA = FirebaseAuth.getInstance();

            RegistrationPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(it);
                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String FEmail = email.getText().toString();
                    if (checkEmail(FEmail) && checkPassword()) {
                        String FPassword = password.getText().toString();
                        FA.signInWithEmailAndPassword(FEmail, FPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, OrdersActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error! Login is not successful", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Error! Check password or email!", Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }

        private boolean checkPassword () {
            String FPassword = password.getText().toString();
            if (FPassword.length() >= 6 && FPassword.length() <= 16) {
                return true;
            } else return false;
        }

        private boolean checkEmail (String character){
            return !TextUtils.isEmpty(character) && Patterns.EMAIL_ADDRESS.matcher(character).matches();
        }

    @Override
    protected void onPostResume() {

        FU = FA.getCurrentUser();
        if (FU != null) {
            startActivity(new Intent(getApplication(), OrdersActivity.class));
            finish();

        }
        super.onPostResume();
    }
}