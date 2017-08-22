package com.marssoft.utils.lib.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;

/**
 * Created by Alexey Sidorenko on 07-Jun-16.
 *
 * we should implement onRequestPermissionsResult
 *  @Override
 *   public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
 *       PermissionsHelper.getInstance(this).onRequestPermissionsResult(this, requestCode, permissions, grantResults);
 *   }
 *
 */
public class PermissionsHelper {
    private final Context mContext;

    public interface PermissionListener {
        void onPermissionGranted(int requestCode);
    }

    private static final String TAG = PermissionsHelper.class.getSimpleName();

    private static PermissionsHelper instance;

    public synchronized static PermissionsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PermissionsHelper(context.getApplicationContext());
        }
        return instance;
    }

    private PermissionsHelper(Context context) {
        mContext = context;
    }

    public void checkPermission(final Activity activity, final String permission,
                                int titleRes, int messageRes, final int action) {
        PermissionListener listener;
        try {
            listener = (PermissionListener) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException(activity.getClass().getSimpleName() + " should implement PermissionListener");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(titleRes);
                builder.setMessage(messageRes);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        activity.requestPermissions(new String[]{permission},
                                action);
                    }

                });
                builder.show();
            } else {
                listener.onPermissionGranted(action);
            }
        } else {
            listener.onPermissionGranted(action);
        }
    }

    public void onRequestPermissionsResult(PermissionListener listener, int requestCode, String permissions[], int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            listener.onPermissionGranted(requestCode);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Functionality limited");
            builder.setMessage("Since access has not been granted, this app will not be fully functional.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    System.exit(0);
                }
            });
            builder.show();
        }
    }
}
