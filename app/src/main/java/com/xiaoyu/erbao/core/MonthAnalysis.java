package com.xiaoyu.erbao.core;

/**
 * Created by jituo on 15/12/30.
 * 计算月经周期,排卵期,安全期,预测月经期,排卵日,每天好友率
 */
public class MonthAnalysis {

    public class DateInterval {
        public long start;
        public long end;
    }

    private long mLastMensStartDate;//上次月经
    private long mMensPeriod;//月经周期天数
    private DateInterval mYJ;//月经日期
    private DateInterval mPL; //排卵日期
    private DateInterval mYCYJ;//预测月经日期
    private long mPLDate;
}
