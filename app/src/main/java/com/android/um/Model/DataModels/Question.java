package com.android.um.Model.DataModels;

import java.util.ArrayList;

public class Question {

    private int id;
    private String description;
    private ArrayList<com.android.um.Model.DataModels.options> options;
    private com.android.um.Model.DataModels.options selectedOptions;
    private String category;
    private String Type;


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

    public ArrayList<com.android.um.Model.DataModels.options> getQustionOptions() {
        return options;
    }

    public void setQustionOptions(ArrayList<com.android.um.Model.DataModels.options> qustionOptions) {
        this.options = qustionOptions;
    }

    public com.android.um.Model.DataModels.options getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(com.android.um.Model.DataModels.options selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setSelectedOption(options option)
    {
        ArrayList<options> options=new ArrayList<>();
        options.add(option);
        setQustionOptions(options);
    }

}
