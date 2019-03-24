package com.android.um.Model.DataModels;

import java.util.ArrayList;

public class AnsweredQuestion {

    private int id;
    private String description;
    private com.android.um.Model.DataModels.options selectedOption;
    private String category;

    public AnsweredQuestion()
    {

    }

    public void AddAnsweredQuestion(Question question,options option)
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
}
