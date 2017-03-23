package com.lendingkart.instant.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Andy on 2/17/2016.
 */
public class Utils {

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String clientVersion;

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches() && phoneNumber.length() == 10;
    }

    public static boolean isValidPin(String pin) {
        return pin.length() == 4;
    }

    public static int getDisplayWidth(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    public static int getDisplayHeight(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    public static String getDeviceID(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {

            String deviceID = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String deviceManufacturer = Build.MANUFACTURER;
            deviceManufacturer = deviceManufacturer.replaceAll("\\s", "");
            String deviceModel = Build.MODEL;

            androidId = deviceManufacturer + "-" + deviceModel + "-" + deviceID;

/*
            androidId = "6a7fe2c6a4277cd6";
*/
        }

        if (TextUtils.isEmpty(androidId))
            androidId = "PWPAndroid";

        return androidId;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 4.0
     * <p/>
     * Uses static final constants to detect if the device's platform version is
     * ICS or later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 4.0
     * <p/>
     * Uses static final constants to detect if the device's platform version is
     * ICS or later.
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 4.1
     * <p/>
     * Uses static final constants to detect if the device's platform version is
     * JELLYBEAN or later.
     */
    public static boolean hasJELLYBEAN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 4.2
     * <p/>
     * Uses static final constants to detect if the device's platform version is
     * JELLYBEAN or later.
     */
    public static boolean hasJELLYBEAN_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 5.0
     * <p/>
     * Uses static final constants to detect if the device's platform version is
     * LOLLIPOP or later.
     */
    public static boolean hasLOLLYPOP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 6.0
     * <p/>
     * Uses static final constants to detect if the device's platform version is
     * LOLLIPOP or later.
     */
    public static boolean hasMarshMellow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static boolean isAppRunning(Context context) {
        if (isForeground(context)){
            return true;
        }
            return false;
    }

    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }


        return true;
    }

    /***
     * Checking Whether any Activity of Application is running or not
     * @param context
     * @return
     */
    public static boolean isForeground(Context context) {

        // Get the Activity Manager
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        // Get a list of running tasks, we are only interested in the last one,
        // the top most so we give a 1 as parameter so we only get the topmost.
        List<ActivityManager.RunningAppProcessInfo> task = manager.getRunningAppProcesses();

        // Get the info we need for comparison.
        ComponentName componentInfo = task.get(0).importanceReasonComponent;

        // Check if it matches our package name.
        if (componentInfo.getPackageName().equals(context.getPackageName()))
            return true;

        // If not then our app is not on the foreground.
        return false;
    }


    public static String getDeviceDetails(Context applicationContext) {
        JSONObject deviceDetailJson = new JSONObject();
        try {
            deviceDetailJson.put("make", Build.MANUFACTURER);
            deviceDetailJson.put("model", Build.MODEL);
            deviceDetailJson.put("device_id", getDeviceID(applicationContext));
            deviceDetailJson.put("imei", getDeviceImei(applicationContext));
            deviceDetailJson.put("android_version",
                    Build.VERSION.RELEASE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deviceDetailJson.toString();
    }

    public static String getDeviceImei(Context applicationContext) {
        TelephonyManager tManager = (TelephonyManager) applicationContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    public static void setPaddingInDp(View view, Context context, float left, float top, float right, float bottom) {
        int leftinPx = (int) convertDpToPixel(left, context);
        int topInPx = (int) convertDpToPixel(top, context);
        int rightInPx = (int) convertDpToPixel(right, context);
        int bottomInPx = (int) convertDpToPixel(bottom, context);

        view.setPadding(leftinPx, topInPx, rightInPx, bottomInPx);
    }

    public static boolean isAPILevel21AndAbove() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isNfcAvailable(Context context) {
        PackageManager pm = context.getPackageManager();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (pm.hasSystemFeature(PackageManager.FEATURE_NFC) || nfcAdapter != null) {
            return true;
        }

        return false;
    }

    public static String getAppVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        return pInfo.versionName;
    }


}
