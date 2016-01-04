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

    private long mLastMensStartDate;
    private long mMensPeriod;
    private DateInterval mYJ;
    private DateInterval mPL;
    private DateInterval mYCYJ;
    private long mPLDate;
    //

}
