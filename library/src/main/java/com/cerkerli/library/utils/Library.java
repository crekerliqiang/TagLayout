package com.cerkerli.library.utils;

import android.content.Context;

/**
 * 将Context传入库
 */
public class Library {
    public static Context mContext = null;
    public static void init(Context context){
        mContext = context;
    }
    public static Context getContext(){
        if(mContext == null){
            throw new RuntimeException("Library is not initialized");
        }
        return mContext;
    }
}
