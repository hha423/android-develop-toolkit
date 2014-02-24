package com.allthelucky.examples.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * 常住城市数据库管理类
 */
public class CityFreqService {
    private static final String TAG = "FreqCityService";
    private static CityFreqService INSTANCE = null;
    private DataBaseHelper helper;

    public CityFreqService(Context context) {
        helper = new DataBaseHelper(context);
    }

    public synchronized static CityFreqService newInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CityFreqService(context);
        }
        return INSTANCE;
    }

    private void add(CityItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into freqcity(cityId, name, enName,prefixLetter,latitude, longitude, num) values(?,?,?,?,?,?,?)", new Object[] { item.id, item.name, item.enName,
                item.prefixLetter, item.latitude, item.longitude, "1" });
    }

    /**
     * 更新城市数据，
     * 
     * @param item
     */
    public void update(CityItem item) {
        String ret = find(item.id);

        if (ret == null) { // 创建
            add(item);
        } else { // 有则更新num值
            int n = Integer.parseInt(ret) + 1;
            String num = "" + n;

            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("update freqcity set num=? where cityId=?", new Object[] { num, item.id });
        }
    }

    /**
     * 查询，若存在则返回其NUM
     * 
     * @param cityId
     * @return
     */
    private String find(String cityId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        String num = null;

        try {
            cursor = db.rawQuery("select * from freqcity where cityId=?", new String[] { cityId });
            if (cursor.moveToFirst()) {
                num = cursor.getString(cursor.getColumnIndex("num"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            num = null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return num;
    }

    /**
     * 取常住城市列表
     * 
     * @return
     */
    public List<CityItem> getCitys() {
        List<FreqCityItem> freqList = getFreqCitys();
        if (freqList != null && freqList.size() > 0) {
            Collections.sort(freqList); // sort
            return FreqCityItem.getList(freqList);
        } else {
            return new ArrayList<CityItem>();
        }
    }

    /**
     * 取常住城市(带num参数）列表
     * 
     * @return
     */
    private List<FreqCityItem> getFreqCitys() {
        List<FreqCityItem> ret = new ArrayList<FreqCityItem>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + "freqcity", null);
            if (cursor.moveToFirst()) {
                do {
                    CityItem item = new CityItem();
                    item.id = cursor.getString(0);
                    item.name = cursor.getString(1);
                    item.enName = cursor.getString(2);
                    item.prefixLetter = cursor.getString(3);
                    item.latitude = cursor.getString(4);
                    item.longitude = cursor.getString(5);

                    FreqCityItem freqItem = new FreqCityItem(item);
                    freqItem.num = cursor.getString(6);
                    ret.add(freqItem);
                } while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "getUsernamePassword", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return ret;
    }

    /**
     * 按城市被选频率num排序的Decorator类
     */
    static class FreqCityItem implements Comparable<FreqCityItem> {
        public CityItem item;
        public String num;

        private static final int FREQ_CIYS = 5; // 返回常住城市个数

        public FreqCityItem(CityItem item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "FreqCityItem [item=" + item + ", num=" + num + "]";
        }

        @Override
        public int compareTo(FreqCityItem another) { // 由大到小排列
            int thisnum = Integer.parseInt(this.num);
            int othernum = Integer.parseInt(another.num);
            if (thisnum > othernum) {
                return -1;
            } else if (thisnum == othernum) {
                return 0;
            } else {
                return 1;
            }
        }

        /**
         * 重组排序后的数据
         * 
         * @param freqList
         * @return
         */
        public static List<CityItem> getList(List<FreqCityItem> freqList) {
            List<CityItem> list = new ArrayList<CityItem>();
            int i = 1;
            for (FreqCityItem item : freqList) {
                if (i > FREQ_CIYS) {
                    break;
                }
                list.add(item.item);
                i++;
            }
            return list;
        }
    }

}
