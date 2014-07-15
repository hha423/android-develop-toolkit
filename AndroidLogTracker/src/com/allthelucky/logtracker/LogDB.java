package com.allthelucky.logtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class LogDB extends SQLiteOpenHelper {

	private static final int DB_VER = 1;

	private static final String DB_NAME = "LOG_DB_FILE";

	private static final String LOG_TABLE = "LOG_DB_TABLE";

	public LogDB(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("create TABLE " + LOG_TABLE
				+ "(type varchar(2), message varchar(20), datetime varchar(20), account, )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
