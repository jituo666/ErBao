package com.xiaoyu.erbao.cctrls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.xiaoyu.erbao.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by jituo on 15/12/25.
 */
public class TempView extends View {

    private OverScroller mScroller;
    private GestureDetector mGestureDetector;
    private TempViewLayout mLayout;
    private TempViewLayout.LayoutAttrs mTempAtrrs;
    private SimpleDateFormat mDateFormat;
    private ScrollListener mScrollListener;
    private boolean mTouchDown;
    private int mTemperatures[];

    public interface ScrollListener {
        public void onScrollTo(int index);
    }

    public TempView(Context context) {
        super(context);
        init(context);
    }

    public TempView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TempView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context c) {
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mScroller = new OverScroller(c);
        mGestureDetector = new GestureDetector(c, new MyGestureListener());
        mLayout = new TempViewLayout(getResources(), 0, 0);
        mTempAtrrs = mLayout.getLayoutAtrrs();
        mDateFormat = new SimpleDateFormat("MM/dd");
        //test
        mTemperatures = new int[TempViewLayout.TOTAL_SHOW_DAYS + 1];
        for (int i = 0; i < mTemperatures.length; i++) {
            if (i <= TempViewLayout.PRE_SHOW_DAYS) {
                mTemperatures[i] = Util.INVALIDE_VALUE;
            } else {
                mTemperatures[i] = 360 + new Random().nextInt(11);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDown = true;
                mScroller.forceFinished(true);
                break;
            case MotionEvent.ACTION_UP:
                mTouchDown = false;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean more = mScroller.computeScrollOffset();
        int currentPosition = mScroller.getCurrX();
        mLayout.setScrollPosition(currentPosition);
        drawGridBackground(canvas);
        drawTemperatureLine(canvas);
        if (more) {
            invalidate();
        } else if (!mTouchDown) {
            int offset = currentPosition % mLayout.getColumnWidth();
            if (offset != 0) { // 吸附功能
                if (Math.abs(offset) >= mLayout.getColumnWidth() / 2) {//进
                    int dx = mLayout.getColumnWidth() - Math.abs(offset);
                    mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), offset > 0 ? dx : -dx, 0);
                } else {//退
                    mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), -offset, 0);
                }
                invalidate();
            } else {
                mScrollListener.onScrollTo(mLayout.getVisibleStart());
            }
        }
    }

    private void drawGridBackground(Canvas c) {
        Paint gridLinePaint = new Paint();
        gridLinePaint.setColor(mTempAtrrs.gridLineColor);
        gridLinePaint.setStrokeWidth(mTempAtrrs.gridLineSize);
        // 日期paint
        Paint datePaint = new Paint();
        datePaint.setColor(mTempAtrrs.dateTextColor);
        datePaint.setTextSize(mTempAtrrs.dateTextSize);
        int columnWidth = mLayout.getColumnWidth();
        int rowHeight = mLayout.getRowHeight();
        int offsetX = (columnWidth - mScroller.getCurrX() % columnWidth) % columnWidth;
        int columnCount = TempViewLayout.COLUMN_COUNT;
        int rowCount = TempViewLayout.ROW_COUNT;
        int marginY = mLayout.getMarginY();
        int fromX;
        int toX;
        int fromY = marginY;
        int toY = mLayout.getContainerHeight() + marginY;
        for (int i = (columnCount + 1); i >= -1; i--) { // 绘制竖线和日期,左右各扩展一个
            fromX = offsetX + mLayout.getContainerWidth() - i * columnWidth;
            toX = fromX;
            c.drawLine(fromX, fromY, toX, toY, gridLinePaint);
            String text = mDateFormat.format(new Date(System.currentTimeMillis() - (mLayout.getVisibleStart() + i - TempViewLayout.PRE_SHOW_DAYS) * Util.DAY_IN_MILLSECONDS));
            c.drawText(text, fromX - columnWidth / 2, fromY - mTempAtrrs.gradTextLeftMargin / 2, datePaint);
        }
        //刻度标签paint
        Paint gradLabelPaint = new Paint();
        gradLabelPaint.setColor(mTempAtrrs.gradTextColor);
        gradLabelPaint.setTextSize(mTempAtrrs.gradTextSize);
        fromX = 0;
        toX = mLayout.getContainerWidth();
        for (int i = 0; i <= rowCount; i++) { // 横线
            fromY = i * rowHeight + marginY;
            toY = fromY;
            if (i % TempViewLayout.GRAD_STEP == 0) {
                c.drawText(mLayout.getGradLabels()[i / TempViewLayout.GRAD_STEP], toX - 2 * columnWidth + mTempAtrrs.gradTextLeftMargin, toY, gradLabelPaint);
                if (mLayout.getGradLabels()[i / TempViewLayout.GRAD_STEP].length() > 0) {
                    gridLinePaint.setColor(mTempAtrrs.dividerLineColor);
                } else {
                    gridLinePaint.setColor(mTempAtrrs.gridLineColor);
                }
            } else {
                gridLinePaint.setColor(mTempAtrrs.gridLineColor);
            }
            c.drawLine(fromX, fromY, toX, toY, gridLinePaint);
        }
        //温度计标线
        gridLinePaint.setColor(mTempAtrrs.thermometerLineColor);
        int thermometerLineX = mLayout.getContainerWidth() - TempViewLayout.PRE_SHOW_DAYS * columnWidth;
        c.drawLine(thermometerLineX, marginY, thermometerLineX, mLayout.getContainerHeight() + marginY, gridLinePaint);
    }

    public void drawTemperatureLine(Canvas c) {
        int middleLine = mLayout.getMiddleGridPos();
        int maxOffset = (TempViewLayout.ROW_COUNT / TempViewLayout.GRAD_STEP + 1) / 2 * TempViewLayout.GRAD_STEP;
        int minOffset = TempViewLayout.ROW_COUNT - TempViewLayout.ROW_COUNT / 2;
        int lastX = Util.INVALIDE_VALUE;
        int lastY = Util.INVALIDE_VALUE;
        Paint tempDotPaint = new Paint();
        tempDotPaint.setColor(mTempAtrrs.tempLineColor);
        Paint tempLinePaint = new Paint();
        tempLinePaint.setAntiAlias(true);
        tempLinePaint.setStrokeWidth(mTempAtrrs.tempLineSize);
        tempLinePaint.setColor(mTempAtrrs.tempLineColor);
        int offsetX = (mLayout.getColumnWidth() - mScroller.getCurrX() % mLayout.getColumnWidth()) % mLayout.getColumnWidth();
        int xPos[] = new int[TempViewLayout.COLUMN_COUNT + 3];
        int yPos[] = new int[TempViewLayout.COLUMN_COUNT + 3];
        // 加2是因为左右两边各要多绘一个温度
        for (int i = 0; i <= TempViewLayout.COLUMN_COUNT + 2; i++) {
            int offset = (mTemperatures[i + mLayout.getVisibleStart()] - TempViewLayout.MIDDLE_GRAD); // 温度和中间线的偏移量
            if (Math.abs(offset) > maxOffset || Math.abs(offset) > minOffset) {
                lastX = Util.INVALIDE_VALUE;
                lastY = Util.INVALIDE_VALUE;
            } else {
                int x = offsetX + mLayout.getContainerWidth() - mLayout.getColumnWidth() * (i - 1);//减1是因为右边要多绘制一个温度
                int y = middleLine - offset * mLayout.getRowHeight();
                if (lastX != Util.INVALIDE_VALUE && lastY != Util.INVALIDE_VALUE) {
                    c.drawLine(x, y, lastX, lastY, tempLinePaint);
                }
                xPos[i] = x;
                yPos[i] = y;
                lastX = x;
                lastY = y;
            }
        }
        for (int i = 0; i <= TempViewLayout.COLUMN_COUNT + 2; i++) {
            tempDotPaint.setColor(Color.WHITE);
            c.drawCircle(xPos[i], yPos[i], 15, tempDotPaint);
            tempDotPaint.setColor(mTempAtrrs.tempLineColor);
            c.drawCircle(xPos[i], yPos[i], 10, tempDotPaint);
            tempDotPaint.setColor(Color.WHITE);
            c.drawCircle(xPos[i], yPos[i], 5, tempDotPaint);
        }
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
            int scrollLimit = -mLayout.getLengthLimit();
            if (scrollLimit == 0)
                return false;
            float velocity = velocityX;
            int startX = mScroller.getCurrX();
            mScroller.fling(startX, 0, (int) -velocity, 0, scrollLimit, 0, 0, 0, 0, 0);
            invalidate();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
            int distance = Math.round(distanceX);
            int currPosition = mScroller.getCurrX();
            int finalPosition = mScroller.isFinished() ? currPosition : mScroller.getFinalX();
            int newPosition = Util.clamp(finalPosition + distance, -mLayout.getLengthLimit(), 0);
            if (newPosition != currPosition) {
                mScroller.startScroll(currPosition, 0, newPosition - currPosition, 0, 0);
            }
            invalidate();
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        mLayout = new TempViewLayout(getResources(), right - left, bottom - top);
    }

    public void setmScrollListener(ScrollListener l) {
        mScrollListener = l;
    }

}
