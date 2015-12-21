package com.xiaoyu.erbao.cctrls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 农历算法
 *
 */
public class LunarCalendar {
	private int mYear; // 农历的年份
	private int mMonth;
	private int mDay;
	private String mLunarMonth; // 农历的月份
	private boolean mLeap;
	public int mLeapMonth = 0; // 闰的是哪个月

	final static String chineseNumber[] = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };
	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	final static long[] lunarInfo = new long[] { //
	0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, //
			0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, //
			0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, //
			0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, //
			0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, //
			0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, //
			0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, //
			0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, //
			0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, //
			0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, //
			0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, //
			0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, //
			0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, //
			0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, //
			0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, //
			0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, //
			0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };

	// 农历部分假日
	final static String[] lunarHolimDay = new String[] { "0101 春节", "0115 元宵", "0505 端午", "0707 情人", "0715 中元", "0815 中秋", "0909 重阳", "1208 腊八", "1224 小年", "0100 除夕" };

	// 公历部分节假日
	final static String[] solarHolimDay = new String[] { //
	"0101 元旦", "0214 情人", "0308 妇女", "0312 植树", "0315 消费者权益日", "0401 愚人", "0501 劳动", "0504 青年", //
			"0512 护士", "0601 儿童", "0701 建党", "0801 建军", "0808 父亲", "0909 毛泽东逝世纪念", "0910 教师", "0928 孔子诞辰",//
			"1001 国庆", "1006 老人", "1024 联合国日", "1112 孙中山诞辰纪念", "1220 澳门回归纪念", "1225 圣诞", "1226 毛泽东诞辰纪念" };

	// ====== 传回农历 y年的总天数
	final private static int mYearDays(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + mLeapDays(y));
	}

	// ====== 传回农历 y年闰月的天数
	final private static int mLeapDays(int y) {
		if (mLeapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	// ====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	final private static int mLeapMonth(int y) {
		int result = (int) (lunarInfo[y - 1900] & 0xf);
		return result;
	}

	// ====== 传回农历 y年m月的总天数
	final private static int mMonthDays(int y, int m) {
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	// ====== 传回农历 y年的生肖
	final public String animalsYear(int mYear) {
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(mYear - 4) % 12];
	}

	// ====== 传入 月日的offset 传回干支, 0=甲子
	final private static String cyclicalm(int num) {
		final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
		final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	// ====== 传入 offset 传回干支, 0=甲子
	final public String cyclical(int mYear) {
		int num = mYear - 1900 + 36;
		return (cyclicalm(num));
	}

	public static String getChinaDayString(int mDay) {
		String chineseTen[] = { "初", "十", "廿", "卅" };
		int n = mDay % 10 == 0 ? 9 : mDay % 10 - 1;
		if (mDay > 30)
			return "";
		if (mDay == 10)
			return "初十";
		else
			return chineseTen[mDay / 10] + chineseNumber[n];
	}

	/** */
	/**
	 * 传出y年m月d日对应的农历. mYearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
	 * mDayCyl5:与1900年1月31日相差的天数,再加40 ?
	 * 
	 * ismDay: 这个参数为false---日期为节假日时，阴历日期就返回节假日 ，true---不管日期是否为节假日依然返回这天对应的阴历日期
	 * 
	 * @param cal
	 * @return
	 */
	public String getLunarDate(int mYear_log, int mMonth_log, int mDay_log, boolean ismDay) {
		// @SuppressWarnings("unused")
		int mYearCyl, monCyl, mDayCyl;
		// int mLeapMonth = 0;
		String nowamDays;
		Date baseDate = null;
		Date nowamDay = null;
		try {
			baseDate = chineseDateFormat.parse("1900年1月31日");
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use
			// Options | File Templates.
		}

		nowamDays = mYear_log + "年" + mMonth_log + "月" + mDay_log + "日";
		try {
			nowamDay = chineseDateFormat.parse(nowamDays);
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use
		}

		// 求出和1900年1月31日相差的天数
		int offset = (int) ((nowamDay.getTime() - baseDate.getTime()) / 86400000L);
		mDayCyl = offset + 40;
		monCyl = 14;

		// 用offset减去每农历年的天数
		// 计算当天是农历第几天
		// i最终结果是农历的年份
		// offset是当年的第几天
		int iYear, mDaysOfYear = 0;
		for (iYear = 1900; iYear < 10000 && offset > 0; iYear++) {
			mDaysOfYear = mYearDays(iYear);
			offset -= mDaysOfYear;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += mDaysOfYear;
			iYear--;
			monCyl -= 12;
		}
		// 农历年份
		mYear = iYear;
		setYear(mYear); // 设置公历对应的农历年份

		mYearCyl = iYear - 1864;
		mLeapMonth = mLeapMonth(iYear); // 闰哪个月,1-12
		mLeap = false;

		// 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		int iMonth, mDaysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// 闰月
			if (mLeapMonth > 0 && iMonth == (mLeapMonth + 1) && !mLeap) {
				--iMonth;
				mLeap = true;
				mDaysOfMonth = mLeapDays(mYear);
			} else
				mDaysOfMonth = mMonthDays(mYear, iMonth);

			offset -= mDaysOfMonth;
			// 解除闰月
			if (mLeap && iMonth == (mLeapMonth + 1))
				mLeap = false;
			if (!mLeap)
				monCyl++;
		}
		// offset为0时，并且刚才计算的月份是闰月，要校正
		if (offset == 0 && mLeapMonth > 0 && iMonth == mLeapMonth + 1) {
			if (mLeap) {
				mLeap = false;
			} else {
				mLeap = true;
				--iMonth;
				--monCyl;
			}
		}
		// offset小于0时，也要校正
		if (offset < 0) {
			offset += mDaysOfMonth;
			--iMonth;
			--monCyl;
		}
		mMonth = iMonth;
		setLunarMonth(chineseNumber[mMonth - 1] + "月"); // 设置对应的阴历月份
		mDay = offset + 1;

		if (!ismDay) {
			// 如果日期为节假日则阴历日期则返回节假日
			// setLeapMonth(mLeapMonth);
			for (int i = 0; i < solarHolimDay.length; i++) {
				// 返回公历节假日名称
				String sd = solarHolimDay[i].split(" ")[0]; // 节假日的日期
				String sdv = solarHolimDay[i].split(" ")[1]; // 节假日的名称
				String smMonth_v = mMonth_log + "";
				String smDay_v = mDay_log + "";
				String smd = "";
				if (mMonth_log < 10) {
					smMonth_v = "0" + mMonth_log;
				}
				if (mDay_log < 10) {
					smDay_v = "0" + mDay_log;
				}
				smd = smMonth_v + smDay_v;
				if (sd.trim().equals(smd.trim())) {
					return sdv;
				}
			}

			for (int i = 0; i < lunarHolimDay.length; i++) {
				// 返回农历节假日名称
				String ld = lunarHolimDay[i].split(" ")[0]; // 节假日的日期
				String ldv = lunarHolimDay[i].split(" ")[1]; // 节假日的名称
				String lmMonth_v = mMonth + "";
				String lmDay_v = mDay + "";
				String lmd = "";
				if (mMonth < 10) {
					lmMonth_v = "0" + mMonth;
				}
				if (mDay < 10) {
					lmDay_v = "0" + mDay;
				}
				lmd = lmMonth_v + lmDay_v;
				if (ld.trim().equals(lmd.trim())) {
					return ldv;
				}
			}
		}
		if (mDay == 1)
			return chineseNumber[mMonth - 1] + "月";
		else
			return getChinaDayString(mDay);

	}

	public String toString() {
		if (chineseNumber[mMonth - 1] == "一" && getChinaDayString(mDay) == "初一")
			return "农历" + mYear + "年";
		else if (getChinaDayString(mDay) == "初一")
			return chineseNumber[mMonth - 1] + "月";
		else
			return getChinaDayString(mDay);
		// return mYear + "年" + (mLeap ? "闰" : "") + chineseNumber[mMonth - 1] +
		// "月" + getChinaDayString(mDay);
	}

	/*
	 * public static void main(String[] args) { System.out.println(new
	 * LunarCalendar().getLunarDate(2012, 1, 23)); }
	 */

	public int getLeapMonth() {
		return mLeapMonth;
	}

	public void setLeapMonth(int mLeapMonth) {
		this.mLeapMonth = mLeapMonth;
	}

	/**
	 * 得到当前日期对应的阴历月份
	 * 
	 * @return
	 */
	public String getLunarMonth() {
		return mLunarMonth;
	}

	public void setLunarMonth(String mLunarMonth) {
		this.mLunarMonth = mLunarMonth;
	}

	/**
	 * 得到当前年对应的农历年份
	 * 
	 * @return
	 */
	public int getYear() {
		return mYear;
	}

	public void setYear(int mYear) {
		this.mYear = mYear;
	}
}
