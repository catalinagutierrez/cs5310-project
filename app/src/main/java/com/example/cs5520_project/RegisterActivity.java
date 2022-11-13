package com.example.cs5520_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextView existingAccount;
    EditText regUsername, regEmail, regPassword, regConfirmPassword;
    Button regButton;
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
        regButton = findViewById(R.id.registerBtn);

        existingAccount = findViewById(R.id.existingAccountText);
        existingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                reference.setValue("hello");
                Toast.makeText(RegisterActivity.this, reference.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerUser (View view) {

        if(!validateUsername() || !validateEmail() || !validatePassword()) {
            return;
        }

        String username = regUsername.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        String confirmPass = regConfirmPassword.getText().toString();

        //UserHelperClass helperclass = new UserHelperClass(username,email,password,confirmPass);
        //reference.child(username).setValue(helperclass);
    }

    private Boolean validateUsername() {
        String username = regUsername.getText().toString();
        if(username.isEmpty() || username == null || username.trim().isEmpty() || username.contains(" ")){
            regUsername.setError("Field cannot be empty or contain spaces");
//            Toast.makeText(RegisterActivity.this, "Please enter a valid username, no spaces!", Toast.LENGTH_SHORT).show();
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
        String emailPattern = "[a-zA-Z0-9._-]+@[a=z]+\\.+[a-z]+";
        if(email.isEmpty() || email == null || email.trim().isEmpty()){
            regEmail.setError("Field cannot be empty");
//            Toast.makeText(RegisterActivity.this, "Please enter a valid username, no spaces!", Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(RegisterActivity.this, "Please enter a valid username, no spaces!", Toast.LENGTH_SHORT).show();
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
}