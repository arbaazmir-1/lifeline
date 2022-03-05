package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class UserViewAppointments extends AppCompatActivity {
    TextView nameView,phoneNumberView,centerView,dateView,timeView;
    Button cancelButton;
    DBHelper db;
    String userNID,name,phone,center,date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_appointments);
        nameView=findViewById(R.id.nameText);
        phoneNumberView=findViewById(R.id.phoneText);
        centerView=findViewById(R.id.centerText);
        dateView=findViewById(R.id.dateText);
        timeView=findViewById(R.id.timeText);
        cancelButton=findViewById(R.id.cancel_button);
        db = new DBHelper(this);
        SharedPreferences sp= getApplication().getSharedPreferences("UserSharePrefs", Context.MODE_PRIVATE);
        userNID=sp.getString("userNid","");
        if(db.checkAppointment(userNID)) {
            Cursor cursor = db.getAppointmentData(userNID);
            StringBuilder stringBuilder = new StringBuilder();
            // db.execSQL("create Table appointments(usernid TEXT primary key,username TEXT,phonenumber Text,date TEXT,center TEXT,time TEXT,gender TEXT,bloodgroup TEXT)");
            while (cursor.moveToNext()) {
                stringBuilder.append("Name :" + cursor.getString(1) + "\nUser Phone Number:" + cursor.getString(3));
                name = cursor.getString(1);
                phone = cursor.getString(2);
                date = cursor.getString(3);
                center = cursor.getString(4);
                time = cursor.getString(5);

            }
            nameView.setText("Your Name:" + name);
            phoneNumberView.setText("Your Phone Number:" + phone);
            centerView.setText("Your Center:" + center);
            dateView.setText("Date Of Appointment:" + date);
            timeView.setText("Time Of Appointment:" + time);
        }
        else{
            nameView.setText("You Don't Have an appointment!!");
            cancelButton.setVisibility(View.INVISIBLE);
        }
        cancelButton.setOnClickListener(v->{


            //alert dialog will ask if the user wants o go back or not
            AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
            alertDialogLogOut.setMessage("Are you sure to Cancel Appointment?");
            alertDialogLogOut.setCancelable(true);

            //yes will take back to the data entry screen
            alertDialogLogOut.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Integer deleteAppintment=db.deletAppoinment(userNID);
                            if(deleteAppintment>0){
                                Snackbar.make(cancelButton,"Appointment Cancelled",Snackbar.LENGTH_SHORT)
                                        .show();
                                nameView.setText("No Appoinment Found!");
                                phoneNumberView.setVisibility(View.INVISIBLE);
                                centerView.setVisibility(View.INVISIBLE);
                                dateView.setVisibility(View.INVISIBLE);
                                timeView.setVisibility(View.INVISIBLE);
                                cancelButton.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
            //no will dismiss the dialog
            alertDialogLogOut.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alertDialogLogOut.create();
            alert11.show();



        });

    }
}