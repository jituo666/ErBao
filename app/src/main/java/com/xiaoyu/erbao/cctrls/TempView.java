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
        void onScrollTo(int distanceToCurrentDay);
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
        //test data
        mTemperatures = new int[TempViewLayout.TOTAL_SHOW_DAYS + 1];
        for (int i = 0; i < mTemperatures.length; i++) {
            mTemperatures[i] = Util.INVALIDE_VALUE;
        }
    }

    public void setTemperature(int temperature[]) {
        mTemperatures = temperature;
        invalidate();
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
        float currentPosition = mScroller.getCurrX();
        mLayout.setScrollPosition(currentPosition);
        drawColorBackground(canvas);
        drawGridBackground(canvas);
        drawTemperatureLine(canvas);
        if (more) {
            invalidate();
        } else if (!mTouchDown) {
            float offset = currentPosition % mLayout.getColumnWidth();
            if (offset != 0) { // 吸附功能
                if (Math.abs(offset) >= mLayout.getColumnWidth() / 2) {//进
                    float dx = mLayout.getColumnWidth() - Math.abs(offset);
                    mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), (int) (offset > 0 ? dx : -dx), 0);
                } else {//退
                    mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), (int) -offset, 0);
                }
                invalidate();
            } else {
                if (mScrollListener != null) {
                    mScrollListener.onScrollTo(mLayout.getVisibleStart());
                }
            }
        }
    }

    private void drawGridBackground(Canvas c) {
        Paint gridLinePaint = new Paint();
        gridLinePaint.setAntiAlias(true);
        gridLinePaint.setColor(mTempAtrrs.gridLineColor);
        gridLinePaint.setStrokeWidth(mTempAtrrs.gridLineSize);
        // 日期paint
        Paint datePaint = new Paint();
        datePaint.setAntiAlias(true);
        datePaint.setColor(mTempAtrrs.dateTextColor);
        datePaint.setTextSize(mTempAtrrs.dateTextSize);
        float columnWidth = mLayout.getColumnWidth();
        float rowHeight = mLayout.getRowHeight();
        float offsetX = (columnWidth - mScroller.getCurrX() % columnWidth) % columnWidth;
        int columnCount = TempViewLayout.COLUMN_COUNT;
        int rowCount = TempViewLayout.ROW_COUNT;
        float textHeight = mLayout.getDateTextHeight();
        float fromX;
        float toX;
        float fromY = textHeight;
        float toY = mLayout.getContainerHeight();
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
//        // 横向元素
        for (int i = 0; i <= rowCount; i++) {
            fromY = i * rowHeight + textHeight;
            toY = fromY;
            if (i % TempViewLayout.GRAD_STEP == 0) {
                // 横线-网络分割线和刻度标签
                if (mLayout.getGradLabels()[i / TempViewLayout.GRAD_STEP].length() > 0) {
                    c.drawText(mLayout.getGradLabels()[i / TempViewLayout.GRAD_STEP],
                            toX - TempViewLayout.PRE_SHOW_DAYS * columnWidth + mTempAtrrs.gradTextLeftMargin,
                            toY, gradLabelPaint);
                    gridLinePaint.setColor(mTempAtrrs.dividerLineColor);
                } else {
                    gridLinePaint.setColor(mTempAtrrs.gridLineColor);
                }
            } else {
                // 横线-网络线
                gridLinePaint.setColor(mTempAtrrs.gridLineColor); // 横线
            }
            c.drawLine(fromX, fromY, toX, toY, gridLinePaint);
        }
        // 温度计标线-红色竖线
        gridLinePaint.setColor(mTempAtrrs.thermometerLineColor);
        float thermometerLineX = mLayout.getContainerWidth() - TempViewLayout.PRE_SHOW_DAYS * columnWidth;
        c.drawLine(thermometerLineX, textHeight, thermometerLineX, mLayout.getContainerHeight() + textHeight, gridLinePaint);
    }

    public void drawColorBackground(Canvas c) {

        Paint dateBg = new Paint();
        dateBg.setColor(Color.WHITE);
        c.drawRect(0, 0, mLayout.getContainerWidth(), mLayout.getDateTextHeight(), dateBg);
    }

    //绘制有效温度数据
    public void drawTemperatureLine(Canvas c) {
        float middleLine = mLayout.getMiddleGridPos();
        float maxOffset = (TempViewLayout.ROW_COUNT / TempViewLayout.GRAD_STEP + 1) / 2 * TempViewLayout.GRAD_STEP;
        float minOffset = TempViewLayout.ROW_COUNT - TempViewLayout.ROW_COUNT / 2;
        float lastX = Util.INVALIDE_VALUE;
        float lastY = Util.INVALIDE_VALUE;
        Paint tempDotPaint = new Paint();
        tempDotPaint.setColor(mTempAtrrs.tempLineColor);
        Paint tempLinePaint = new Paint();
        tempLinePaint.setAntiAlias(true);
        tempLinePaint.setStrokeWidth(mTempAtrrs.tempLineSize);
        tempLinePaint.setColor(mTempAtrrs.tempLineColor);

        float offsetX = (mLayout.getColumnWidth() - mScroller.getCurrX() % mLayout.getColumnWidth()) % mLayout.getColumnWidth();
        float xPos[] = new float[TempViewLayout.COLUMN_COUNT + 3];
        float yPos[] = new float[TempViewLayout.COLUMN_COUNT + 3];
        // 加2是因为左右两边各要多绘一个温度
        for (int i = 0; i <= TempViewLayout.COLUMN_COUNT + 2; i++) {
            int offset = (mTemperatures[i + mLayout.getVisibleStart()] - TempViewLayout.MIDDLE_GRAD); // 温度和中间线的偏移量

            // 超过记录范围最大值或者低于最小值都是无效数据
            if (Math.abs(offset) > maxOffset || Math.abs(offset) > minOffset) {
                lastX = Util.INVALIDE_VALUE;
                lastY = Util.INVALIDE_VALUE;
            } else {
                float x = offsetX + mLayout.getContainerWidth() - mLayout.getColumnWidth() * (i - 1);//减1是因为右边要多绘制一个温度
                float y = middleLine - offset * mLayout.getRowHeight();
                // 只绘制有效温度数据
                if (lastX != Util.INVALIDE_VALUE && lastY != Util.INVALIDE_VALUE) {
                    c.drawLine(x, y, lastX, lastY, tempLinePaint);
                }
                xPos[i] = x;
                yPos[i] = y;
                lastX = x;
                lastY = y;
            }
        }
        //绘制有效温度点
        for (int i = 0; i <= TempViewLayout.COLUMN_COUNT + 2; i++) {
            if (yPos[i] > 0.0f) {
                tempDotPaint.setColor(Color.WHITE);
                c.drawCircle(xPos[i], yPos[i], 15, tempDotPaint);
                tempDotPaint.setColor(mTempAtrrs.tempLineColor);
                c.drawCircle(xPos[i], yPos[i], 10, tempDotPaint);
                tempDotPaint.setColor(Color.WHITE);
                c.drawCircle(xPos[i], yPos[i], 5, tempDotPaint);
            }
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
            float scrollLimit = -mLayout.getLengthLimit();
            if (scrollLimit == 0)
                return false;
            float velocity = velocityX;
            int startX = mScroller.getCurrX();
            mScroller.fling(startX, 0, (int) -velocity, 0, (int) scrollLimit, 0, 0, 0, 0, 0);
            invalidate();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
            int distance = Math.round(distanceX);
            int currPosition = mScroller.getCurrX();
            int finalPosition = mScroller.isFinished() ? currPosition : mScroller.getFinalX();
            int newPosition = Util.clamp(finalPosition + distance, (int) -mLayout.getLengthLimit(), 0);
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
