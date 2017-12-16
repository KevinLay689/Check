package com.example.kevinlay.check;

/**
 * Created by kevinlay on 12/15/17.
 */

public class User {
    private String name;
    private String email;
    private String major;

    public User(String name, String email, String major) {
        this.name = name;
        this.email = email;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
