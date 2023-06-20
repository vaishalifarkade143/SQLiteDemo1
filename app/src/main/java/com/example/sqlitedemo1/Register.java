package com.example.sqlitedemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity
{
    DbHelper dbHelper;
    EditText reg_name,reg_email,reg_pass,reg_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_pass = (EditText) findViewById(R.id.reg_pass);
        reg_gender = (EditText) findViewById(R.id.reg_gender);

        dbHelper = new DbHelper(getApplicationContext());

  }
  public void  registerUser(View view)
  {
        String name = reg_name.getText().toString();
        String email = reg_email.getText().toString();
        String pass = reg_pass.getText().toString();
        String gender = reg_gender.getText().toString();

        //email alerdy exist
        boolean b = dbHelper.checkEmailIdExist(email);//true
        if(b)
        {

            //agar email id alrady exist karti hai to user add nhi hoga
            Toast.makeText(this, "email aleady exist..", Toast.LENGTH_SHORT).show();
        }

        //otherwise user add  hoga user exist nhi krta to
        else
        {
            //call method to vaccunt the field
            boolean b1= dbHelper.registerUserHelper(name,email,pass,gender);
            if(b1 == true)
            {
                Toast.makeText(this, "Register Successfully......", Toast.LENGTH_SHORT).show();
                //jub sari ki sari information register ho jaye to fields empty ho jaye
                reg_email.setText("");
                reg_name.setText("");
                reg_pass.setText("");
                reg_gender.setText("");
            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        //
  }
}