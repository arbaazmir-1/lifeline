package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button goAppointmentBtn,checkAppointmentButton,viewCentersBtn,viewAdvicesBtn,logoutButton;
    String userNID;
    TextView welcomeMessageView;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goAppointmentBtn=findViewById(R.id.donateBtn);
        checkAppointmentButton=findViewById(R.id.viewAppointments);
        viewCentersBtn=findViewById(R.id.viewCenterBtn);
        welcomeMessageView=findViewById(R.id.welcomeUser);
        logoutButton=findViewById(R.id.logoutBtn);
        db=new DBHelper(this);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserSharePrefs", Context.MODE_PRIVATE);
        userNID=sp.getString("userNid","");
        Cursor cursor=db.getUserData(userNID);
        StringBuilder stringBuilder=new StringBuilder();
        while(cursor.moveToNext()){
                    stringBuilder.append("Welcome "+cursor.getString(1));
        }
        welcomeMessageView.setText(stringBuilder);

        //        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                userNID= null;
//
//            } else {
//                userNID=extras.getString("userNid");
//                Cursor cursor=db.getUserData(userNID);
//                StringBuilder stringBuilder=new StringBuilder();
//                while(cursor.moveToNext()){
//                    stringBuilder.append("Welcome "+cursor.getString(1));
//                }
//                welcomeMessageView.setText(stringBuilder);
//
//
//            }
//        }
        goAppointmentBtn.setOnClickListener(v->{
            if(!db.checkAppointment(userNID)){
            Intent intent = new Intent(MainActivity.this,DonationEligibility.class);
            intent.putExtra("userNid",userNID);
            Log.d("mainacivityuserid", "onCreate: "+userNID);
            startActivity(intent);}
            else{
                Snackbar.make(checkAppointmentButton,"You have One Appointment Already!",Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        logoutButton.setOnClickListener(v->{
            //alert dialog will ask if the user wants o go back or not
            AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
            alertDialogLogOut.setMessage("Are you sure to logout?");
            alertDialogLogOut.setCancelable(true);

            //yes will take back to the data entry screen
            alertDialogLogOut.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SharedPreferences.Editor editor=sp.edit();
                            editor.putBoolean("isLoggedIn",false);
                            editor.commit();
                            Intent intent= new Intent(MainActivity.this,Login_Screens.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
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

        checkAppointmentButton.setOnClickListener(v->{
            startNewActivity(UserViewAppointments.class);
        });
        viewCentersBtn.setOnClickListener(v->{
            startNewActivity(CheckHospital.class);
        });


    }
    //custom startnewactivity that accepts a class as argument and pass it ot intent to open a new activity
    private void startNewActivity(Class newClass) {
        Intent intent=new Intent(MainActivity.this,newClass);
        startActivity(intent);

    }

}