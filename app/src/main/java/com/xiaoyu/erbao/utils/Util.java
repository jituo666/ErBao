package com.xiaoyu.erbao.utils;

/**
 * Created by jituo on 15/12/27.
 */
public class Util {
    public static final long  DAY_IN_MILLSECONDS = 24*60*60*1000l;
    public static final int  INVALIDE_VALUE = 0xFFFFFFFF;
    public static int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }
}
