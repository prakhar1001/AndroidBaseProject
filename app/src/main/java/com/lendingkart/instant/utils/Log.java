package com.lendingkart.instant.utils;


import com.lendingkart.instant.network.UrlConstants;

public class Log {
    public static void d(String TAG, String message) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.d(TAG, message);
        }
    }

    public static void d(String TAG, String message, Throwable ex) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.d(TAG, message, ex);
        }
    }

    public static void e(String TAG, String message) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.e(TAG, message);
        }
    }

    public static void e(String TAG, String message, Throwable ex) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.e(TAG, message, ex);
        }
    }

    public static void w(String TAG, String message) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.w(TAG, message);
        }
    }

    public static void w(String TAG, String message, Throwable ex) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.w(TAG, message, ex);
        }
    }

    public static void i(String TAG, String message) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.i(TAG, message);
        }
    }

    public static void i(String TAG, String message, Throwable ex) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.i(TAG, message, ex);
        }
    }

    public static void v(String TAG, String message) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.v(TAG, message);
        }
    }

    public static void v(String TAG, String message, Throwable ex) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.v(TAG, message, ex);
        }
    }

    public static void wtf(String TAG, String message) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.wtf(TAG, message);
        }
    }

    public static void wtf(String TAG, String message, Throwable ex) {
        if (UrlConstants.DEBUG_MODE) {
            if (TAG == null || message == null) return;
            android.util.Log.wtf(TAG, message, ex);
        }
    }

}
