package com.android.um.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.SmokeFreeTime;
import com.android.um.Model.SharedPrefsManager;

import java.util.Date;

public class DashboardPresenter implements DashboardContract.Presenter{

    private DashboardContract.View mView;
    private DataHandler mDataHandler;

    public DashboardPresenter(DashboardContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }


    @Override
    public void start(@Nullable Bundle extras){
    }

    @Override
    public void getTargetToSave() {

        if (mDataHandler.getTargetToSaveLocaly()!=null && Double.parseDouble(mDataHandler.getTargetToSaveLocaly())!=0.0)
        {
            mView.showTargetToSave(mDataHandler.getTargetToSaveLocaly());
        }
        else
        {
            mDataHandler.getTargetToSaveOnline(new DataCallBack<Double, String>() {
                @Override
                public void onReponse(Double result) {
                    mView.showTargetToSave(""+result);
                }

                @Override
                public void onError(String result) {
                    mView.showTargetToSave("0.0");
                }
            });
        }


    }

    @Override
    public void startSmokeFreeTime() {

        //get the start date of smoke free ,for now it started when he adds smoke diary
        mDataHandler.getSmokeFreeTime(new DataCallBack<SmokeFreeTime, String>() {
            @Override
            public void onReponse(final SmokeFreeTime smokeFreeTime) {
                //show smoke free lable
                mView.showSmokeFreeTime();
                //get the real time from firebase to avoid any cheating
                mDataHandler.getFirebaseTime(new DataCallBack<Long, String>() {
                    @Override
                    public void onReponse(Long result) {

                        Date firebaseDate = new Date(result);

                       final SmokeFreeTime smokeFreeDiffernce= getDifferentSmokeFreeTime(smokeFreeTime.getStartDate(),firebaseDate);
                            smokeFreeDiffernce.setStartDate(smokeFreeTime.getStartDate());
                            mDataHandler.startTimer(new DataCallBack<Long, String>() {
                                @Override
                                public void onReponse(Long result) {
                                    smokeFreeDiffernce.setSeconds(smokeFreeDiffernce.getSeconds()+1);
                                    if (smokeFreeDiffernce.getSeconds()==60)
                                    {
                                        smokeFreeDiffernce.setSeconds(0);
                                        smokeFreeDiffernce.setMinutes(smokeFreeDiffernce.getMinutes()+1);
                                    }
                                    if (smokeFreeDiffernce.getMinutes()==60)
                                    {
                                        smokeFreeDiffernce.setMinutes(0);
                                        smokeFreeDiffernce.setHour(smokeFreeDiffernce.getHour()+1);
                                    }
                                    if (smokeFreeDiffernce.getMinutes()!=0 &&((int)smokeFreeDiffernce.getMinutes()%10)==0)
                                    {

                                        mDataHandler.updateSmokeFreeTime(smokeFreeDiffernce);
                                    }

                                    mView.updateSmokeFreeTimer(smokeFreeDiffernce.getSeconds(),smokeFreeDiffernce.getMinutes(),smokeFreeDiffernce.getHour());
                                }

                                @Override
                                public void onError(String result) {

                                }
                            });

                    }

                    @Override
                    public void onError(String result) {

                    }
                });
            }

            @Override
            public void onError(String result) {

            }
        });
    }

    public SmokeFreeTime getDifferentSmokeFreeTime(Date startDate,Date endDate)
    {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        SmokeFreeTime smokeFreeTime=new SmokeFreeTime();
        smokeFreeTime.setHour(elapsedHours);
        smokeFreeTime.setMinutes(elapsedMinutes);
        smokeFreeTime.setSeconds(elapsedSeconds);

        return smokeFreeTime;

    }
    @Override
    public void stopTimer() {
        mDataHandler.stopTimer();
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
