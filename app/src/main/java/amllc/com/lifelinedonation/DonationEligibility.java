package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

public class DonationEligibility extends AppCompatActivity {
    RadioGroup questionOne,questionTwo,questionThree,questionFour,questionFive;
    Button nextButton;
    String userNID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_eligibility);
        nextButton=findViewById(R.id.nextBtn);
        questionOne=findViewById(R.id.questionOneRadio);
        questionTwo=findViewById(R.id.questionTwoRadio);
        questionThree=findViewById(R.id.questionThreeRadio);
        questionFour=findViewById(R.id.questionFourRadio);
        questionFive=findViewById(R.id.questionFiveRadio);



        nextButton.setOnClickListener(v->{

            RadioButton questionOneRadio,questionTwoRadio,questionThreeRadio,questionFourRadio,questionFiveRadio;
            int indexOne,indexTwo,indexThree,indexFour,indexFive;
            indexOne=questionOne.getCheckedRadioButtonId();
            indexTwo=questionTwo.getCheckedRadioButtonId();
            indexThree=questionThree.getCheckedRadioButtonId();
            indexFour=questionFour.getCheckedRadioButtonId();
            indexFive=questionFive.getCheckedRadioButtonId();
            String valueOne,valueTwo,valueThree,valueFour,valueFive;


            //verifying if all the datas are provided
            if(indexOne!=-1 && indexTwo!=-1 && indexThree!=-1 && indexFour!=-1 && indexFive!=-1){
                questionOneRadio=findViewById(indexOne);
                questionTwoRadio=findViewById(indexTwo);
                questionThreeRadio=findViewById(indexThree);
                questionFourRadio=findViewById(indexFour);
                questionFiveRadio=findViewById(indexFive);

                valueOne=questionOneRadio.getText().toString();
                valueTwo=questionTwoRadio.getText().toString();
                valueThree=questionThreeRadio.getText().toString();
                valueFour=questionFourRadio.getText().toString();
                valueFive=questionFiveRadio.getText().toString();
                //if any of the values of the questions are yes this if will go on work
                if(valueOne.equals("Yes") ||valueTwo.equals("Yes") || valueThree.equals("Yes") || valueFour.equals("Yes") || valueFive.equals("Yes")){
                    //a value of denied will be sent to the next activity
                    Snackbar.make(questionFiveRadio,"You are Not Eligible to Donate Right Now",Snackbar.LENGTH_SHORT)
                            .show();
                }else{
                    // if the answers are no then a value of accepeted will be sent
                    Intent intent = new Intent(DonationEligibility.this,DonationTimeAndCenter.class);
                    intent.putExtra("userNid",userNID);
                    Log.d("eligiblityusernid", "onCreate: "+userNID);
                    startActivity(intent);
                }
            }else{
                //asking to enter all the answers
                Snackbar.make(questionOne,"Please selected all the buttons!",Snackbar.LENGTH_SHORT)
                        .show();
            }



        });
    }
    //activity starter that dont pass any data
    private void startNewActivityNoData(Class newClass) {
        Intent intent=new Intent(DonationEligibility.this,newClass);
        startActivity(intent);

    }
    //custom backbutton pressed for system back button
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogLogOut = new AlertDialog.Builder(this);
        alertDialogLogOut.setMessage("Are you sure?");
        alertDialogLogOut.setCancelable(true);


        alertDialogLogOut.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startNewActivityNoData(MainActivity.class);
                        finish();
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