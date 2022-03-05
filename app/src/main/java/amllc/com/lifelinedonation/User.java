package amllc.com.lifelinedonation;

public class User {
    String userNid,name,phoneNumber,dateOfBirth,bloodGroup,Gender;

    public User(String userNid, String name, String phoneNumber, String dateOfBirth, String bloodGroup, String gender) {
        this.userNid = userNid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        Gender = gender;
    }

    public User(String userNid, String name, String bloodGroup) {
        this.userNid = userNid;
        this.name = name;
        this.bloodGroup = bloodGroup;
    }

    public User() {
    }

    public String getUserNid() {
        return userNid;
    }

    public void setUserNid(String userNid) {
        this.userNid = userNid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
