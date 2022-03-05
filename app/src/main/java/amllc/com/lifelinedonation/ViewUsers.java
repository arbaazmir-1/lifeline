package amllc.com.lifelinedonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity {
    DBHelper db;
    ArrayList<User> userArrayList;
    AdapterDB adapterDB;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        db = new DBHelper(this);
         listView =findViewById(R.id.userListView);
        userArrayList=new ArrayList<>();
        initSearchWidgets();
        getDatainListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView userNid=view.findViewById(R.id.userNid);
                Intent intent= new Intent(ViewUsers.this,SingleUserView.class);
                intent.putExtra("userNid",userNid.getText().toString());
                startActivity(intent);

            }
        });
    }
    private void initSearchWidgets(){
        SearchView searchView=findViewById(R.id.searchUserView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<User> filteredUsers= new ArrayList<User>();
                for(User user: userArrayList){
                    if(user.getUserNid().toLowerCase().contains(newText.toLowerCase())){
                        filteredUsers.add(user);
                    }
                }
                AdapterDB adapterDB=new AdapterDB(getApplicationContext(),filteredUsers);
                listView.setAdapter(adapterDB);
                return false;
            }
        });
    }
    private void getDatainListView() {
        userArrayList=db.getAllUserdata();
        adapterDB=new AdapterDB(this,userArrayList);
        listView.setAdapter(adapterDB);
        adapterDB.notifyDataSetChanged();
    }
}