package com.android.um.Model.DataModels;


import com.google.gson.annotations.SerializedName;

public class PersonalityData {

    @SerializedName("scores")
    public PersonalityModel personalityModel;

    @SerializedName("error_message")
    public String error_message;

    public PersonalityModel getPersonalityModel() {
        return personalityModel;
    }

    public void setPersonalityModel(PersonalityModel personalityModel) {
        this.personalityModel = personalityModel;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
