package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class SignUp extends AppCompatActivity {
    EditText userNameEdit,userNIDEdit,userPhoneNumberEdit,userDateOfBirthEdit,userPasswordEdit;
    Button signUpButton;
    DBHelper DB;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Spinner spinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
        Spinner spinner1 = findViewById(R.id.genderSpinner);
        userNameEdit=findViewById(R.id.userNameEditText);
        userPhoneNumberEdit=findViewById(R.id.userPhoneEditText);
        userNIDEdit=findViewById(R.id.userNidEditText);
        userDateOfBirthEdit=findViewById(R.id.editTextDate);
        userPasswordEdit=findViewById(R.id.userPasswordEditText);
        signUpButton=findViewById(R.id.signUpBtn);
        DB = new DBHelper(this);
        sp=getSharedPreferences("UserSharePrefs",MODE_PRIVATE);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodGroupTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.genderTypes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        signUpButton.setOnClickListener(v->{
            String userName,userNid,userPhoneNumber,userDateOfBirth,userPassword,bloodGroup,gender;
            userName=userNameEdit.getText().toString();
            userNid=userNIDEdit.getText().toString();
            userPhoneNumber=userPhoneNumberEdit.getText().toString();
            userDateOfBirth=userDateOfBirthEdit.getText().toString();
            userPassword=userPasswordEdit.getText().toString();
            bloodGroup=spinner.getSelectedItem().toString();
            gender=spinner1.getSelectedItem().toString();
            if(userName.isEmpty()||userNid.isEmpty()||userPhoneNumber.isEmpty()||userDateOfBirth.isEmpty()||userPassword.isEmpty()|bloodGroup.isEmpty()||gender.isEmpty()){
                Snackbar.make(userNameEdit,"Please provide all the details",Snackbar.LENGTH_SHORT)
                        .show();
            }else{
                Boolean checkUser = DB.checkUserNid(userNid);
                if (!checkUser) {

                    Boolean insertUser = DB.insertUser(userNid,userName,userPassword,userPhoneNumber,bloodGroup,userDateOfBirth,"false",gender);
                    if(insertUser){
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("userNid",userNid);
                        editor.putBoolean("isLoggedIn",true);
                        editor.apply();
                        if(!DB.adminLoginCheck(userNid)){
                        Intent intent=new Intent(SignUp.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finishAffinity();
                        startActivity(intent);}
                        else{


                            editor.putString("userNid",userNid);
                            editor.putBoolean("isLoggedIn",true);
                            editor.putBoolean("isAdmin",true);
                            editor.commit();
                            Intent intent=new Intent(SignUp.this,AdminHome.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finishAffinity();
                            startActivity(intent);
                        }


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
    }
}