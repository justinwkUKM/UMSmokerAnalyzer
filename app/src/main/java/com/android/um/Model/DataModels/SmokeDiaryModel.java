package com.android.um.Model.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class SmokeDiaryModel implements Parcelable {

    String day;
    String date;
    String smoked;
    int cravings;
    double severity;

    public SmokeDiaryModel()
    {}
    protected SmokeDiaryModel(Parcel in) {
        day = in.readString();
        date = in.readString();
        smoked = in.readString();
        cravings = in.readInt();
        severity = in.readDouble();
    }

    public static final Creator<SmokeDiaryModel> CREATOR = new Creator<SmokeDiaryModel>() {
        @Override
        public SmokeDiaryModel createFromParcel(Parcel in) {
            return new SmokeDiaryModel(in);
        }

        @Override
        public SmokeDiaryModel[] newArray(int size) {
            return new SmokeDiaryModel[size];
        }
    };

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSmoked() {
        return smoked;
    }

    public void setSmoked(String smoked) {
        this.smoked = smoked;
    }

    public int getCravings() {
        return cravings;
    }

    public void setCravings(int cravings) {
        this.cravings = cravings;
    }

    public double getSeverity() {
        return severity;
    }

    public void setSeverity(double severity) {
        this.severity = severity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(date);
        dest.writeString(smoked);
        dest.writeInt(cravings);
        dest.writeDouble(severity);
    }
}
