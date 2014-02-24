package com.allthelucky.examples.common;

import java.lang.Thread.UncaughtExceptionHandler;

import android.util.Log;

/**
 * 未捕捉到异常处理
 */
public class ExceptionManager {
    private static final String TAG = "ExceptionManager";

    /**
     * 在应用Application中进行初始化
     */
    public static void init() {
        final CrashHandler crashHandler = new CrashHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    /**
     * 未catch到的异常处理Handler
     */
    final static class CrashHandler implements UncaughtExceptionHandler {

        public CrashHandler() {

        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            final StackTraceElement ste = thread.getStackTrace()[2];
            final String errorloc = "CLASS:" + ste.getClassName() + " METHOD:" + ste.getMethodName() + " LINE:"
                    + ste.getLineNumber();
            Log.d(TAG, "==========异常LOG开始:==========\n" + errorloc);
        }

        String getStackMessage(Throwable ex) {
            StringBuffer expSb = new StringBuffer();
            expSb.append(ex.getLocalizedMessage() + "\n");
            StackTraceElement[] elements = ex.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                expSb.append(elements[i].toString() + "\n");
            }
            return expSb.toString();
        }
    }
}
