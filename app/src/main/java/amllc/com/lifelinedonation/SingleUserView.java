package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class SingleUserView extends AppCompatActivity {
    String userNid,name,phone,bloodgroup,dateOfBirth,gender;
    DBHelper db;
    EditText userNameEdit,userPhoneEdit,dateOfbirthEdit;
    TextView nidTextView,bloodGroupTextView,genderTextView,isAdminText;
    String isadmin;
    Button deleteButton,upDateBtn;
    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_view);
        db=new DBHelper(this);
        userNameEdit=findViewById(R.id.single_item_name);
        userPhoneEdit=findViewById(R.id.single_item_phone);
        dateOfbirthEdit=findViewById(R.id.single_item_date_of_bith);
        nidTextView=findViewById(R.id.single_item_userNid);
        bloodGroupTextView=findViewById(R.id.single_item_bloodgroup);
        genderTextView=findViewById(R.id.single_item_gender);
        isAdminText=findViewById(R.id.single_item_isAdmin);
        deleteButton=findViewById(R.id.deletebtn);
        backBtn=findViewById(R.id.backBtn);
        upDateBtn=findViewById(R.id.updateBtn);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userNid=null;
            } else {
                //if avaiable the vales are asigned to their local variables
                userNid=extras.getString("userNid");
            }
        }
//        public Boolean insertUser(String userNid,String userName,String password,String phoneNumber,String bloodGroup,String dateOfBirth,String isAdmin,String gender){
        Cursor cursor=db.getUserData(userNid);
        while (cursor.moveToNext()) {

            name = cursor.getString(1);
            phone = cursor.getString(3);
            dateOfBirth=cursor.getString(5);
            bloodgroup=cursor.getString(4);
            gender=cursor.getString(7);
            isadmin=cursor.getString(6);



        }
        userNameEdit.setText(name);
        userPhoneEdit.setText(phone);
        dateOfbirthEdit.setText(dateOfBirth);
        nidTextView.setText("Users NID:"+userNid);
        bloodGroupTextView.setText("User Blood Group:"+bloodgroup);
        genderTextView.setText("User Gender:"+gender);
        isAdminText.setText("is Admin:"+ isadmin);


        deleteButton.setOnClickListener(v->{
            //alert dialog will ask if the user wants o go back or not
            AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
            alertDialogLogOut.setMessage("Are you sure to DELETE User with NID:"+userNid+"?");
            alertDialogLogOut.setCancelable(true);

            //yes will take back to the data entry screen
            alertDialogLogOut.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Integer deleteUserValue=db.deleteUser(userNid);
                            if(deleteUserValue>0){
                                Snackbar.make(dateOfbirthEdit,"User Deleted!",Snackbar.LENGTH_SHORT)
                                        .show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent=new Intent(SingleUserView.this,ViewUsers.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 2000);

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
        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(SingleUserView.this,ViewUsers.class);
            startActivity(intent);
            finish();
        });
        upDateBtn.setOnClickListener(v->{
            String userNameUpdate=userNameEdit.getText().toString();
            String userPhoneUpdate = userPhoneEdit.getText().toString();
            String updateDateOfBirth=dateOfbirthEdit.getText().toString();
            int valueReturned = db.updateUser(userNid,userNameUpdate,userPhoneUpdate,updateDateOfBirth);
            if(valueReturned>0){
                Snackbar.make(dateOfbirthEdit,"User Updated!",Snackbar.LENGTH_SHORT)
                        .show();
            }else{
                Snackbar.make(dateOfbirthEdit,"Error Updating!",Snackbar.LENGTH_SHORT)
                        .show();
            }
        });


    }
}