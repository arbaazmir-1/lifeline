package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class AddUserAdmin extends AppCompatActivity {
    EditText userNameEdit,userNIDEdit,userPhoneNumberEdit,userDateOfBirthEdit,userPasswordEdit;
    Button addUserBtn;
    ImageButton backBtn;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_admin);
        Spinner bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
        Spinner genderSpinner = findViewById(R.id.genderSpinner);
        Spinner isAdminSpinner = findViewById(R.id.isAdminSpinner);
        userNameEdit=findViewById(R.id.userNameEditText);
        userPhoneNumberEdit=findViewById(R.id.userPhoneEditText);
        userNIDEdit=findViewById(R.id.userNidEditText);
        userDateOfBirthEdit=findViewById(R.id.editTextDate);
        userPasswordEdit=findViewById(R.id.userPasswordEditText);
        addUserBtn=findViewById(R.id.addUserBtn);
        backBtn=findViewById(R.id.backBtn);
        DB = new DBHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodGroupTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupSpinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.genderTypes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.isAdminString, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isAdminSpinner.setAdapter(adapter2);

        addUserBtn.setOnClickListener(v->{
            String userName,userNid,userPhoneNumber,userDateOfBirth,userPassword,bloodGroup,gender;
            userName=userNameEdit.getText().toString();
            userNid=userNIDEdit.getText().toString();
            userPhoneNumber=userPhoneNumberEdit.getText().toString();
            userDateOfBirth=userDateOfBirthEdit.getText().toString();
            userPassword=userPasswordEdit.getText().toString();
            bloodGroup=bloodGroupSpinner.getSelectedItem().toString();
            gender=genderSpinner.getSelectedItem().toString();
            if(userName.isEmpty()||userNid.isEmpty()||userPhoneNumber.isEmpty()||userDateOfBirth.isEmpty()||userPassword.isEmpty()|bloodGroup.isEmpty()||gender.isEmpty()){
                Snackbar.make(userNameEdit,"Please provide all the details",Snackbar.LENGTH_SHORT)
                        .show();
            }else{
                Boolean checkUser = DB.checkUserNid(userNid);
                if (!checkUser) {

                    Boolean insertUser = DB.insertUser(userNid,userName,userPassword,userPhoneNumber,bloodGroup,userDateOfBirth,isAdminSpinner.getSelectedItem().toString(),gender);
                    if(insertUser){

                       Snackbar.make(userNameEdit,"User Added",Snackbar.LENGTH_SHORT)
                               .show();
                       userNameEdit.setText("");
                       userNIDEdit.setText("");
                       userPhoneNumberEdit.setText("");
                       userPasswordEdit.setText("");
                       userDateOfBirthEdit.setText("");


                    }else{
                        Snackbar.make(userNameEdit,"Error Occurred, Please try later",Snackbar.LENGTH_SHORT)
                                .show();
                    }

                }
                else{
                    Snackbar.make(userNameEdit,"User Already Exists, Please Sign In",Snackbar.LENGTH_SHORT)
                            .show();
                }
            }

        });
        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(AddUserAdmin.this,AdminHome.class);
            finishAffinity();
            startActivity(intent);
            finish();
        });
    }
}