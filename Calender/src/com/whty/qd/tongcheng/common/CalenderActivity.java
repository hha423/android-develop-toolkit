package com.whty.qd.tongcheng.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 日历选择控件, DAY_NUM为0或负数时，当前及以后日期可任选，当DAY_NUM为正数时，表示当前DAY_NUM天后可选择。
 * 
 * @author pxw
 * 
 */
public class CalenderActivity extends Activity {
    private static final int DAY_NUM = 60; // 可选择天数
    public static final int RESULT_CODE = 100;
    private View preMonth;
    private View nextMonth;
    private TextView dateText;

    private GridView weekView;
    private GridView dateView;

    private int year;
    private int month;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.com_cal_layout);
        intView();
        intData();
    }

    private void intView() {
        this.weekView = (GridView) findViewById(R.id.gridView1);
        this.dateView = (GridView) findViewById(R.id.gridView2);

        this.preMonth = findViewById(R.id.premonth_button);
        this.nextMonth = findViewById(R.id.nextmonth_button);
        this.dateText = (TextView) findViewById(R.id.date_text);

        this.dateView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DateItem item = (DateItem) parent.getAdapter().getItem(position);
                
                Intent intent = new Intent();
                intent.putExtra("MONTH", CalendarUtils.getMonthString(item.month));
                intent.putExtra("DAY", CalendarUtils.getDayString(item.day));
                intent.putExtra("WEEK", CalendarUtils.getWeekString(item.week));
                intent.putExtra("DATE", CalendarUtils.getDateString(item));

                setResult(RESULT_CODE, intent);
                finish();
            }
        });

        this.preMonth.setOnClickListener(l);
        this.nextMonth.setOnClickListener(l);
    }

    private void intData() {
        Calendar cal = CalendarUtils.getCalendar();
        this.year = CalendarUtils.getYear(cal);
        this.month = CalendarUtils.getMonth(cal);

        this.dateText.setText(year + "年" + CalendarUtils.getMonthString(month) + "月");
        this.weekView.setAdapter(new WeekAdapter(this, CalendarUtils.getWeekDays()));
        this.dateView.setAdapter(new DateAdapter(this, CalendarUtils.getMonth(year, month, DAY_NUM)));
    }

    private OnClickListener l = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (preMonth == v) {
                if (month == 0) {
                    year--;
                    month = 11;
                } else {
                    month--;
                }
            } else if (nextMonth == v) {
                if (month == 11) {
                    year++;
                    month = 0;
                } else {
                    month++;
                }
            }
            handler.sendEmptyMessage(0);
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            refreshDate();
        }
    };

    private void refreshDate() {
        this.dateText.setText(year + "年" + CalendarUtils.getMonthString(month) + "月");
        this.dateView.setAdapter(new DateAdapter(this, CalendarUtils.getMonth(year, month, DAY_NUM)));
    }

    class WeekAdapter extends ArrayAdapter<String> {
        Context context;

        public WeekAdapter(Context context, List<String> list) {
            super(context, 0, list);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.com_cal_week_item, null);
            tv.setText(getItem(position));
            return tv;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

    class DateAdapter extends ArrayAdapter<DateItem> {
        Context context;
        int oldColer = 0xFFF8F8F8;
        int evenColer = 0xFFEEEEEE;
        int disableColer = 0xFF999999;

        public DateAdapter(Context context, List<DateItem> list) {
            super(context, 0, list);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.com_cal_date_item, null);
            TextView tv = (TextView) view.findViewById(R.id.day_text);
            TextView note = (TextView) view.findViewById(R.id.note_text);
            
            int temp = position;
            if(temp>6 && (temp/7)%2!=0) {
                temp= temp-1;
            }
            if (temp % 2 == 0) {
                view.setBackgroundColor(oldColer);
            } else {
                view.setBackgroundColor(evenColer);
            }
            DateItem item = getItem(position);
            if (!item.isEnable()) {
                tv.setTextColor(disableColer);
            }
         
            if (CalendarUtils.isToday(item)) {
                view.setBackgroundResource(R.drawable.calendar_bt02);   //tv.setBackgroundColor(0xff7db717);
                tv.setTextColor(0xffffffff);
                note.setText("今天");
            } else {
                note.setText("");
            }
            final String day = new StringBuffer().append(getItem(position).getDay()).toString();
            tv.setText(day);
            return view;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItem(position).isEnable();
        }
    }

    /**
     * 单条日期显示条目信息类
     * 
     * @author pxw
     * 
     */
    public static class DateItem implements Serializable {
        private static final long serialVersionUID = -631655386111015284L;
        private int year;
        private int month;
        private int day;
        private int week;
        private boolean enable;

        public DateItem(Calendar cal, int dayNum) {
            this.year = CalendarUtils.getYear(cal);
            this.month = CalendarUtils.getMonth(cal);
            this.day = CalendarUtils.getDay(cal);
            this.week = CalendarUtils.getWeek(cal);

            setEnable(cal, dayNum);
        }

        /**
         * 当前日期之前及60天以后不可选
         * 
         * @param cal
         * @param iYear
         * @param dayOfYear
         */
        private void setEnable(Calendar cal, int dayNum) {
            Calendar ic = CalendarUtils.getCalendar();
            int iYear = CalendarUtils.getYear(ic);
            int iMaxDays = CalendarUtils.getMaxDaysOfYear(ic);
            int iDayOfYear = CalendarUtils.getDayOfYear(ic);

            boolean flag = false;
            if (year > iYear) {
                if (dayNum > 0) {
                    int remain = iMaxDays + CalendarUtils.getDayOfYear(cal) - iDayOfYear;
                    if (remain < dayNum) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                } else {// 无天数要求
                    flag = true;
                }
            } else if (year == iYear) {
                int cur = CalendarUtils.getDayOfYear(cal);
                if (cur >= iDayOfYear) {
                    if (dayNum > 0) {
                        if ((cur - iDayOfYear) < dayNum) {// 要求dayNum天
                            flag = true;
                        } else {
                            flag = false;
                        }
                    } else {
                        flag = true;
                    }
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }
            this.enable = flag;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }

        public int getWeek() {
            return week;
        }

        public boolean isEnable() {
            return enable;
        }

        @Override
        public String toString() {
            return "year=" + getYear() + ",month=" + getMonth() + ",day=" + getDay() + ",week=" + getWeek() + ",enable=" + enable;
        }
    }

}
