package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SingleAppointmentView extends AppCompatActivity {
    TextView nameTextview,nidTextView,genderTextView,bloodGroupTextView,centerTextView,dateTextView,timeTextView;
    String userNid;
    DBHelper db;
    String name,phone,date,center,time,bloodgroup,gender;
    Button deletButton;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_appointment_view);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userNid=null;
            } else {
                //if avaiable the vales are asigned to their local variables
                userNid=extras.getString("userNid");
            }
        }
        backBtn=findViewById(R.id.backBtn);

        deletButton=findViewById(R.id.deletebtn);
        db=new DBHelper(this);
        nameTextview=findViewById(R.id.single_item_userName);
        nidTextView=findViewById(R.id.single_item_userNid);
        genderTextView=findViewById(R.id.single_item_gender);
        bloodGroupTextView=findViewById(R.id.single_item_bloodgroup);
        centerTextView=findViewById(R.id.single_item_center);
        dateTextView=findViewById(R.id.single_item_date);
        timeTextView=findViewById(R.id.single_item_time);

        if(db.checkAppointment(userNid)) {
            Cursor cursor = db.getAppointmentData(userNid);
            StringBuilder stringBuilder = new StringBuilder();
            // db.execSQL("create Table appointments(usernid TEXT primary key,username TEXT,phonenumber Text,date TEXT,center TEXT,time TEXT,gender TEXT,bloodgroup TEXT)");
            while (cursor.moveToNext()) {
                stringBuilder.append("Name :" + cursor.getString(1) + "\nUser Phone Number:" + cursor.getString(3));
                name = cursor.getString(1);
                phone = cursor.getString(2);
                date = cursor.getString(3);
                center = cursor.getString(4);
                time = cursor.getString(5);
                gender=cursor.getString(6);
                bloodgroup=cursor.getString(7);

            }
            nameTextview.setText("Name:" + name);
            nidTextView.setText("Phone Number:" + userNid);
            centerTextView.setText("Center:" + center);
            dateTextView.setText("Date Of Appointment:" + date);
            timeTextView.setText("Time Of Appointment:" + time);
            genderTextView.setText("Gender: "+gender);
            bloodGroupTextView.setText("Blood Group:"+bloodgroup);
        }
        else{
            nameTextview.setText("You Don't Have an appointment!!");

        }
        deletButton.setOnClickListener(this::onClickDelete);
        backBtn.setOnClickListener(v->{
            Intent intent=new Intent(SingleAppointmentView.this,ViewAppointments.class);
            startActivity(intent);
            finish();

        });
    }

    private void onClickDelete(View v) {

        //alert dialog will ask if the user wants o go back or not
        AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
        alertDialogLogOut.setMessage("Are you sure to Delete Appointment?");
        alertDialogLogOut.setCancelable(true);

        //yes will take back to the data entry screen
        alertDialogLogOut.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Integer deleteAppintment = db.deletAppoinment(userNid);
                        if (deleteAppintment > 0) {
                            Snackbar.make(deletButton, "Appointment Deleted", Snackbar.LENGTH_SHORT)
                                    .show();
                            nameTextview.setText("No Appoinment Found!");
                            nidTextView.setVisibility(View.INVISIBLE);
                            centerTextView.setVisibility(View.INVISIBLE);
                            bloodGroupTextView.setVisibility(View.INVISIBLE);
                            timeTextView.setVisibility(View.INVISIBLE);
                            dateTextView.setVisibility(View.INVISIBLE);
                            genderTextView.setVisibility(View.INVISIBLE);
                            deletButton.setVisibility(View.VISIBLE);
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
    }
}