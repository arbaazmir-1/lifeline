package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {
    Button logoutButton,viewUser,addUsersButton,viewAppointmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        logoutButton=findViewById(R.id.logoutBtn);
        viewUser=findViewById(R.id.viewUsers);
        addUsersButton=findViewById(R.id.addUserBtn);
        viewAppointmentButton=findViewById(R.id.viewAppointmentsButton);


        viewUser.setOnClickListener(v->{
            Intent intent=new Intent(AdminHome.this, ViewUsers.class);
            startActivity(intent);
        });
        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserSharePrefs", Context.MODE_PRIVATE);

        logoutButton.setOnClickListener(v->{
            //alert dialog will ask if the user wants o go back or not
            AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
            alertDialogLogOut.setMessage("Are you sure to Logout");
            alertDialogLogOut.setCancelable(true);

            //yes will take back to the data entry screen
            alertDialogLogOut.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SharedPreferences.Editor editor=sp.edit();
                            editor.putBoolean("isLoggedIn",false);
                            editor.putBoolean("isAdmin",false);
                            editor.commit();
                            Intent intent= new Intent(AdminHome.this,Login_Screens.class);
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
        addUsersButton.setOnClickListener(v->{
            Intent intent=new Intent(AdminHome.this,AddUserAdmin.class);
            startActivity(intent);
        });
        viewAppointmentButton.setOnClickListener(v->{
            Intent intent=new Intent(AdminHome.this,ViewAppointments.class);
            startActivity(intent);
        });
    }
}