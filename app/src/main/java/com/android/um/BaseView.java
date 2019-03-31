package com.android.um;

import android.content.Context;

/**
 * Base View interface to be extended by all Views of the app
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    Context getContext();
    void showLoading();
    void hideLoading();
}