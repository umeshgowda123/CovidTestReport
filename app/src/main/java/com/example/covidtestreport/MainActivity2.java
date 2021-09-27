package com.example.covidtestreport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import api.APIClient;
import api.APIInterface;
import database.DatabaseHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.OtpVerRes;
import model.TokenRes;
import model.ValidOTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.PreferenceUtils;

public class MainActivity2 extends AppCompatActivity {

    Button b1;
    EditText e1;
    APIInterface apiInterface;
    String acessToken,tokenType;
    String editOTP;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        b1 = findViewById(R.id.verifytop);
        e1 = findViewById(R.id.editotp);


        Intent i = getIntent();
        String getOTP = i.getStringExtra("getOTP");
        String mobileNumber = i.getStringExtra("mobileNumber");
        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class,"CovidTestReport").build();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editOTP = e1.getText().toString();
                if (getOTP.equals(editOTP)) {
                    apiInterface = APIClient.getClientWithoutToken().create(APIInterface.class);
                    ValidOTPRequest validOTPRequest = new ValidOTPRequest();
                    validOTPRequest.setpOTP(editOTP);
                    validOTPRequest.setpMobileNumber(mobileNumber);

                    Call<TokenRes> callToken = apiInterface.getToken(getString(R.string.username), getString(R.string.password), getString(R.string.grandtype));
                    callToken.enqueue(new Callback<TokenRes>() {
                        @Override
                        public void onResponse(Call<TokenRes> call, Response<TokenRes> response) {
                            if (response.isSuccessful()) {
                                TokenRes tokenRes = response.body();
                                acessToken = tokenRes.getAccessToken();
                                tokenType = tokenRes.getTokenType();
                                otpValidate(acessToken, tokenType, validOTPRequest);
                                Toast.makeText(MainActivity2.this, "sucess", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenRes> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(MainActivity2.this, "Invalid otp", Toast.LENGTH_LONG).show();

                }
//                if (e1.getText().toString().equals("1234"))
//                {
//                    Toast.makeText(getApplicationContext(),"Redirecting....To next step ",Toast.LENGTH_LONG).show();
//                    openActivity3();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"Wrong OTP You Entered",Toast.LENGTH_LONG).show();
//                }
            }
        });

    }
    public void otpValidate(String accessToken, String tokenType, ValidOTPRequest validOTPRequest)
    {
        APIInterface apiInterface = APIClient.getClient(tokenType, accessToken).create(APIInterface.class);
        Call<OtpVerRes> call = apiInterface.FnValidateOtp(validOTPRequest);
        call.enqueue(new Callback<OtpVerRes>()
        {
            @Override
            public void onResponse(Call<OtpVerRes> call, Response<OtpVerRes> response)
            {
                if (response.isSuccessful()) {
                    OtpVerRes otpVerRes = response.body();
                    List<CovidMasterData> dbData = otpVerRes.getCovidMasterData();
                    if (dbData.size()>0) {
                        getCountofData(dbData,validOTPRequest);

                    } else {
                        Toast.makeText(MainActivity2.this,"null data",Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(MainActivity2.this, "Sucess", Toast.LENGTH_SHORT).show();

                    //otpResponse.get
                }
            }

            @Override
            public void onFailure(Call<OtpVerRes> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Fail", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void insertdata(final List<CovidMasterData> covidMasterDataList,ValidOTPRequest validOTPRequest)
    {

        Observable<Long[]> insertMasterObservable = Observable.fromCallable(new Callable<Long[]>()
        {
            @Override
            public Long[] call()
            {
                return databaseHelper.daoAccess().insertCovidMasterData(covidMasterDataList);
            }
        });
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {

                        Log.i("INSERTED_DATA", " " + Arrays.toString(longs));

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        Log.i("INSERTED_DATA", " insert done" );
                        openActivity3(validOTPRequest);
                    }
                });

    }
    public void getCountofData(final List<CovidMasterData> covidMasterDataList,ValidOTPRequest validOTPRequest)
    {

        Observable<Integer> getcountObserable = Observable.fromCallable(new Callable<Integer>()
        {
            @Override
            public Integer call()
            {
                return databaseHelper.daoAccess().getNumberOfCovidDataRows();
            }
        });
        getcountObserable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer i) {
                        if (i == 0)
                        {
                            insertdata(covidMasterDataList, validOTPRequest);
                        }else
                        {
                            deletedata(covidMasterDataList, validOTPRequest);
                        }

                        Log.i("count of DATA", " " +i);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        Log.i("count of DATA", " count done" );

                    }
                });
    }
    public void deletedata(final List<CovidMasterData> covidMasterDataList,ValidOTPRequest validOTPRequest)
    {
//        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class,"CovidTestReport").build();
        Observable<Integer> getdeleteObserable = Observable.fromCallable(new Callable<Integer>()
        {
            @Override
            public Integer call()
            {
                return databaseHelper.daoAccess().getdeletedata();
            }
        });
        getdeleteObserable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer i) {
                        insertdata(covidMasterDataList,validOTPRequest);
                        Log.i("DATA", " " +i);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        Log.i("DATA", " delete done" );

                    }
                });
    }

    public void openActivity3(ValidOTPRequest validOTPRequest)
    {
        PreferenceUtils.saveMobilenumber(validOTPRequest.getpMobileNumber(),getApplicationContext(),validOTPRequest.getpOTP(),true);
        Intent intent = new Intent(this,Result_For_MobileNo.class);
        startActivity(intent);
        finish();
    }

    long counter = 0;
    @Override
    public void onBackPressed() {
        counter ++;
        if (counter==2) {
            super.onBackPressed();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(getBaseContext(),"press back button again to exit ",Toast.LENGTH_LONG).show();
        }
    }

}