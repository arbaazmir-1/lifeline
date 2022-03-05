package amllc.com.lifelinedonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterDB extends BaseAdapter {
    Context context;
    ArrayList<User> userArrayList;

    public AdapterDB(Context context,ArrayList<User> userArrayList) {
        this.context=context;
        this.userArrayList=userArrayList;
    }

    @Override
    public int getCount() {
        return this.userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.single_item_user,null);
            TextView userNID= convertView.findViewById(R.id.userNid);
            TextView userName=convertView.findViewById(R.id.userName);
            TextView userBloodGroup=convertView.findViewById(R.id.userBloodGroup);
            User user = userArrayList.get(position);
            userNID.setText(user.getUserNid());
            userName.setText(user.getName());
            userBloodGroup.setText(user.getBloodGroup());

        return convertView;
    }
}
