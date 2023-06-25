package com.example.codehub;

public class userModel {
    private String userName, userEmail, uPhno, profilePic;

    public userModel(){
    }

    public userModel(String userName, String userEmail, String uPhno, String profilePic) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.uPhno = uPhno;
        this.profilePic = profilePic;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProfilePic() { return profilePic; }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getuPhno() {
        return uPhno;
    }

    public void setuPhno(String uPhno) {
        this.uPhno = uPhno;
    }
}
