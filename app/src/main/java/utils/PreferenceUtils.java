package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public static boolean saveMobilenumber(String mobilenumber, Context context,String otpnumber,Boolean b)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(Constants.MOBILE_NUMBER,mobilenumber);
        preferencesEditor.putString(Constants.OTP_NUMBER,otpnumber);
        preferencesEditor.putBoolean(Constants.LOGIN_STATUS,b );
        preferencesEditor.apply();
        return true;
    }
    public static String getMobilenumber(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.MOBILE_NUMBER,null);
    }


    public static boolean saveOTPenumber(String otpnumber, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(Constants.OTP_NUMBER,otpnumber);
        preferencesEditor.apply();
        return true;
    }

    public static String getOTPenumber(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.OTP_NUMBER,null);
    }

}
