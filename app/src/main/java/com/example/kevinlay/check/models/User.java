package com.example.kevinlay.check.models;

/**
 * Created by kevinlay on 12/15/17.
 */

public class User {
    private String firstName;
    private String lastName;
    private String hometown;
    private String email;
    private String major;
    private String timeStart, timeEnd, userState, partner, aboutMe, profilePic, id, lunchTime;

    public User() {

    }

    public User(String firstName, String lastName, String hometown, String major, String email, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hometown = hometown;
        this.major = major;
        this.email = email;
        this.timeStart = "";
        this.timeEnd = "";
        this.userState = "";
        this.partner = "";
        this.aboutMe = "";
        this.profilePic = "";
        this.id = id;
        this.lunchTime = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
