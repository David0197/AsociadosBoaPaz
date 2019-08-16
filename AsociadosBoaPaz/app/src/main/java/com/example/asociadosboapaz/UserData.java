package com.example.asociadosboapaz;

public class UserData {
    public int id;
    public String gender,  disability;

    public UserData(){}

    public UserData( int id, String gender, String disability){

        this.id = id;
        this.gender = gender;
        this.disability = disability;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

}