package com.android.um.Dagger;

import com.android.um.Model.SharedPref.SharedPrefsInstance;

import dagger.Component;

@Component(modules = SharedPrefsModul.class)
public interface SharedPrefComponent {

    public void inject(SharedPrefsInstance context);
}
