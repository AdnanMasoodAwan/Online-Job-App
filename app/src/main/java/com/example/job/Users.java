package com.example.job;



public class Users
{
    public String name, email, password, type,profileimage,phone;

    public Users()
    {

    }

    public Users(String name, String email, String password, String type, String profileimage,String  phone) {
        this.name = name;
        this.phone=phone;
        this.email = email;
        this.password = password;
        this.type = type;
        this.profileimage = profileimage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
}

