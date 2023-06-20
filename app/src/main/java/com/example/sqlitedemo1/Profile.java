package com.example.sqlitedemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Profile extends AppCompatActivity
{

    UserModel userModel ;

    TextView profile_name,profile_email,profile_gender;
    String email1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_name =(TextView) findViewById(R.id.profile_name);
        profile_email =(TextView) findViewById(R.id.profile_email);
        profile_gender =(TextView) findViewById(R.id.profile_gender);
        /* 1....Login se intent ke througth email id lekar ayenge */
        email1 = getIntent().getStringExtra("key_email");
        getUserDetails();
    }
//we have created this method
    public  void getUserDetails()
    {
        DbHelper dbHelper = new DbHelper(this);
        //6... yaha pr values get larva di or use arrayList  ke object me store karva diye//
        ArrayList<UserModel> al =dbHelper.getLoggedInDetails(email1);//2...and vo email id hum log pass karvayenge yaha pe-->(email1)
        userModel = al.get(0);

         //7... print now
        profile_name.setText(userModel.getName());
        profile_email.setText(userModel.getEmail());
        profile_gender.setText(userModel.getGender());
    }
    public void logoutUser(View view)
    {
        //8...
            startActivity(new Intent(Profile.this,Login.class));
    }

    public void getAllUserDetails (View view)
    {
        DbHelper dbHelper = new DbHelper(this);
        ArrayList al = dbHelper.getAllUserDetailsHelper();
        Toast.makeText(this, ""+al, Toast.LENGTH_LONG).show();

    }

    public void  updateProfile(View view)
    {
        //startActivity(new Intent(Profile.this,UpdateProfile.class));
                    //OR

        Intent intent = new Intent(Profile.this,UpdateProfile.class);
        intent.putExtra("key_usermodel" , userModel);
        startActivity(intent);

    }

    public void deleteProfile(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Profile");
        builder.setTitle("Are you sure you want to delete the data..");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbHelper dbHelper = new DbHelper(Profile.this);
               boolean b= dbHelper.deleteProfileHelper(userModel.getEmail());
           
               if(b== true)
               {
                   Toast.makeText(Profile.this, "Profile Deleted Succesfully..", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(Profile.this, "Failed..", Toast.LENGTH_SHORT).show();
               }
            }
            
        });
        builder.show();
    }

}