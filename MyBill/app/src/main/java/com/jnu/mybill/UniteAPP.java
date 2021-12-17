package com.jnu.mybill;

import android.app.Application;

import com.jnu.mybill.data.DBControler;

public class UniteAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库
        DBControler.init(getApplicationContext());
    }
}
