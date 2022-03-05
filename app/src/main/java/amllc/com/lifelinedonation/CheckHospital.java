package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CheckHospital extends AppCompatActivity {
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hospital);
        backButton=findViewById(R.id.backBtn);
        //onclick listener for navigation backbutton
        backButton.setOnClickListener(this::backButtonPressed);
    }
    //custom startnewactivity to open new activity
    private void startNewActivity(Class newClass) {
        Intent intent=new Intent(CheckHospital.this,newClass);
        finishAffinity();
        startActivity(intent);

    }
    //backbutton function for top navigation
    private void backButtonPressed(View v) {
        //dialog to confirm is user want to exit or not
        AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
        alertDialogLogOut.setMessage("Are you sure?");
        alertDialogLogOut.setCancelable(true);


        alertDialogLogOut.setPositiveButton(
                "Yes",
                (dialog, id) -> startNewActivity(MainActivity.class));

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