package com.android.um.Model.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class options implements Parcelable {

    private int index;
    private String description;
    private String Type;
    private int score;
    private String value;

    public options()
    {

    }
    protected options(Parcel in) {
        index = in.readInt();
        description = in.readString();
        Type = in.readString();
        score = in.readInt();
        value = in.readString();
    }

    public static final Creator<options> CREATOR = new Creator<options>() {
        @Override
        public options createFromParcel(Parcel in) {
            return new options(in);
        }

        @Override
        public options[] newArray(int size) {
            return new options[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public void setIndex(int id) {
        this.index = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(description);
        dest.writeString(Type);
        dest.writeInt(score);
        dest.writeString(value);
    }
}
