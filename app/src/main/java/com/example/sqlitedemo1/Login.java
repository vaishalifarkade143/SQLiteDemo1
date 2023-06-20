package com.example.sqlitedemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    EditText log_email,log_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_email = (EditText) findViewById(R.id.log_email);
        log_password = (EditText) findViewById(R.id.log_password);

    }
    public  void loginUser(View view)
    {
        String email1 = log_email.getText().toString();
        String pass1= log_password.getText().toString();
        DbHelper dbHelper = new DbHelper(this);
        boolean loggedin = dbHelper.login(email1,pass1);
        if(loggedin)
        {
            Intent intent = new Intent(Login.this,Profile.class);
           //email id intent k throught pass karvate hai
            intent.putExtra("key_email",email1);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Email id n password didn't match", Toast.LENGTH_SHORT).show();
        }

    }
}