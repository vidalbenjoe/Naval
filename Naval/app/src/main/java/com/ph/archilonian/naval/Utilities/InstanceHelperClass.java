package com.ph.archilonian.naval.Utilities;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by luigigo on 8/28/16.
 */
public class InstanceHelperClass {

    public static AlertDialog.Builder mAlertDialogBuilder;

    public static AlertDialog.Builder getAlertDialogBuilderInstance(Context context) {
        if (mAlertDialogBuilder == null) {
            return mAlertDialogBuilder = new AlertDialog.Builder(context);
        }

        return mAlertDialogBuilder;

    }


}
