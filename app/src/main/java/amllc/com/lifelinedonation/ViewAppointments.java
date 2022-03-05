package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewAppointments extends AppCompatActivity {
    DBHelper db;
    ArrayList<AppointmentModel> appointmentModels;
    AdapterAppointments adapterAppointments;
    ListView listView;
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);
        db=new DBHelper(this);
        listView=findViewById(R.id.viewAppointmentsListView);
        appointmentModels=new ArrayList<>();
        backButton=findViewById(R.id.backBtn);
        initSearchWidgets();
        getDetailsListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView userNid=view.findViewById(R.id.userNidAppointment);
                Intent intent= new Intent(ViewAppointments.this,SingleAppointmentView.class);
                intent.putExtra("userNid",userNid.getText().toString());
                startActivity(intent);

            }
        });
        backButton.setOnClickListener(v->{
            Intent intent=new Intent(ViewAppointments.this,AdminHome.class);
            finishAffinity();
            startActivity(intent);
            finish();
        });
    }
    private void initSearchWidgets(){
       SearchView searchView=findViewById(R.id.searchViewAppointments);
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               ArrayList<AppointmentModel> filteredAppointment=new ArrayList<AppointmentModel>();
               for(AppointmentModel appointmentModel:appointmentModels){
                   if(appointmentModel.getUserNid().toLowerCase().contains(newText.toLowerCase())){
                       filteredAppointment.add(appointmentModel);
                   }
               }
               AdapterAppointments adapterAppointments=new AdapterAppointments(getApplicationContext(),filteredAppointment);
               listView.setAdapter(adapterAppointments);
               return false;
           }
       });
    }

    private void getDetailsListView() {
        appointmentModels=db.getAllApointments();
        adapterAppointments=new AdapterAppointments(this,appointmentModels);
        listView.setAdapter(adapterAppointments);
        adapterAppointments.notifyDataSetChanged();


    }
}