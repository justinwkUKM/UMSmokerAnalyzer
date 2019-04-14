package com.android.um.Model.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MotivationMessageModel implements Parcelable {

    String name="";
    String message="";
    String message_date="";
    String imageUrl="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.message);
        dest.writeString(this.message_date);
        dest.writeString(this.imageUrl);
    }

    public MotivationMessageModel() {
    }

    protected MotivationMessageModel(Parcel in) {
        this.name = in.readString();
        this.message = in.readString();
        this.message_date = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<MotivationMessageModel> CREATOR = new Parcelable.Creator<MotivationMessageModel>() {
        @Override
        public MotivationMessageModel createFromParcel(Parcel source) {
            return new MotivationMessageModel(source);
        }

        @Override
        public MotivationMessageModel[] newArray(int size) {
            return new MotivationMessageModel[size];
        }
    };
}
