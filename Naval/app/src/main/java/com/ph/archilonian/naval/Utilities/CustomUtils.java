package com.ph.archilonian.naval.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ph.archilonian.naval.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;

public class CustomUtils {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static ProgressDialog mProgressDialog;
    private static Context _context;
    static Dialog dialog;
    public static DecimalFormat currencyFormat;

    public CustomUtils(Context context) {
        _context = context;
    }

    /**
     * This method is used to get Hash key
     *
     * @param activity
     */
    public static void getFBHashKey(Activity activity) {
        //get Facebook hash key
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "com.ph.archilonian.naval",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    /**
     * This method is used for checking Alphanumeric characters :)
     *
     * @param edit, filter
     */
    public static void AlphaNumFilter(EditText edit, InputFilter filter) {
        filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    Character c = source.charAt(0);
                    if (Character.isLetter(c) || Character.isDigit(c)) {
                        return "" + Character.toUpperCase(c);
                    } else {
                        //not alphanumeric
                        Log.i("Alpha", "not alphanumeric");
                        return "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        edit.setFilters(new InputFilter[]{filter});
    }

    /**
     * Method is used for checking valid email id format.
     *
     * @return boolean true for valid false for invalid
     */

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * This method is used for converting Numerical value into Decimal Format
     *
     * @param context
     * @param data
     * @return
     */

    public static String decimalFormat(Context context, double data) {
        currencyFormat = new DecimalFormat("#,###.00");
        String currency = currencyFormat.format(data);
        return currency;
    }

    /**
     * Display Toast
     *
     * @param activity
     * @param message
     */
    public static void toastMessage(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Display progress dialog
     *
     * @param context
     */
    public static void showDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        // Set progressdialog title
        mProgressDialog.setTitle("Loading");
        // Set progressdialog message
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    /**
     * Hide Dialog
     */
    public static void hideDialog() {
        // Close the progressdialog
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        mProgressDialog.setCancelable(true);
//        mProgressDialog.cancel();
    }

    /**
     * This method is for checking available internet connection
     *
     * @return
     */
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * This method is used to shuffle the return of JSONArray
     *
     * @param array
     * @return
     * @throws JSONException
     */
    public static JSONArray shuffleJsonArray(JSONArray array) throws JSONException {
        // Implementing Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--) {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
        }
        return array;
    }


    /**
     * This method is used for managing fragment transaction
     *
     * @param fragment
     * @param addToBackStack
     * @param context
     */
    public static void loadFragment(final Fragment fragment, boolean addToBackStack, Context context) {
        final FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, fragment, fragment.getClass().getSimpleName());
        Log.i("CurrentFragment", fragment.getClass().getSimpleName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
//        CustomUtils.popBackStack(fragment);
        fragmentTransaction.commit();
    }

    public static void loadObjectsFragment(final Fragment fragment, boolean addToBackStack, Context context) {
        final FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment, fragment.getClass().getName());
        Log.i("CurrentFragment", fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
//        CustomUtils.popBackStack(fragment);
        fragmentTransaction.commit();
    }


    /**
     * This is method is used for removing a fragment from backstack
     *
     * @param fragment
     */
    public static void popBackStack(Fragment fragment) {
        fragment.getFragmentManager().popBackStack();
    }


    // Decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }



}
