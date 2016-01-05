package com.xiaoyu.erbao.prefers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * jituo
 */
public class PersonalPrefers {

    private static SharedPreferences sPrefs = null;
    private static final String PREFS_KEY_MENS_TERM = "mens_term";
    private static final String PREFS_KEY_MENS_DAYS = "mens_days";


    private static SharedPreferences initSharedPreferences(Context ctx) {
        if (sPrefs == null) {
            sPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        }
        return sPrefs;
    }

    public static int getMensTerm(Context ctx) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        return prefs.getInt(PREFS_KEY_MENS_TERM, 0);
    }

    public static void setMensTerm(Context ctx, int term) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        prefs.edit().putInt(PREFS_KEY_MENS_TERM, term);
    }

    public static int getMensDays(Context ctx) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        return prefs.getInt(PREFS_KEY_MENS_DAYS, 0);
    }

    public static void setMensDays(Context ctx, int days) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        prefs.edit().putInt(PREFS_KEY_MENS_DAYS, days);
    }
}
