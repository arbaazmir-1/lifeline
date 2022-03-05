package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class DonationTimeAndCenter extends AppCompatActivity {
    //this array will be used in spinner for user to choose the location
    String[] center = {  "Nilai",
            "Cyberjaya", "Putrajaya",
            "Kuala Lumpur", "Daman Sara" };
    String userNID;

     RadioGroup dateGroup,timeGroup;
     RadioButton selectedDateRadioBtn,selectedTimeRadioBTn;
     Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_time_and_center);
        nextButton=findViewById(R.id.confirm_button);
        dateGroup=findViewById(R.id.dateRadioGroup);
        timeGroup=findViewById(R.id.timeRadiogroup);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userNID= null;

            } else {
                userNID=extras.getString("userNid");
            }
        }
        Spinner spino = findViewById(R.id.donationcenter);


        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                center);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spino.setAdapter(ad);


        nextButton.setOnClickListener(v->{
            String centers =spino.getSelectedItem().toString();
            int selectedDateIndex=dateGroup.getCheckedRadioButtonId();
            int selectedTimeIndex=timeGroup.getCheckedRadioButtonId();
            //checking if all the datas are provided
            if (!centers.isEmpty()&&selectedDateIndex!=-1&&selectedTimeIndex!=-1){
                selectedDateRadioBtn=findViewById(selectedDateIndex);
                selectedTimeRadioBTn=findViewById(selectedTimeIndex);
                String date=selectedDateRadioBtn.getText().toString();
                 String time=selectedTimeRadioBTn.getText().toString();
                Intent i=new Intent(DonationTimeAndCenter.this,ConfirmationApplication.class);
                //passing information from this class to the next one for use.
                i.putExtra("userNid",userNID);
                i.putExtra("centerName",centers);
                i.putExtra("date",date);
                i.putExtra("time",time);
                Log.d("centeruser", "onCreate: "+userNID);

                startActivity(i);
            }else{
                //used snackbar to notify user about the missing fields
                Snackbar.make(dateGroup,"Please Select all the fields",Snackbar.LENGTH_SHORT)
                        .show();
            }
        });




    }
    //custom system backbutton function to prevent the user to go back mistakenly
    @Override
    public void onBackPressed() {
        //creating dialog to confirm
        AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
        alertDialogLogOut.setMessage("Are you sure?");
        alertDialogLogOut.setCancelable(true);


        alertDialogLogOut.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(DonationTimeAndCenter.this,DonationEligibility.class);

                        intent.putExtra("userNid",userNID);

                        startActivity(intent);
                    }
                });

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