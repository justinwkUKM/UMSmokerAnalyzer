package com.android.um.Model.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User implements Parcelable {

    private String username;
    private String email;
    private String password;
    private int age=0;
    private String gender;
    private ArrayList<AnsweredQuestion> demographicQuestions;
    private ArrayList<AnsweredQuestion> levelofAddictionsQuestions;
    private String id;
    private TargetToSaveModel target;
    private SmokeDiaryModel smokeDiaryModel;

    public User() {
    }


    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        age = in.readInt();
        gender = in.readString();
        id = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<AnsweredQuestion> getDemographicQuestions() {
        return demographicQuestions;
    }

    public void setDemographicQuestions(ArrayList<AnsweredQuestion> questions) {
        this.demographicQuestions = questions;
    }

    public ArrayList<AnsweredQuestion> getLevelofAddictionsQuestions() {
        return levelofAddictionsQuestions;
    }

    public void setLevelofAddictionsQuestions(ArrayList<AnsweredQuestion> levelofAddictionsQuestions) {
        this.levelofAddictionsQuestions = levelofAddictionsQuestions;
    }

    public TargetToSaveModel getTarget() {
        return target;
    }

    public void setTarget(TargetToSaveModel target) {
        this.target = target;
    }

    public void setFirebaseUser(FirebaseUser user)
    {
        if (this.username==null)
            this.setUsername(user.getDisplayName());

        this.setEmail(user.getEmail());
        this.setId(user.getUid());
    }

    public SmokeDiaryModel getSmokeDiaryModel() {
        return smokeDiaryModel;
    }

    public void setSmokeDiaryModel(SmokeDiaryModel smokeDiaryModel) {
        this.smokeDiaryModel = smokeDiaryModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(age);
        dest.writeString(gender);
        dest.writeString(id);
    }
}
