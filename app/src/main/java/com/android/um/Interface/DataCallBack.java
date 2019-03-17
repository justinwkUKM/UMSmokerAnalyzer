package com.android.um.Interface;

public interface DataCallBack<T,S> {
    void onReponse(T result);

    void onError(S result);
}
