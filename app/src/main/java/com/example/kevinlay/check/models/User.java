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
    private String location, flakeRating, preferredMajor;

    public User() {

    }

    public User(String firstName, String lastName, String hometown, String major, String email, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hometown = hometown;
        this.major = major;
        this.email = email;
        this.timeStart = "12:00";
        this.timeEnd = "14:00";
        this.userState = "Idle";
        this.partner = "";
        this.id = id;
        this.lunchTime = "";
        this.aboutMe = "";
        this.profilePic = "";
        this.flakeRating = "";
        this.location = "Cafe Russo";
        this.preferredMajor = "any";

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFlakeRating() {
        return flakeRating;
    }

    public void setFlakeRating(String flakeRating) {
        this.flakeRating = flakeRating;
    }

    public String getPreferredMajor() {
        return preferredMajor;
    }

    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }
}
