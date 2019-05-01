package com.wxc.coolcar.Guide;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 18/5/21.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    //设置划屏介绍的操作过程
    int PRIVATE_MODE = 0;

    //SharedPreferences 文件名
    private static final String PREF_NAME = "intro_slider";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
