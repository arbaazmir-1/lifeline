package amllc.com.lifelinedonation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public  static final String DBName="Lifelife.db";
    public DBHelper(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(usernid TEXT primary key,username TEXT,password TEXT,phonenumber TEXT,bloodgroup TEXT,dateofbirth TEXT,isadmin TEXT,gender TEXT)");
        db.execSQL("create Table appointments(usernid TEXT primary key,username TEXT,phonenumber Text,date TEXT,center TEXT,time TEXT,gender TEXT,bloodgroup TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists appointments");

    }
    public Boolean insertAppointments(String useNid,String date,String Center,String Time){
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("usernid",useNid);
        contentValues.put("date",date);
        contentValues.put("center",Center);
        contentValues.put("time",Time);
        long results= mydb.insert("appointments",null,contentValues);
        if(results==-1) return  false;
        else
            return true;
    }
    public Boolean insertUser(String userNid,String userName,String password,String phoneNumber,String bloodGroup,String dateOfBirth,String isAdmin,String gender){
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("usernid",userNid);
        contentValues.put("username",userName);
        contentValues.put("password",password);
        contentValues.put("phonenumber",phoneNumber);
        contentValues.put("bloodgroup",bloodGroup);
        contentValues.put("dateofbirth",dateOfBirth);
        contentValues.put("isadmin",isAdmin);
        contentValues.put("gender",gender);
        long results =mydb.insert("users",null,contentValues);
        if(results==-1) return false;
        else
            return true;
    }
    public ArrayList<User> getAllUserdata(){
        ArrayList<User> userArrayList=new ArrayList<>();
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("SELECT * FROM users",null);

        while (cursor.moveToNext()){
            String userNid=cursor.getString(0);
            String userName = cursor.getString(1);
            String bloodGroup=cursor.getString(4);
            User user=new User(userNid,userName,bloodGroup);

            userArrayList.add(user);

        }
        return userArrayList;
    }
//     db.execSQL("create Table appointments(usernid TEXT primary key,username TEXT,phonenumber Text,date TEXT,center TEXT,time TEXT,gender TEXT,bloodgroup TEXT)");
    public ArrayList<AppointmentModel> getAllApointments(){
        ArrayList<AppointmentModel> appointmentModels=new ArrayList<>();
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("SELECT * FROM appointments",null);

        while (cursor.moveToNext()){
            String userNid=cursor.getString(0);
            String centerName = cursor.getString(4);
            String dateOfAppointment=cursor.getString(3);
            AppointmentModel model=new AppointmentModel(userNid,centerName,dateOfAppointment);

            appointmentModels.add(model);

        }
        return appointmentModels;
    }

//    db.execSQL("create Table appointments(usernid TEXT primary key,username TEXT,phonenumber Text,date TEXT,center TEXT,time TEXT)");
    public Boolean addAppointment(String userNid,String userName,String phonenumber,String date,String center,String time,String gender,String bloodgroup){
        SQLiteDatabase mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("usernid",userNid);
        contentValues.put("username",userName);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("date",date);
        contentValues.put("center",center);
        contentValues.put("time",time);
        contentValues.put("gender",gender);
        contentValues.put("bloodgroup",bloodgroup);
        long results = mydb.insert("appointments",null,contentValues);
        if(results==-1) return  false;
        else
            return true;
    }
    public Boolean checkUserNid(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("Select * from users where usernid =?",new String[]{userNid});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkAppointment(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("Select * from appointments where usernid =?",new String[]{userNid});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkUserCreditentials(String userNid,String password){
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("Select * from users where usernid = ? and password = ?",new String[]{userNid,password});
        if(cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean adminLoginCheck(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("Select * from users where usernid = ? and isadmin= ?",new String[]{userNid,"true"});
        if(cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public Cursor getUserData(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("Select * from users where usernid = ? ",new String[]{userNid});
        return cursor;
    }
    public Cursor getAppointmentData(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("Select * from appointments where usernid = ? ",new String[]{userNid});
        if(cursor.getCount()>0){

            return cursor;

        }
        else {
            Log.d("userdatanot", "getUserData: no data found");
            return cursor;
        }
    }
    public Integer deletAppoinment(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        return  mydb.delete("appointments","usernid = ?",new String[]{userNid});

    }
    public Integer deleteUser(String userNid){
        SQLiteDatabase mydb=this.getWritableDatabase();
        return  mydb.delete("users","usernid = ?",new String[]{userNid});

    }
    // below is the method for updating our courses
    public Integer updateUser(String userNid, String userName, String userPhone,
                             String dateOfBirth) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("usernid", userNid);
        values.put("username",userName);
        values.put("phonenumber",userPhone);
        values.put("dateofbirth",dateOfBirth);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int userUpdatedValue=db.update("users", values, "usernid=?", new String[]{userNid});
        db.close();

        return userUpdatedValue;

    }

}
