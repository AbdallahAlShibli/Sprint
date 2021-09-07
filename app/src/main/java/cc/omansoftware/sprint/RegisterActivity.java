/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cc.omansoftware.sprint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private TextView sback;
    private Button loginButton;
    private EditText email, password;
    FirebaseAuth FA;
    FirebaseDatabase FD;
    DatabaseReference DR;
    FirebaseUser FU;
    DBRequirements DBRequirements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FA = FirebaseAuth.getInstance();
        FD = FirebaseDatabase.getInstance();

        sback = findViewById(R.id.idLoginPage);
        loginButton = findViewById(R.id.idRegistration);
        email = findViewById(R.id.idEmailRegistration);
        password = findViewById(R.id.idPasswordRegistration);


        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterActivity.this, OrdersActivity.class);
                startActivity(it);

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String FEmail = email.getText().toString();
                if (checkEmail(FEmail) && checkPassword()){

                    String FPassword = password.getText().toString();
                    FA.createUserWithEmailAndPassword(FEmail, FPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Register is successfully!", Toast.LENGTH_SHORT).show();
                                DBRequirements = new DBRequirements();
                                DBRequirements.setEmail(FEmail);
                                FU = FA.getCurrentUser();
                                DR = FD.getReference("UsersRequirements/" + FU.getUid());
                                DR.setValue(DBRequirements);
                                startActivity(new Intent(getApplication(),OrdersActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "Error!, Check the Password or Email!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });



    }

    private boolean checkPassword() {
        String FPassword = password.getText().toString();
        if (FPassword.length()>=6 && FPassword.length()<=16){
            return true;
        }else return false;
    }

    private boolean checkEmail(String character) {
        return !TextUtils.isEmpty(character) && Patterns.EMAIL_ADDRESS.matcher(character).matches();
    }
}
