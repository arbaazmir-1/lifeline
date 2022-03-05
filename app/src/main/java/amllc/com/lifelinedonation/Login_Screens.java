package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login_Screens extends AppCompatActivity {
    Button loginButton,signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp= getApplication().getSharedPreferences("UserSharePrefs", Context.MODE_PRIVATE);
        Boolean loggedIn=sp.getBoolean("isLoggedIn",true);
        Boolean isAdmin=sp.getBoolean("isAdmin",false);
        if(loggedIn && isAdmin){
            Intent intent= new Intent(Login_Screens.this,AdminHome.class);
            startActivity(intent);
            finish();
        }
        else if(loggedIn){
            Intent intent= new Intent(Login_Screens.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginButton=findViewById(R.id.loginBtn);
        signButton=findViewById(R.id.signUpBtn);

        loginButton.setOnClickListener(v->{
            startNewActivity(Login.class);
        });
        signButton.setOnClickListener(v->{
            startNewActivity(SignUp.class);
        });

    }

    private void startNewActivity(Class classPassed) {
        Intent intent=new Intent(Login_Screens.this,classPassed);
        startActivity(intent);

    }

}