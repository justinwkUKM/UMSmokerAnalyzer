package com.android.um;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.adapter.SmokeDiaryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmokeDiaryActivity extends BaseActivity {

    @BindView(R.id.add_smoke_diary)
    FloatingActionButton addSmokeDiary;
    @BindView(R.id.rv_smoke_diary)
    RecyclerView rvSmokeDiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.smoke_diary_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        SmokeDiaryModel s1=new SmokeDiaryModel();
        s1.setDay("20");
        s1.setDate("sep 2019");
        s1.setCravings(4);
        s1.setSeverity(3.5);
        s1.setSmoked("bored");

        SmokeDiaryModel s2=new SmokeDiaryModel();
        s2.setDay("20");
        s2.setDate("sep 2019");
        s2.setCravings(4);
        s2.setSeverity(3.5);

        SmokeDiaryModel s3=new SmokeDiaryModel();
        s3.setDay("20");
        s3.setDate("sep 2019");
        s3.setCravings(4);
        s3.setSeverity(3.5);

        SmokeDiaryModel s4=new SmokeDiaryModel();
        s4.setDay("20");
        s4.setDate("sep 2019");
        s4.setCravings(4);
        s4.setSeverity(3.5);

        SmokeDiaryModel s5=new SmokeDiaryModel();
        s5.setDay("20");
        s5.setDate("sep 2019");
        s5.setCravings(4);
        s5.setSeverity(3.5);

        SmokeDiaryModel s6=new SmokeDiaryModel();
        s6.setDay("20");
        s6.setDate("sep 2019");
        s6.setCravings(4);
        s6.setSeverity(3.5);

        SmokeDiaryModel s7=new SmokeDiaryModel();
        s7.setDay("20");
        s7.setDate("sep 2019");
        s7.setCravings(4);
        s7.setSeverity(3.5);

        SmokeDiaryModel s8=new SmokeDiaryModel();
        s8.setDay("20");
        s8.setDate("sep 2019");
        s8.setCravings(4);
        s8.setSeverity(3.5);

        SmokeDiaryModel s9=new SmokeDiaryModel();
        s9.setDay("20");
        s9.setDate("sep 2019");
        s9.setCravings(4);
        s9.setSeverity(3.5);

        SmokeDiaryModel s10=new SmokeDiaryModel();
        s10.setDay("20");
        s10.setDate("sep 2019");
        s10.setCravings(4);
        s1.setSeverity(3.5);


        ArrayList<SmokeDiaryModel> list=new ArrayList<>();
        list.add(s1);
        list.add(s3);
        list.add(s4);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);
        list.add(s8);
        list.add(s9);
        list.add(s10);

        SmokeDiaryAdapter adapter=new SmokeDiaryAdapter(list);
        rvSmokeDiary.setLayoutManager(new LinearLayoutManager(this));
        rvSmokeDiary.setAdapter(adapter);
    }

    @Override
    public void setLocale(String localeName, int layout) {
        super.setLocale(localeName, layout);
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @OnClick(R.id.add_smoke_diary)
    public void onViewClicked() {
    }
}
