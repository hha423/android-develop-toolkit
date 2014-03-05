package com.whty.qd.tongcheng.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.whty.qd.tongcheng.common.CalenderActivity.DateItem;

/**
 * 日历数据生成工具类
 * 
 * @author pxw
 * 
 */
public class CalendarUtils {
    private final static String weekDays[] = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

    private CalendarUtils() {

    }

    /**
     * 取当前Calendar变量
     * 
     * @return Calendar实例
     */
    public static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(new Date());
        return cal;
    }

    /**
     * 取年份
     * 
     * @param cal
     * @return
     */
    public static int getYear(Calendar cal) {
        return cal.get(Calendar.YEAR);
    }

    /**
     * 取月份，1月为0
     * 
     * @param cal
     * @return
     */
    public static int getMonth(Calendar cal) {
        return cal.get(Calendar.MONTH);
    }

    /**
     * 取一个月第几天，每月1号为1
     * 
     * @param cal
     * @return
     */
    public static int getDay(Calendar cal) {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取星期，星期天为1
     * 
     * @param cal
     * @return
     */
    public static int getWeek(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 取一年中第几天
     * 
     * @param cal
     * @return
     */
    public static int getDayOfYear(Calendar cal) {
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 取当前年份的总天娄
     */
    public static int getMaxDaysOfYear(Calendar cal) {
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 星期值
     * 
     * @return
     */
    public static List<String> getWeekDays() {
        final List<String> list = new ArrayList<String>();
        final int len = weekDays.length;

        for (int i = 0; i < len; i++) {
            list.add(weekDays[i]);
        }
        return list;
    }

    /**
     * 取某年某月显示数据，两头视情况添加
     * 
     * @param year
     * @param month
     * @param dayNum
     * @return
     */
    public static List<DateItem> getMonth(int year, int month, int dayNum) {
        final List<DateItem> list = new ArrayList<DateItem>();

        List<DateItem> header = getHeader(year, month, dayNum);
        int len = header.size();
        if (len > 0) {
            for (int i = len - 1; i >= 0; i--) {
                list.add(header.get(i));
            }
        }

        final Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month); // Calendar.JANUARY=0
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= days; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            addToList(list, cal, dayNum);
        }

        int remain = 42 - list.size();
        if (remain > 0) {
            int loop = 0;
            if (remain == 7) {// 不追加
                loop = 0;
            } else if (remain < 7) {// 余下全追加
                loop = remain;
            } else { // 仅追加一行
                loop = remain - 7;
            }

            if (loop > 0) {
                if (getMonth(cal) == 11) {
                    cal.roll(Calendar.YEAR, 1);
                }
                for (int i = 0; i < loop; i++) {
                    cal.roll(Calendar.DAY_OF_YEAR, 1);
                    addToList(list, cal, dayNum);
                }
            }
        }
        return list;
    }

    /**
     * 取月初不足的数据
     * 
     * @param year
     * @param month
     * @param dayNum
     * @return
     */
    private static List<DateItem> getHeader(int year, int month, int dayNum) {
        List<DateItem> header = new ArrayList<DateItem>();
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        final int first = getWeek(cal);
        if (first > 0) {
            if (getMonth(cal) == 0) {
                cal.roll(Calendar.YEAR, -1);
            }
            for (int i = 0; i < first - 1; i++) {
                cal.roll(Calendar.DAY_OF_YEAR, -1);
                addToList(header, cal, dayNum);
            }
        }
        return header;
    }

    /**
     * 添加某条数据到列表
     * 
     * @param list
     * @param cal
     * @param dayNum
     */
    private static void addToList(List<DateItem> list, Calendar cal, int dayNum) {
        DateItem item = new DateItem(cal, dayNum);
        list.add(item);
    }

    /**
     * 是否当前
     * 
     * @param item
     * @return
     */
    public static boolean isToday(DateItem item) {
        Calendar cal = CalendarUtils.getCalendar();
        if (item.getYear() == CalendarUtils.getYear(cal) && item.getMonth() == CalendarUtils.getMonth(cal)
                && item.getDay() == CalendarUtils.getDay(cal)) {
            return true;
        }
        return false;
    }

    /**
     * 取月份字符串
     * 
     * @param month
     * @return
     */
    public static String getMonthString(int month) {
        int m = month + 1;
        if (m < 10) {
            return new String("0" + m);
        } else {
            return new Integer(m).toString();
        }
    }

    public static String getDayString(int day) {
        if (day < 10) {
            return new String("0" + day);
        } else {
            return new Integer(day).toString();
        }
    }

    public static String getWeekString(int week) {
        String weekName = "";
        switch (week) {
        case 1:
            weekName = "周日";
            break;
        case 2:
            weekName = "周一";
            break;
        case 3:
            weekName = "周二";
            break;
        case 4:
            weekName = "周三";
            break;
        case 5:
            weekName = "周四";
            break;
        case 6:
            weekName = "周五";
            break;
        case 7:
            weekName = "周六";
            break;
        default:
            break;
        }
        return weekName;
    }

    public static String getDateString(DateItem item) {
        StringBuffer sb = new StringBuffer();
        sb.append(item.getYear());
        sb.append("-");
        sb.append(getMonthString(item.getMonth()));
        sb.append("-");
        sb.append(getDayString(item.getDay()));
        
        return sb.toString();
    }

}
