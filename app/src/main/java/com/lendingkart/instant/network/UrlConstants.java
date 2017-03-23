package com.lendingkart.instant.network;

import com.lendingkart.instant.BuildConfig;

/**
 * Created by lendingkart on 3/23/2017.
 */

public class UrlConstants {

    public static boolean DEBUG_MODE = BuildConfig.DEBUG;
    public static boolean ACCEPT_ALL_CERTIFICATES = DEBUG_MODE;


    private static boolean isDevMode = false;

    private static final String BASE_URL_STAGING = "";
    private static final String BASE_URL_PRODUCTION = "";
    public static String getBaseUrl() {
        return (isDevMode) ? BASE_URL_STAGING : BASE_URL_PRODUCTION;
    }

    public static final boolean ENABLE_LOGS = true;



}
