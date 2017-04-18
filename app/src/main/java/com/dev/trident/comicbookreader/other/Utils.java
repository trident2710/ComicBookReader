package com.dev.trident.comicbookreader.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * trident 25.02.16.
 */
public final class Utils {


    public interface BinaryActionListener{
        void onActionChosen(DialogInterface dialogInterface, boolean isAllowed);
    }

    public static void showBinaryActionDialog(Context context, int titleResource, int messageResource, int positiveActionResource, int negativeActionResource, final BinaryActionListener listener){
        new AlertDialog.Builder(context)
                .setTitle(titleResource)
                .setMessage(messageResource)
                .setCancelable(false)
                .setPositiveButton(positiveActionResource, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onActionChosen(dialog, true);
                    }
                })
                .setNegativeButton(negativeActionResource, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onActionChosen(dialog, false);
                    }
                })
                .create().show();
    }

    /**
     * Temporary lock screen from being rotated.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void setScreenOrientationLocked(Activity activity,boolean isShouldBeLocked){
        if(isShouldBeLocked){
            Display display = activity.getWindowManager().getDefaultDisplay();
            int rotation = display.getRotation();
            int height;
            int width;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
                height = display.getHeight();
                width = display.getWidth();
            } else {
                Point size = new Point();
                display.getSize(size);
                height = size.y;
                width = size.x;
            }
            switch (rotation) {
                case Surface.ROTATION_90:
                    if (width > height)
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    else
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    break;
                case Surface.ROTATION_180:
                    if (height > width)
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    else
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
                case Surface.ROTATION_270:
                    if (width > height)
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    else
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                default :
                    if (height > width)
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    else
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

    }

    /**
     * Check if the app has permission for the action
     * @param context
     * @return
     */
    public static boolean doesUserHavePermission(Context context,String permission)
    {
        int result = context.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isImage(String filename) {
        return filename.toLowerCase().matches(".*\\.(jpg|jpeg|bmp|gif|png|webp)$");
    }

    public static boolean isZip(String filename) {
        return filename.toLowerCase().matches(".*\\.(zip|cbz)$");
    }

    public static boolean isRar(String filename) {
        return filename.toLowerCase().matches(".*\\.(rar|cbr)$");
    }

    public static String MD5(String string) {
        try {
            byte[] strBytes = string.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(strBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException e) {
            return string.replace("/", ".");
        }
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static int getScreenDpWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(displayMetrics.widthPixels / displayMetrics.density);
    }

    public static boolean isIceCreamSandwitchOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean isHoneycombOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isHoneycombMR1orLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean isJellyBeanMR1orLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLollipopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static int getHeapSize(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean isLargeHeap = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (isLargeHeap && Utils.isHoneycombOrLater()) {
            memoryClass = am.getLargeMemoryClass();
        }
        return 1024 * memoryClass;
    }

    public static int calculateBitmapSize(Bitmap bitmap) {
        int sizeInBytes;
        if (Utils.isHoneycombMR1orLater()) {
            sizeInBytes = bitmap.getByteCount();
        }
        else {
            sizeInBytes = bitmap.getRowBytes() * bitmap.getHeight();
        }
        return sizeInBytes / 1024;
    }



    public static boolean isTarball(String filename) {
        return filename.toLowerCase().matches(".*\\.(cbt)$");
    }

    public static boolean isSevenZ(String filename) {
        return filename.toLowerCase().matches(".*\\.(cb7|7z)$");
    }

    public static boolean isArchive(String filename) {
        return isZip(filename) || isRar(filename) || isTarball(filename) || isSevenZ(filename);
    }

    public static int getDeviceWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(displayMetrics.widthPixels / displayMetrics.density);
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(displayMetrics.heightPixels / displayMetrics.density);
    }



    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int calculateMemorySize(Context context, int percentage) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
        int memoryClass = activityManager.getLargeMemoryClass();
        return 1024 * 1024 * memoryClass / percentage;
    }

    public static File getCacheFile(Context context, String identifier) {
        return new File(context.getExternalCacheDir(), Utils.MD5(identifier));
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }


}
