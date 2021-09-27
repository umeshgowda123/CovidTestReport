package com.example.covidtestreport;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.APIClient;
import api.APIInterface;
import model.AlertDialogClass_Alert;
import model.OTPRequest;
import model.OtpResponse;
import model.TokenRes;
import receivers.AppSignatureHashHelper;
import receivers.SMSReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="HashKey" ;
    APIInterface apiInterface;
    String acessToken, tokenType;
    SharedPreferences sharedPreferences;
    String mobileNumber;
    int PERMISSION_REQUEST_CODE = 200;
    ProgressDialog dialog;
    public static final String SHARED_PREFERENCES = "shared_preferences";

    private String received_otp;
    private char[] c1;

    private boolean serviceResponse;
    private boolean broadCastResponse;


    private String sMobileNumber;
    private String sOtp;

    private SMSReceiver smsReceiver;
    private String HashKey;
//    public static final String TAG = com.example.receivers.AppSignatureHashHelper.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Method", "onCreate");
        setContentView(R.layout.activity_main);

        EditText etMobileNum = findViewById(R.id.phoneNum);
        Button button = findViewById(R.id.sendOTP);
        PhoneNumberUtils.formatNumber(etMobileNum.getText().toString());
        etMobileNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); // 10 is max digits

        dialog = new ProgressDialog(this, R.style.Base_Theme_AppCompat_Dialog_Alert);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading...");
        dialog.setMessage("Please wait, Do not Interrupt.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(1);
        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);
        // This code requires one time to get Hash keys do comment and share key
        Log.i(TAG, "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));

        HashKey = appSignatureHashHelper.getAppSignatures().get(0);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileNumber = etMobileNum.getText().toString();

                Log.d("Enter_mobieleNumber", "" + mobileNumber);

                if (!TextUtils.isEmpty(mobileNumber)) {
                    if (isValidMobile(mobileNumber)) {
                        if (isNetworkAvailable()) {
                            dialog.show();
                            apiInterface = APIClient.getClientWithoutToken().create(APIInterface.class);
                            OTPRequest otpRequest = new OTPRequest();
                            otpRequest.setMobileNumber(mobileNumber);
                            Call<TokenRes> callToken = apiInterface.getToken(getString(R.string.username), getString(R.string.password), getString(R.string.grandtype));
                            callToken.enqueue(new Callback<TokenRes>() {
                                @Override
                                public void onResponse(Call<TokenRes> call, Response<TokenRes> response) {
                                    if (response.isSuccessful()) {
                                        TokenRes tokenRes = response.body();
                                        //  String otpres = tokenRes.getResponseOTP().trim();

                                        acessToken = tokenRes.getAccessToken();
                                        tokenType = tokenRes.getTokenType();
                                        sendOtp(acessToken, tokenType, otpRequest);
                                        // Toast.makeText(MainActivity.this, "sucess", Toast.LENGTH_LONG).show();
                                        startSMSListener();

                                    }

                                }

                                @Override
                                public void onFailure(Call<TokenRes> call, Throwable t) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            buildAlertMessage();
                        }
                    } else {
                        etMobileNum.setError("enter valid num");
                    }
                } else {
                    etMobileNum.setError("please fill the num");
                }
            }
        });
//        else{
//            requestPerms();
//        }
    }


    public void openActivity2(OTPRequest otpRequest, String getOTP) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("mobileNumber", otpRequest.getMobileNumber());
        intent.putExtra("getOTP", getOTP);
        startActivity(intent);
        finish();

    }

    private void displayAlert(String msg) {

        AlertDialogClass_Alert dialog = new AlertDialogClass_Alert(MainActivity.this, "OK", "Info", "" + msg);
        dialog.displayDialog();
    }

    public void sendOtp(String accessToken, String tokenType, OTPRequest otpRequest) {
        APIInterface apiInterface = APIClient.getClient(tokenType, accessToken).create(APIInterface.class);
        Call<OtpResponse> call = apiInterface.FnSendOtp(otpRequest);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    OtpResponse otpResponse = response.body();
                    String otp = otpResponse.getResponseOTP();
                    Toast.makeText(MainActivity.this, "OTP sent Successfully and if your not receive OTP, Please check Your Number", Toast.LENGTH_LONG).show();
                    openActivity2(otpRequest, otp);
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isValidMobile(String phone) {
        boolean check;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() == 10;
        } else {
            check = false;
        }
        return check;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (Objects.requireNonNull(connectivityManager.getNetworkInfo(0)).getState() == android.net.NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(0)).getState() == android.net.NetworkInfo.State.CONNECTING ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(1)).getState() == android.net.NetworkInfo.State.CONNECTING ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(1)).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;

        } else if (Objects.requireNonNull(connectivityManager.getNetworkInfo(0)).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(1)).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    private void buildAlertMessage() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("No internet connection")
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Check your mobile data or Wi-Fi")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        AlertDialog alert = builder.create();
//                        alert.dismiss();
                        finish();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.default_color));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
    }
//
//    private void requestPerms() {
//
//        ActivityCompat.requestPermissions(this, new String[]
//                {ACCESS_NETWORK_STATE,
//                        INTERNET,}, PERMISSION_REQUEST_CODE);
//    }


            /**
         * Starts SmsRetriever, which waits for ONE matching SMS message until timeout
         * (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
         * action SmsRetriever#SMS_RETRIEVED_ACTION.
         */
        private void startSMSListener()
        {
            Log.d("SMSListener","Enter startSMSListener");
            try {
                smsReceiver = new SMSReceiver();
                smsReceiver.setOTPListener((SMSReceiver.OTPReceiveListener) this);
                Log.d("SMSListener","initialize startSMSListener");

                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
                this.registerReceiver(smsReceiver, intentFilter);

                SmsRetrieverClient client = SmsRetriever.getClient(this);

                Log.d("SMSListener","initialize SmsRetrieverClient");

                Task<Void> task = client.startSmsRetriever();
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // API successfully started
                        Log.d("SMSListener","success");
                    }
                });

                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Fail to start API
                        Log.d("SMSListener","Failed");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("SMSListener",""+ e.getLocalizedMessage());
            }
        }


    }



//    private void buildAlertMessage() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("ALERT")
//                .setMessage("Are you sure, do you want to Login?")
//                .setIcon(android.R.drawable.alert_dark_frame)
//                .setCancelable(false)
//                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        Toast.makeText(getApplicationContext(), "Please Enter Your Number", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }


