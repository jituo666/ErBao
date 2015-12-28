package com.xiaoyu.erbao.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.cctrls.MyCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CalendarAdapter extends BaseAdapter {
    private boolean mIsLeapYear = false; // 是否为闰年
    private int mDaysOfMonth = 0; // 某月的天数
    private int mDayOfWeek = 0; // 具体某一天是星期几
    private int mDaysOfLastMonth = 0; // 上一个月的总天数
    private Context mContext;
    private String[] mDateArray = new String[42]; // 一个Gridview中的日期存入此数组中
    private MyCalendar mMyCalendar = null;
    private Drawable drawable = null;

    private String mCurrentYear = "";
    private String mCurrentMonth = "";
    private String mCurrentDay = "";

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1; // 用于标记当天

    private String showYear = ""; // 用于在头部显示的年份
    private String showMonth = ""; // 用于在头部显示的月份
    private String leapMonth = ""; // 闰哪一个月
    private String cyclical = ""; // 天干地支
    // 系统当前时间
    private String mCurrentSysDate = "";
    private String mCurrentSysYear = "";
    private String mCurrentSysMonth = "";
    private String mCurrentSysDay = "";

    public CalendarAdapter() {
        Date date = new Date();
        mCurrentSysDate = mSimpleDateFormat.format(date);
        mCurrentSysYear = mCurrentSysDate.split("-")[0];
        mCurrentSysMonth = mCurrentSysDate.split("-")[1];
        mCurrentSysDay = mCurrentSysDate.split("-")[2];

    }

    public CalendarAdapter(Context mContext, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c) {
        this();
        this.mContext = mContext;
        mMyCalendar = new MyCalendar();

        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            // 往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            // 往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }

        mCurrentYear = String.valueOf(stepYear); // 得到当前的年份
        mCurrentMonth = String.valueOf(stepMonth); // 得到本月
        mCurrentDay = String.valueOf(day_c); // 得到当前日期是哪天

        getCalendar(Integer.parseInt(mCurrentYear), Integer.parseInt(mCurrentMonth));

    }

    public CalendarAdapter(Context context, Resources rs, int year, int month, int day) {
        this();
        mContext = context;
        mMyCalendar = new MyCalendar();
        mCurrentYear = String.valueOf(year);// 得到跳转到的年份
        mCurrentMonth = String.valueOf(month); // 得到跳转到的月份
        mCurrentDay = String.valueOf(day); // 得到跳转到的天
        getCalendar(Integer.parseInt(mCurrentYear), Integer.parseInt(mCurrentMonth));
    }

    @Override
    public int getCount() {
        return mDateArray.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.calendar_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
        String d = mDateArray[position].split("\\.")[0];

        SpannableString sp = new SpannableString(d);
        sp.setSpan(new StyleSpan(Typeface.NORMAL), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(sp);
        textView.setTextColor(Color.GRAY);

        if (position < mDaysOfMonth + mDayOfWeek && position >= mDayOfWeek) {
            // 当前月信息显示
            textView.setTextColor(Color.BLACK);// 当月字体设黑
            drawable = new ColorDrawable(Color.rgb(23, 126, 214));
            if (position % 7 == 0 || position % 7 == 6) {
                // 当前月信息显示
                textView.setTextColor(Color.rgb(23, 126, 214));// 当月字体设黑
                drawable = new ColorDrawable(Color.rgb(23, 126, 214));
            }
        }

        if (currentFlag == position) {
            // 设置当天的背景
            drawable = new ColorDrawable(Color.rgb(23, 126, 214));
            textView.setBackgroundDrawable(drawable);
            textView.setTextColor(Color.WHITE);
        }
        return convertView;
    }

    // 得到某年的某月的天数且这月的第一天是星期几
    public void getCalendar(int year, int month) {
        mIsLeapYear = mMyCalendar.isLeapYear(year); // 是否为闰年
        mDaysOfMonth = mMyCalendar.getDaysOfMonth(mIsLeapYear, month); // 某月的总天数
        mDayOfWeek = mMyCalendar.getWeekdayOfMonth(year, month); // 某月第一天为星期几
        mDaysOfLastMonth = mMyCalendar.getDaysOfMonth(mIsLeapYear, month - 1); // 上一个月的总天数
        Log.d("DAY", mIsLeapYear + " ======  " + mDaysOfMonth + "  ============  " + mDayOfWeek + "  =========   " + mDaysOfLastMonth);
        getWeek(year, month);
    }

    // 将一个月中的每一天的值添加入数组dayNuber中
    private void getWeek(int year, int month) {
        int j = 1;
        int flag = 0;
        String lunarDay = "";

        // 得到当前月的所有日程日期(这些日期需要标记)
        for (int i = 0; i < mDateArray.length; i++) {
                if (i < mDayOfWeek) { // 前一个月
                int temp = mDaysOfLastMonth - mDayOfWeek + 1;
                mDateArray[i] = (temp + i) + "." + lunarDay;
            } else if (i < mDaysOfMonth + mDayOfWeek) { // 本月
                String day = String.valueOf(i - mDayOfWeek + 1); // 得到的日期
                mDateArray[i] = i - mDayOfWeek + 1 + "." + lunarDay;
                // 对于当前月才去标记当前日期
                if (mCurrentSysYear.equals(String.valueOf(year)) && mCurrentSysMonth.equals(String.valueOf(month)) && mCurrentSysDay.equals(day)) {
                    // 标记当前日期
                    currentFlag = i;
                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
            } else { // 下一个月
                mDateArray[i] = j + "." + lunarDay;
                j++;
            }
        }

        String abc = "";
        for (int i = 0; i < mDateArray.length; i++) {
            abc = abc + mDateArray[i] + ":";
        }

    }


    public String getDateByClickItem(int position) {
        return mDateArray[position];
    }


    public int getStartPositon() {
        return mDayOfWeek + 7;
    }

    public int getEndPosition() {
        return (mDayOfWeek + mDaysOfMonth + 7) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

}
