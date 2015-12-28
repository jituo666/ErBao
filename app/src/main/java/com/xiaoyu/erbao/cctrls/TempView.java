package com.xiaoyu.erbao.cctrls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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
    private int mTemperatures[] = new int[TempViewLayout.SHOW_DAYS];

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
        //
        mTemperatures[0] = 0;
        mTemperatures[1] = 0;
        for (int i = 2; i < mTemperatures.length; i++) {
            mTemperatures[i] = 355 + new Random().nextInt(20);
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
        drawTempratureLine(canvas);
        if (more) {
            invalidate();
        } else if (!mTouchDown) {
            int offset = currentPosition % mLayout.getmColumnWidth();
            if (offset != 0) {
                if (Math.abs(offset) >= mLayout.getmColumnWidth() / 2) {//进
                    int dx = mLayout.getmColumnWidth() - Math.abs(offset);
                    mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), offset > 0 ? dx : -dx, 0);
                } else {//退
                    mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), -offset, 0);
                }
                invalidate();
            } else {
                //Log.i("xxdd",mLayout.getVisibleEnd() + " ---- " + mLayout.getVisibleStart());
                mScrollListener.onScrollTo(mLayout.getVisibleStart());
            }
        }
    }

    private void drawGridBackground(Canvas c) {
        Paint gridLinePaint = new Paint();
        gridLinePaint.setColor(mTempAtrrs.gridLineColor);
        gridLinePaint.setStrokeWidth(mTempAtrrs.gridLineSize);
        //
        Paint datePaint = new Paint();
        datePaint.setColor(mTempAtrrs.dateTextColor);
        datePaint.setTextSize(mTempAtrrs.dateTextSize);
        int offsetX = mLayout.getmColumnWidth() - mScroller.getCurrX() % mLayout.getmColumnWidth();
        int columnCount = mLayout.getColumnCount()+1;
        int rowCount = mLayout.getRowCount() + 1;
        for (int i = columnCount; i >= 0; i--) {//竖线
            int fromX = offsetX + mLayout.getContainerWidth() - (i) * mLayout.getmColumnWidth();
            int fromY = mLayout.getMarginY();
            int toX = fromX;
            int toY = mLayout.getContainerHeight() + mLayout.getMarginY();
            c.drawLine(fromX, fromY, toX, toY, gridLinePaint);
            String text = mDateFormat.format(new Date(System.currentTimeMillis() - (mLayout.getVisibleStart() + i - TempViewLayout.PRE_SHOW_DAYS-1) * Util.DAY_IN_MILLSECONDS));
            c.drawText(text, fromX - mLayout.getmColumnWidth() / 2, fromY - mTempAtrrs.gradTextLeftMargin / 2, datePaint);
        }
        //
        Paint gradTextPaint = new Paint();
        gradTextPaint.setColor(mTempAtrrs.gradTextColor);
        gradTextPaint.setTextSize(mTempAtrrs.gradTextSize);
        for (int i = 0; i < rowCount; i++) {//横线

            int fromX = 0;
            int fromY = i * mLayout.getRowHeight() + mLayout.getMarginY();
            int toX = mLayout.getContainerWidth();
            int toY = fromY;
            if (i % 5 == 0) {
                c.drawText(mLayout.getGradLabels()[i / 5], toX - 2 * mLayout.getmColumnWidth() + mTempAtrrs.gradTextLeftMargin, toY, gradTextPaint);
                if (mLayout.getGradLabels()[i / 5].length() > 0) {
                    gridLinePaint.setColor(mTempAtrrs.dividerLineColor);
                } else {
                    gridLinePaint.setColor(mTempAtrrs.gridLineColor);
                }
            } else {
                gridLinePaint.setColor(mTempAtrrs.gridLineColor);
            }
            c.drawLine(fromX, fromY, toX, toY, gridLinePaint);
        }
        gridLinePaint.setColor(mTempAtrrs.thermometerLineColor);
        int thermometerLineX = mLayout.getContainerWidth() - 2 * mLayout.getmColumnWidth();
        c.drawLine(thermometerLineX, mLayout.getMarginY(), thermometerLineX, mLayout.getContainerHeight() + mLayout.getMarginY(), gridLinePaint);
    }

    public void drawTempratureLine(Canvas c) {
        int middleLine = mLayout.getMiddleGridPos();
        int maxOffset = (mLayout.getRowCount()/5+1)/2 * 5;
        int minOffset =  mLayout.getRowCount()-mLayout.getRowCount()/2;
        boolean lastDrawn = false;

        Paint tempPaint = new Paint();
        tempPaint.setColor(mTempAtrrs.tempLineColor);

        Log.i("xxdd",mLayout.getVisibleStart() + "----------------------------begin:" + mLayout.getVisibleEnd());
        int offsetX = mLayout.getmColumnWidth() - mScroller.getCurrX() % mLayout.getmColumnWidth();
        for (int i = 0; i <= mLayout.getColumnCount(); i++) {
            int x = 0,y = 0;
            int offset = (mTemperatures[i + mLayout.getVisibleStart()] - 365);
            if (Math.abs(offset) > maxOffset || Math.abs(offset) > minOffset) {
                lastDrawn = false;
            } else {
                lastDrawn= true;
                x = offsetX + mLayout.getContainerWidth() - mLayout.getmColumnWidth()*(i+1);
                y = middleLine-offset * mLayout.getRowHeight();
                c.drawCircle(x,y,12,tempPaint);
            }

            Log.i("xxdd","----------------------------tem " + mTemperatures[i] + " drawn :" + lastDrawn
             + " x:" + x + " y :" + y);
//            Log.i("xxdd", " maxoffset: " + maxOffset +
//                    " minoffset: " + minOffset + " mTempratures[i]: " + mTempratures[i] +
//             " lastDrawn: " + lastDrawn);
        }

        Log.i("xxdd","----------------------------end");
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
