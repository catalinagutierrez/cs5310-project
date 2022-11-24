package com.example.cs5520_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    TextView existingAccount;
    EditText regUsername, regEmail, regPassword, regConfirmPassword;
    Button regButton;
    String uid;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        regUsername = findViewById(R.id.inputUsername1);
        regEmail = findViewById(R.id.inputEmail);
        regPassword = findViewById(R.id.inputPassword1);
        regConfirmPassword = findViewById(R.id.inputConfirmPassword);
        regButton = findViewById(R.id.eventureLoginBtn);

        existingAccount = findViewById(R.id.existingAccountText);
        existingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLoginPage();
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Eventure Users");
                registerUser(view);
            }
        });
    }

    public void registerUser (View view) {

        if(!validateUsername() || !validateEmail() || !validatePassword()) {
            return;
        }

        uid = reference.push().getKey();

        String username = regUsername.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        String confirmPass = regConfirmPassword.getText().toString();
        ArrayList<EventHelperClass> addedEventList = new ArrayList<>();
        UserHelperClass helperclass = new UserHelperClass(username,email,password,confirmPass,addedEventList);

        FirebaseDatabase.getInstance().getReference().child("Eventure Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                for (DataSnapshot data : snapshot.getChildren()) {
                    // if user exists, load their user profile
                    if (data.child("username").getValue().toString().equals(username)){
                        Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        userExists = true;
                        break;
                    } else if (data.child("email").getValue().toString().equals(email)) {
                        Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                        userExists = true;
                        break;
                    }
                }
                // if user does not exist, create a new user
                if(!userExists){
                    reference.child(uid).setValue(helperclass);
                    loadUserProfile();
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
            }
        });

    }

    private Boolean validateUsername() {
        String username = regUsername.getText().toString();
        if(username.isEmpty() || username == null || username.trim().isEmpty() || username.contains(" ")){
            regUsername.setError("Field cannot be empty or contain spaces");
            return false;
        } else if (username.length() > 10){
            regUsername.setError("Username too long");
            return false;
        }else {
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String email = regEmail.getText().toString();
        String emailPattern = "^(.+)@(.+)$";
        if(email.isEmpty() || email == null || email.trim().isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!email.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        }else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = regPassword.getText().toString();
        String confirmPassword = regConfirmPassword.getText().toString();

        if(password.isEmpty() || password == null){
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!password.equals(confirmPassword)) {
            regConfirmPassword.setError("Password does not match");
            return false;
        } else if (password.length() < 5) {
            regPassword.setError("Field needs to be more than 5 characters");
            return false;
        }else{
            regPassword.setError(null);
            regConfirmPassword.setError(null);
            return true;
        }
    }

    public void loadUserProfile(){
        Intent intent = new Intent(RegisterActivity.this, HomePageActivity.class);
        intent.putExtra("username",regUsername.getText().toString());
        intent.putExtra("uid",uid);
        startActivity(intent);
        finish();
    }

    public void loadLoginPage(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        loadLoginPage();
        super.onBackPressed();
    }
}