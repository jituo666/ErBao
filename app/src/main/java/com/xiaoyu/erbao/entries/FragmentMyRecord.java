package com.xiaoyu.erbao.entries;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.adapters.CalendarAdapter;
import com.xiaoyu.erbao.cctrls.SlidingLayer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jituo on 15/12/18.
 */
public class FragmentMyRecord extends Fragment implements View.OnClickListener {


    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private GestureDetector mGestureDetector = null;
    private CalendarAdapter mCalendarAdapter = null;
    private ViewFlipper mFlipper = null;
    private GridView mGridView = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String mCurrentDate = "";
    private int gvFlag = 0;
//
    private TextView mCurrentMonth;
    private ImageView mBackToady;
    private ImageView mEditInfo;
    private ImageView mPrevMonth;
    private ImageView mNextMonth;
    private SlidingLayer mDetailLayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mCurrentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(mCurrentDate.split("-")[0]);
        month_c = Integer.parseInt(mCurrentDate.split("-")[1]);
        day_c = Integer.parseInt(mCurrentDate.split("-")[2]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_record, null);
        mCurrentMonth = (TextView) rootView.findViewById(R.id.current_month);
        mPrevMonth = (ImageView) rootView.findViewById(R.id.previous_month);
        mNextMonth = (ImageView) rootView.findViewById(R.id.next_month);
        mBackToady = (ImageView) rootView.findViewById(R.id.back_today);
        mEditInfo = (ImageView) rootView.findViewById(R.id.edit_info);

        mPrevMonth.setOnClickListener(this);
        mNextMonth.setOnClickListener(this);
        mBackToady.setOnClickListener(this);
        mEditInfo.setOnClickListener(this);

        mGestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
        mFlipper = (ViewFlipper) rootView.findViewById(R.id.flipper);
        mFlipper.removeAllViews();
        mCalendarAdapter = new CalendarAdapter(getActivity(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        addGridView();
        mGridView.setAdapter(mCalendarAdapter);
        mFlipper.addView(mGridView, 0);
        addTextToTopTextView(mCurrentMonth);

        mDetailLayer = (SlidingLayer)rootView.findViewById(R.id.slidingLayer1);
        mDetailLayer.setTopNeihorView(rootView,rootView.findViewById(R.id.record_calendar));
        mDetailLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        mDetailLayer.setChangeStateOnTap(false);
        return rootView;
    }


    private class MyGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
                enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }


    private void enterNextMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月

        mCalendarAdapter = new CalendarAdapter(getActivity(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        mGridView.setAdapter(mCalendarAdapter);
        addTextToTopTextView(mCurrentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        mFlipper.addView(mGridView, gvFlag);
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));
        mFlipper.showNext();
        mFlipper.removeViewAt(0);
    }


    private void enterPrevMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月

        mCalendarAdapter = new CalendarAdapter(getActivity(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        mGridView.setAdapter(mCalendarAdapter);
        gvFlag++;
        addTextToTopTextView(mCurrentMonth); // 移动到上一月后，将当月显示在头标题中
        mFlipper.addView(mGridView, gvFlag);

        mFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out));
        mFlipper.showPrevious();
        mFlipper.removeViewAt(0);
    }

    public void addTextToTopTextView(TextView view) {
        view.setText(String.format("%s/%02d",mCalendarAdapter.getShowYear(),Integer.valueOf(mCalendarAdapter.getShowMonth())));
    }

    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mGridView = new GridView(getActivity());
        mGridView.setNumColumns(7);
        mGridView.setGravity(Gravity.CENTER_VERTICAL);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        mGridView.setVerticalSpacing(1);
        mGridView.setHorizontalSpacing(1);
        mGridView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = mCalendarAdapter.getStartPositon();
                int endPosition = mCalendarAdapter.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    String scheduleDay = mCalendarAdapter.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
                    // String scheduleLunarDay =
                    // mCalendarAdapter.getDateByClickItem(position).split("\\.")[1];
                    // //这一天的阴历
                    String scheduleYear = mCalendarAdapter.getShowYear();
                    String scheduleMonth = mCalendarAdapter.getShowMonth();
                    Toast.makeText(getActivity(), scheduleYear + "-" + scheduleMonth + "-" + scheduleDay, Toast.LENGTH_LONG).show();
                }
            }
        });
        mGridView.setLayoutParams(params);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_month:
                enterNextMonth(gvFlag);
                break;
            case R.id.previous_month:
                enterPrevMonth(gvFlag);
                break;
            case R.id.back_today:
                //
                break;
            case R.id.edit_info:
                //
                break;
            default:
                break;
        }
    }
}
