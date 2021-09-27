package com.example.covidtestreport;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.window.SplashScreen;

import database.DatabaseHelper;
//import model.LocationData;
import utils.Constants;


public class Splash_Screen extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private String verName = null;
    private int verCode = 0;
    String RangeFactorValue;

    private String mobile_Shared;
    private String otp_shared;
    private boolean Login_Status;
    private String mNumber;
    private DatabaseHelper databaseHelper;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

       // checkupdate();
        loadLocalData();
    }

//    private void checkupdate() {
//        int internetpermissionCheck = ContextCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.INTERNET);
//        int accessNetworkStateResult = ContextCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.ACCESS_NETWORK_STATE);
//
//        if (internetpermissionCheck == PackageManager.PERMISSION_GRANTED && accessNetworkStateResult == PackageManager.PERMISSION_GRANTED)
//        {
//            checkAppUpdate();
//        } else
//        {
//            loadLocalData();
//        }
//
//    }
//
//    private void checkAppUpdate() {
//
//
//
//    }

    public void loadLocalData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.contains(Constants.MOBILE_NUMBER) && sharedPreferences.contains(Constants.LOGIN_STATUS)) {
                    Log.d("LOGIN_STATUS", "old one");
                    mobile_Shared = sharedPreferences.getString(Constants.MOBILE_NUMBER, "");
                    otp_shared = sharedPreferences.getString(Constants.OTP_NUMBER, "");

                    Login_Status = sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false);

                    if (sharedPreferences.contains(Constants.MOBILE_NUMBER) && Login_Status) {
                        mNumber = sharedPreferences.getString(Constants.MOBILE_NUMBER, "");
                        Log.d("mNumber", "" + mNumber);
                        Intent intent = new Intent(Splash_Screen.this, Result_For_MobileNo.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("Mobile_number", "Details not found");
                        Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.d("LOGIN_STATUS", "Fresh one");
                    Intent intent1 = new Intent(Splash_Screen.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        }, 1000);

    }
}


//    private void checkVersionNumberForApplicationUpdate() {
//        try {
//            PackageManager manager = this.getPackageManager();
//            PackageInfo packageInfo = manager.getPackageInfo(this.getPackageName(),PackageManager.GET_ACTIVITIES);
//            verName = packageInfo.versionName;
//            verCode = packageInfo.versionCode;
//        }catch (PackageManager.NameNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        dialog.show();
//      APIInterface  apiInterface = APIClient.getClientWithoutToken().create(APIInterface.class);
//        Call<TokenRes> callToken = apiInterface.getToken(getString(R.string.username), getString(R.string.password), getString(R.string.grandtype));
//
//        callToken.enqueue(new Callback<TokenRes>() {
//            @Override
//            public void onResponse(Call<TokenRes> call, Response<TokenRes> response) {
//                if (response.isSuccessful())
//                {
//                    dialog.dismiss();
//                    TokenRes tokenRes = response.body();
//                    //  String otpres = tokenRes.getResponseOTP().trim();
//
//                    String acessToken = tokenRes.getAccessToken();
//                   String tokenType = tokenRes.getTokenType();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TokenRes> call, Throwable t) {
//
//            }
//        });
//
//    }

//    private void loadLocationMasterData() {
//
//        databaseHelper = Room.databaseBuilder(getApplicationContext(),
//                DatabaseHelper.class, getString(R.string.db_name)).build();
//
//        Observable<Integer> noOfRows = Observable.fromCallable(new Callable<Integer>() {
//
//            @Override
//            public Integer call() {
//                return databaseHelper.daoAccess().getNumberOfCovidDataRows();
//            }
//        });
//        noOfRows
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer>() {
//
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        if (integer == 0) {
//                            List<LocationData> c_master_List = loadDataFromCsv();
//                            createLocationMasterData(c_master_List);
//
//                        } else {
//                            checkPermission();
//                            Log.d("Master Data Loaded", "Master Data Loaded SuccessFully!");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

// new Handler().postDelayed(new Runnable() {
//@Override
//public void run() {
//        //This method will be executed once the timer is over
//        // Start your app main activity
//        Intent i = new Intent(Splash_Screen.this, MainActivity.class);
//        startActivity(i);
//        // close this activity
//        finish();
//        }
//        }, 3000);