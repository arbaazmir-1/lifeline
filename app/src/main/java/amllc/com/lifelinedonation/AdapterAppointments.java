package amllc.com.lifelinedonation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAppointments extends BaseAdapter {
    Context context;
    ArrayList<AppointmentModel> appointmentModels;

    public AdapterAppointments(Context context, ArrayList<AppointmentModel> appointmentModels) {
        this.context = context;
        this.appointmentModels = appointmentModels;
    }

    @Override
    public int getCount() {
        return this.appointmentModels.size();
    }

    @Override
    public Object getItem(int position) {
        return appointmentModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.single_appointment,null);
        TextView userNID= convertView.findViewById(R.id.userNidAppointment);
        TextView centerName=convertView.findViewById(R.id.userNameAppointment);
        TextView userBloodGroup=convertView.findViewById(R.id.userBloodDateAppointment);
        AppointmentModel appointmentModel = appointmentModels.get(position);
        userNID.setText(appointmentModel.getUserNid());
        centerName.setText(appointmentModel.getCenterName());
        userBloodGroup.setText(appointmentModel.getDateOfAppointments());

        return convertView;
    }
}
