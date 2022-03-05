package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {
    EditText userNidEdit,userPasswordEdit;
    Button loginButton,signUpButton;
    DBHelper DB;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userNidEdit=findViewById(R.id.userNidEditText);
        userPasswordEdit=findViewById(R.id.userPasswordEditText);
        loginButton=findViewById(R.id.loginBtn);
        signUpButton=findViewById(R.id.signUpBtn);
        DB=new DBHelper(getApplicationContext());
        sp=getSharedPreferences("UserSharePrefs",MODE_PRIVATE);

        loginButton.setOnClickListener(v->{
            String userNid,userPassword;
            userNid=userNidEdit.getText().toString();
            userPassword=userPasswordEdit.getText().toString();
            if(userNid.isEmpty()||userPassword.isEmpty()){
                Snackbar.make(userNidEdit,"Please provide all the details",Snackbar.LENGTH_SHORT)
                        .show();
            }
            else{
                Boolean checkUser = DB.checkUserNid(userNid);
                if(checkUser) {
                    Boolean isAdmin = DB.adminLoginCheck(userNid);
                    if(!isAdmin){
                        Log.d("isAdmin", "Not an admin");
                    Boolean checkUserPass = DB.checkUserCreditentials(userNid, userPassword);
                    if (checkUserPass) {
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("userNid",userNid);
                        editor.putBoolean("isLoggedIn",true);
                        editor.commit();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finishAffinity();

                        startActivity(intent);


                    } else {
                        Snackbar.make(userNidEdit, "Invalid Credentials", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
                    else{

                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("userNid",userNid);
                        editor.putBoolean("isLoggedIn",true);
                        editor.putBoolean("isAdmin",true);
                        editor.commit();
                        Intent intent = new Intent(Login.this, AdminHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finishAffinity();
                        startActivity(intent);

                    }

                }
                else{
                    Snackbar.make(userNidEdit, "User Doesn't Exists,Please Sign Up", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
        signUpButton.setOnClickListener(v->{
            Intent intent=new Intent(Login.this,SignUp.class);
            startActivity(intent);

        });

    }
}