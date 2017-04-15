package com.dev.trident.comicbookreader.other;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Surface;

/**
 * trident 25.02.16.
 */
public class Utils {


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


}
