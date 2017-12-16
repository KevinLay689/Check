package com.example.kevinlay.check;

/**
 * Created by kevinlay on 12/15/17.
 */

public class User {
    private String firstName;
    private String lastName;
    private String hometown;
    private String email;
    private String major;

    public User(String firstName, String lastName, String hometown, String major, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hometown = hometown;
        this.major = major;
        this.email = email;
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
}
