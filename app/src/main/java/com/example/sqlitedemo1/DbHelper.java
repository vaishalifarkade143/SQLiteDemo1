package com.example.sqlitedemo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "demo_db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE register (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT,password TEXT,gender TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS register");
        onCreate(db);
    }

    public boolean registerUserHelper(String name, String email, String pass, String gender) {
        //insert method
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();//getWritableDatabase() for insert ,update ,delete
        //ContentValues to insert values into table
        ContentValues contentValues = new ContentValues();
        //contentValues.put("colonm name", value in field  ); store  with the help of ContentValues object
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", pass);
        contentValues.put("gender", gender);

        long l= sqLiteDatabase.insert("register", null, contentValues);
        sqLiteDatabase.close();
        // condition to check ki data Database ke under insert ho raha ya nhi
        if (l>0)
        {
            return true;
        }
        else
        {
            return false;
        }


    }
    boolean loggedin;
    public boolean login(String email1,String pass1)
    {
       SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();//use this on time of Retrival
       Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM register WHERE email = '"+email1+"' AND password = '"+pass1+"'",null);

       if(cursor.moveToFirst())
       {
            loggedin = true;
       }
       else {
           loggedin = false;
       }
       return loggedin;
    }

//for get data in profile after login
    public ArrayList<UserModel> getLoggedInDetails(String email1) //we can also store this value in arrayList--> public  void getLoggedInDetails(String email1) //we can also store this value in arrayList  public  ArrayList getLoggedInDetails(String email1)
                                                    //3... (String email1) email1 ke basis par
    {
        ArrayList<UserModel> al = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM register WHERE email='"+email1+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            //4... using email1 sari ki sari values get karva dega
            //1st data store in variable
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String gender = cursor.getString(4);

        //set value or data usermodel
            UserModel userModel = new UserModel();
            //5... pehale usermodel ke under store karvadega
            userModel.setName(name);
            userModel.setEmail(email);
            userModel.setGender(gender);

            //6... fir arrayList ke under store karva dega
            al.add(userModel);

          //OR

//            UserModel userModel = new UserModel();
//            userModel.setName(cursor.getString(1));
//            userModel.setEmail(cursor.getString(2));
//            userModel.setGender( cursor.getString(3));



        }
        return al;
    }
    public  ArrayList  getAllUserDetailsHelper()
    {
       //to store object n show total row
        ArrayList alUser = new ArrayList();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM register",null);
        if(cursor.moveToFirst())
        {
            //to print table
            do
            {
                //this ArrayList will use get single single colomn value like name, email,gender
                ArrayList al = new ArrayList();

                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String gender = cursor.getString(4);

                al.add(name);
                al.add(email);
                al.add(gender);

                //adding colomn value to rows
                alUser.add(al);
            }

            while (cursor.moveToNext());

        }
       return alUser;
    }
    public boolean updateProfileHelper(String email1,String name1, String gender1)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name1);
        values.put("gender",gender1);
        int i =  sqLiteDatabase.update("register",values,"email=?", new String[]{email1});

        if(i>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public  boolean deleteProfileHelper(String emaail)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i =sqLiteDatabase.delete("register","email=?" , new String[] {emaail});

        if(i>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //email already exists
    public boolean checkEmailIdExist(String email)
    {
       SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
       Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM register WHERE email= '"+email+"'",null);

       if(cursor.moveToFirst())
       {
           return true;
       }
       else {
           return false;
       }

    }

}
