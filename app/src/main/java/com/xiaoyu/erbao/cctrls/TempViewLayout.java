package com.xiaoyu.erbao.cctrls;

import android.content.res.Resources;

import com.xiaoyu.erbao.R;

/**
 * Created by jituo on 15/12/27.
 */
public class TempViewLayout {
    public static final int PRE_SHOW_DAYS = 2;
    public static final int SHOW_DAYS = 365 + PRE_SHOW_DAYS;
    private static final int COLUMN_COUNT = 12;
    private static final int ROW_COUNT = 30;
    private int mColumnWidth;
    private int mRowHeight;
    private int mMarginY;
    private int mContainerWidth;
    private int mContainerHeight;
    private int mContentLength;
    protected int mScrollPosition = -1;
    private int mVisibleStart;
    private int mVisibleEnd;
    private int mMiddleGridPos;
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
        mMarginY = mRes.getDimensionPixelSize(R.dimen.temp_view_margin_y);
        mContainerWidth = viewWidth;
        mContainerHeight = viewHeight - 2 * mMarginY;
        mContainerHeight = mContainerHeight - mContainerHeight % ROW_COUNT;
        mColumnWidth = mContainerWidth / COLUMN_COUNT;
        mRowHeight = mContainerHeight / ROW_COUNT;
        mContentLength = SHOW_DAYS* mColumnWidth;
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
        int count = ROW_COUNT / 5 + 1;
        mGradLabels = new String[count];
        int middleGrad = count / 2;
        for (int i = 0; i < count; i++) {
            mGradLabels[count - 1 - i] = String.format("%2.1f", (365 - (middleGrad - i) * 5.0f) / 10);
            // Log.d("xxdd", i + " -------- " + mGradLabels[i]);
        }
        mGradLabels[0] = "";
        mGradLabels[count - 1] = "";
        mMiddleGridPos = (middleGrad) * mRowHeight * 5 + getMarginY();
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
    public int getMiddleGridPos() {
        return mMiddleGridPos;
    }

    public int getmColumnWidth() {
        return mColumnWidth;
    }

    public int getRowHeight() {
        return mRowHeight;
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }


    public int getRowCount() {
        return ROW_COUNT;
    }

    public int getContainerWidth() {
        return mContainerWidth;
    }

    public int getContainerHeight() {
        return mContainerHeight;
    }

    public int getMarginY() {
        return mMarginY;
    }

    public int getLengthLimit() {
        int limit = mContentLength - mContainerWidth;
        return limit <= 0 ? 0 : limit;
    }


    public int getVisibleStart() {
        return mVisibleStart;
    }

    public int getVisibleEnd() {
        return mVisibleEnd;
    }


    public void setScrollPosition(int position) {

        if (mScrollPosition == position)
            return;
        mScrollPosition = position;
        int start = (-position) / mColumnWidth;
        int end = start + mContainerWidth / mColumnWidth;
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
