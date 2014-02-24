package com.allthelucky.examples.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "freqcity.db";
    private static final int DB_VER = 1;
    private static final String TABLE_CREATE = "CREATE TABLE freqcity(cityId integer primary key, name varchar(20),enName varchar(20), prefixLetter varchar(20), latitude varchar(20), longitude varchar(20), num integer)";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // 创建，添加默认数据
        db.execSQL(TABLE_CREATE);
        db.execSQL("insert into freqcity(cityId, name, enName,prefixLetter,latitude, longitude, num) values(?,?,?,?,?,?,?)", new Object[] { "192", "武汉", "wuhan", "W", "30.581084", "114.3162", "1" });
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
