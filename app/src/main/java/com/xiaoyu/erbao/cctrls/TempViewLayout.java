package com.xiaoyu.erbao.cctrls;

import android.content.res.Resources;

import com.xiaoyu.erbao.R;

/**
 * Created by jituo on 15/12/27.
 */
public class TempViewLayout {
    public static final int PRE_SHOW_DAYS = 2;
    public static final int SHOW_DAYS = 365;
    public static final int TOTAL_SHOW_DAYS = PRE_SHOW_DAYS + SHOW_DAYS;
    public static final int GRAD_STEP = 5;
    public static final int MIDDLE_GRAD = 365;

    public static final int COLUMN_COUNT = 12;
    public static final int ROW_COUNT = 30;

    private float mColumnWidth;
    private float mRowHeight;
    private float mDateTextHeight;
    private int mContainerWidth;
    private int mContainerHeight;
    private float mContentLength;
    protected float mScrollPosition = -1;
    private int mVisibleStart;
    private int mVisibleEnd;
    private float mMiddleGridPos;
    private LayoutAttrs mAttrs;
    private Resources mRes;
    private String mGradLabels[];

    public static class LayoutAttrs {
        //日期
        public float dateTextSize;
        public int dateTextColor;
        // 刻度
        public float gradTextSize;
        public float gradTextLeftMargin;
        public int gradTextColor;
        //网络纵横线
        public float gridLineSize;
        public int gridLineColor;
        //网络分隔线
        public float dividerLineSize;
        public int dividerLineColor;
        //温度曲线
        public float tempLineSize;
        public int tempLineColor;
        //体温计标线
        public float thermometerLineSize;
        public int thermometerLineColor;

    }

    public TempViewLayout(Resources res, int viewWidth, int viewHeight) {
        mRes = res;
        mDateTextHeight = mRes.getDimensionPixelSize(R.dimen.temp_view_margin_y);
        mContainerWidth = viewWidth;
        mContainerHeight = viewHeight;
        mColumnWidth = mContainerWidth / COLUMN_COUNT;
        mRowHeight = (mContainerHeight-mDateTextHeight) / ROW_COUNT;
        mContentLength = SHOW_DAYS * mColumnWidth;
        initGradLabels();
        ////
        mAttrs = new LayoutAttrs();
        mAttrs.dateTextSize = mRes.getDimension(R.dimen.temp_dateTextSize);
        mAttrs.dividerLineSize = mRes.getDimension(R.dimen.temp_dividerLineSize);
        mAttrs.tempLineSize = mRes.getDimension(R.dimen.temp_tempLineSize);
        mAttrs.gradTextSize = mRes.getDimension(R.dimen.temp_gradTextSize);
        mAttrs.gradTextLeftMargin = mRes.getDimension(R.dimen.temp_gradTextLeftMargin);
        mAttrs.gridLineSize = mRes.getDimension(R.dimen.temp_gridLineSize);
        mAttrs.thermometerLineSize = mRes.getDimension(R.dimen.temp_thermometerLineSize);
        //
        mAttrs.dateTextColor = mRes.getColor(R.color.temp_dateTextColor);
        mAttrs.dividerLineColor = mRes.getColor(R.color.temp_dividerLineColor);
        mAttrs.gradTextColor = mRes.getColor(R.color.temp_gradTextColor);
        mAttrs.gridLineColor = mRes.getColor(R.color.temp_gridLineColor);
        mAttrs.tempLineColor = mRes.getColor(R.color.temp_tempLineColor);
        mAttrs.thermometerLineColor = mRes.getColor(R.color.temp_thermometerLineColor);
    }

    private void initGradLabels() {
        int count = ROW_COUNT / GRAD_STEP + 1;
        mGradLabels = new String[count];
        float middleGrad = (count) / 2;
        for (int i = 0; i < count; i++) {
            mGradLabels[count - 1 - i] = String.format("%2.1f", (365 - (middleGrad - i) * 5.0f) / 10);
            // Log.d("xxdd", i + " -------- " + mGradLabels[i]);
        }
        //最顶部和最底部的温度刻度目前为空
        mGradLabels[0] = "";
        mGradLabels[count - 1] = "";
        mMiddleGridPos = (middleGrad) * mRowHeight * GRAD_STEP + getDateTextHeight();
    }

    public LayoutAttrs getLayoutAtrrs() {
        return mAttrs;
    }

    public String[] getGradLabels() {
        return mGradLabels;
    }

    /**
     * 36.5标线所在Y方向位置
     *
     * @return
     */
    public float getMiddleGridPos() {
        return mMiddleGridPos;
    }

    public float getColumnWidth() {
        return mColumnWidth;
    }

    public float getRowHeight() {
        return mRowHeight;
    }

    public int getContainerWidth() {
        return mContainerWidth;
    }

    public int getContainerHeight() {
        return mContainerHeight;
    }

    public float getDateTextHeight() {
        return mDateTextHeight;
    }

    public float getLengthLimit() {
        float limit = mContentLength - mContainerWidth;
        return limit <= 0 ? 0 : limit;
    }


    public int getVisibleStart() {
        return mVisibleStart;
    }

    public int getVisibleEnd() {
        return mVisibleEnd;
    }


    public void setScrollPosition(float position) {

        if (mScrollPosition == position)
            return;
        mScrollPosition = position;
        int start = (int)((-position) / mColumnWidth);
        int end = (int)(start + mContainerWidth / mColumnWidth);
        if (mVisibleStart == start && mVisibleEnd == end)
            return;
        if (start < end) {
            mVisibleStart = start;
            mVisibleEnd = end;
        } else {
            mVisibleEnd = mVisibleStart = 0;
        }
    }
}
