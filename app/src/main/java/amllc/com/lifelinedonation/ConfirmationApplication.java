package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ConfirmationApplication extends AppCompatActivity {
    //declaring the all the variables and views
     TextView nameView,phoneView,centerView,dateView,timeView,consentView,icNumberTxtView;
     String name,phone,gender,center,bloodGroup,date,time,userNID;
     Button homeBtn,openMapsButton;
    //string url to open when user clicks on open maps
     String url ="http://maps.google.com/?q=";
     DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_application);
        //binding views with id
        nameView=findViewById(R.id.nameText);
        phoneView=findViewById(R.id.phoneText);
        centerView=findViewById(R.id.centerText);
        dateView=findViewById(R.id.dateText);
        timeView=findViewById(R.id.timeText);
        consentView=findViewById(R.id.consentText);
        icNumberTxtView=findViewById(R.id.icNumberView);
        homeBtn=findViewById(R.id.gohome);
        openMapsButton=findViewById(R.id.openMapsBtn);
        db=new DBHelper(this);
        SharedPreferences sp= getApplication().getSharedPreferences("UserSharePrefs", Context.MODE_PRIVATE);
        userNID=sp.getString("userNid","");
        Log.d("sp", "onCreate: "+userNID);
        //checking if saved instance state is avaiable, the extras from previous activities
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                center=null;
                date=null;
                time=null;
            } else {
                //if avaiable the vales are asigned to their local variables

                center=extras.getString("centerName");
                date=extras.getString("date");
                time=extras.getString("time");
            }
        }

        Cursor cursor=db.getUserData(userNID);
        StringBuilder stringBuilder=new StringBuilder();
        while(cursor.moveToNext()){
            stringBuilder.append("Name :"+cursor.getString(1)+"\nUser Phone Number:"+cursor.getString(3));
            name=cursor.getString(1);
            phone=cursor.getString(3);
            bloodGroup=cursor.getString(4);
            gender=cursor.getString(7);

        }
        if(!db.checkAppointment(userNID)) {
            Boolean results = db.addAppointment(userNID, name, phone, date, center, time, gender, bloodGroup);
            if (results) {
                icNumberTxtView.setText("IC Number:"+userNID);
                centerView.setText("Center: "+center);
                dateView.setText("Date: "+date);
                timeView.setText("Time: "+time);
                Snackbar.make(dateView, "Appointment added!", Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                Snackbar.make(dateView, "Error Cannot add Appointment!", Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        else{
            icNumberTxtView.setText("Appointment Already Exists");
            Snackbar.make(dateView, "Error Appointment Already Exists", Snackbar.LENGTH_SHORT)
                    .show();
        }
        homeBtn.setOnClickListener(v->{
            Intent intent=new Intent(ConfirmationApplication.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finishAffinity();
            startActivity(intent);
        });


    }

}