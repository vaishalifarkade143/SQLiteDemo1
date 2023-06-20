package com.example.sqlitedemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProfile extends AppCompatActivity
{
    TextView up_email;
    EditText up_name,up_gender;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        up_email = (TextView) findViewById(R.id.up_email);
        up_name = (EditText) findViewById(R.id.up_name);
        up_gender  = (EditText) findViewById(R.id.up_gender);
            //getting data from profile which is stored in usermodel
        userModel =(UserModel) getIntent().getSerializableExtra("key_usermodel");

        //setting data on field
        up_email.setText(userModel.getEmail());
        up_name.setText(userModel.getName());
        up_gender.setText(userModel.getGender());


    }

    public void updateMyProfile(View view)
    {
       //get the value which are on UpdateProfile
        String name1 = up_name.getText().toString();
        String gender =  up_gender.getText().toString();
        DbHelper dbHelper = new DbHelper(this);
        boolean b=dbHelper.updateProfileHelper(userModel.getEmail(),name1,gender);

       if(b == true)
       {
           Toast.makeText(this, "Values Updated successfully....", Toast.LENGTH_SHORT).show();
       }
       else
       {
           Toast.makeText(this, "Error Occured....", Toast.LENGTH_SHORT).show();

       }
    }
}