package amllc.com.lifelinedonation;

public class AppointmentModel {
    String userNid,userName,centerName,dateOfAppointments,timeOfAppointment,userBloodGroup,userGender;

    public AppointmentModel(String userNid, String userName, String centerName, String dateOfAppointments, String timeOfAppointment, String userBloodGroup, String userGender) {
        this.userNid = userNid;
        this.userName = userName;
        this.centerName = centerName;
        this.dateOfAppointments = dateOfAppointments;
        this.timeOfAppointment = timeOfAppointment;
        this.userBloodGroup = userBloodGroup;
        this.userGender = userGender;
    }

    public AppointmentModel() {
    }

    public AppointmentModel(String userNid, String centerName, String dateOfAppointments) {
        this.userNid = userNid;
        this.centerName = centerName;
        this.dateOfAppointments = dateOfAppointments;
    }

    public String getUserNid() {
        return userNid;
    }

    public void setUserNid(String userNid) {
        this.userNid = userNid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getDateOfAppointments() {
        return dateOfAppointments;
    }

    public void setDateOfAppointments(String dateOfAppointments) {
        this.dateOfAppointments = dateOfAppointments;
    }

    public String getTimeOfAppointment() {
        return timeOfAppointment;
    }

    public void setTimeOfAppointment(String timeOfAppointment) {
        this.timeOfAppointment = timeOfAppointment;
    }

    public String getUserBloodGroup() {
        return userBloodGroup;
    }

    public void setUserBloodGroup(String userBloodGroup) {
        this.userBloodGroup = userBloodGroup;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
