package com.cerkerli.library.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * 随机颜色工具
 */
public class RandomColor {
    private static final Random mRandom = new Random();


    //背景颜色
    private static final int[] COLORS = {

            Color.YELLOW,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.parseColor("#0CFF15"),
            Color.parseColor("#FFB42C"),
            Color.parseColor("#913DFF"),
            Color.parseColor("#52AFFF"),
    };

    public static int getColor(){
       return COLORS[mRandom.nextInt(COLORS.length)];
    }
}
