package com.android.um.addmotivationmessage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.Model.SharedPrefsManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMotivationMessagesPresenter implements AddMotivationMessagesContract.Presenter{

    private AddMotivationMessagesContract.View mView;
    private DataHandler mDataHandler;
    String name;
    String imageUrl;

    public AddMotivationMessagesPresenter(AddMotivationMessagesContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void addMessage(final String message) {
        MotivationMessageModel messageModel=new MotivationMessageModel();
        messageModel.setImageUrl(this.imageUrl);
        messageModel.setMessage(message);
        messageModel.setName(this.name);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
        messageModel.setMessage_date(format.format(calendar.getTime()));
        mDataHandler.addMotivtationMessages(messageModel, new DataCallBack<String, String>() {
            @Override
            public void onReponse(String result) {

                mDataHandler.deleteString("MOTIVATOR_IMAGE");
                mView.addMessageSuccess(message);
            }

            @Override
            public void onError(String result) {
                mDataHandler.deleteString("MOTIVATOR_IMAGE");
                mView.addMessageFailed(result);
            }
        });
    }

    @Override
    public void start(@Nullable Bundle extras){
        this.name=extras.getString("MOTIVATOR_NAME");
        this.imageUrl=mDataHandler.getString("MOTIVATOR_IMAGE");
        mView.showMotivator(this.name,this.imageUrl);
    }

    @Override
    public void deleteImageUrl() {
        mDataHandler.deleteString("MOTIVATOR_IMAGE");
    }

    @Override
    public String getLanguage() {

        return mDataHandler.getLanguage();
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean checkifLogged() {
        return mDataHandler.checkLogged();
    }
}
