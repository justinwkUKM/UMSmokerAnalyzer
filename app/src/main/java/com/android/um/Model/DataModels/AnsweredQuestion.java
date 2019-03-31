package com.android.um.Model.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AnsweredQuestion implements Parcelable {

    private int id;
    private String description;
    private com.android.um.Model.DataModels.options selectedOption;
    private String category;

    public AnsweredQuestion()
    {

    }

    protected AnsweredQuestion(Parcel in) {
        id = in.readInt();
        description = in.readString();
        selectedOption = in.readParcelable(options.class.getClassLoader());
        category = in.readString();
    }

    public static final Creator<AnsweredQuestion> CREATOR = new Creator<AnsweredQuestion>() {
        @Override
        public AnsweredQuestion createFromParcel(Parcel in) {
            return new AnsweredQuestion(in);
        }

        @Override
        public AnsweredQuestion[] newArray(int size) {
            return new AnsweredQuestion[size];
        }
    };

    public void AddAnsweredQuestion(Question question, options option)
    {
        this.id=question.getId();
        this.category=question.getCategory();
        this.description=question.getDescription();
        this.selectedOption=option;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public options getSelectedOptions() {
        return selectedOption;
    }

    public void setSelectedOptions(options selectedOptions) {
        this.selectedOption = selectedOptions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeParcelable(selectedOption, flags);
        dest.writeString(category);
    }
}
