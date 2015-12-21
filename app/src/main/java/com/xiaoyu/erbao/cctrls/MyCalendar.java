package com.xiaoyu.erbao.cctrls;

import java.util.Calendar;


public class MyCalendar {

	private int mDaysOfMonth = 0; // 某月的天数
	private int mDayOfWeek = 0; // 具体某一天是星期几

	// 判断是否为闰年
	public boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 == 0) {
			return true;
		} else if (year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	// 得到某月有多少天数
	public int getDaysOfMonth(boolean isLeapyear, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			mDaysOfMonth = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			mDaysOfMonth = 30;
			break;
		case 2:
			if (isLeapyear) {
				mDaysOfMonth = 29;
			} else {
				mDaysOfMonth = 28;
			}

		}
		return mDaysOfMonth;
	}

	// 指定某年中的某月的第一天是星期几
	public int getWeekdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		mDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return mDayOfWeek;
	}

}
