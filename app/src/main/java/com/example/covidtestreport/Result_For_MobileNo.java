package com.example.covidtestreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Callable;

import database.DatabaseHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Result_For_MobileNo extends AppCompatActivity {

    public static final String TAG = "Result_For_MobileNo";
    ListView mlistView;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_for_mobile_no);

        Log.d(TAG, "onCreate: Started ");
        mlistView = (ListView) findViewById(R.id.mListView);

        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "CovidTestReport").build();

        selectData();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick (AdapterView < ? > adapterView, View view,int i, long l){
//                String viewDetails = peopleList.toString();

                Intent intent = new Intent(Result_For_MobileNo.this, All_details.class);
//                intent.putExtra("Listviewclickvalue", viewDetails);
                startActivity(intent);
                //finish();
            }
        });

    }

    public void selectData() {

        Observable<List<CovidMasterData>> getSelectDataObserable = Observable.fromCallable(new Callable<List<CovidMasterData>>() {
            @Override
            public List<CovidMasterData> call() {
                return databaseHelper.daoAccess().getCovidDetails();
            }
        });
        getSelectDataObserable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CovidMasterData>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CovidMasterData> covidMasterDataList) {
//                            List<CovidMasterData> covidMasterDataList1 = covidMasterDataList;
                        PersonListAdapter adapter = new PersonListAdapter(getApplicationContext(), R.layout.result_view_layout, covidMasterDataList);
                        mlistView.setAdapter(adapter);
                        Log.i("select the DATA", " ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        Log.i("select the DATA", " select done");

                    }
                });
    }
    long counter = 0;
    @Override
    public void onBackPressed() {
        counter ++;
        if (counter==3) {
            super.onBackPressed();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(getBaseContext(),"press back button again to exit ",Toast.LENGTH_LONG).show();
        }
    }
}

//        Result basava= new Result ("12334","basava","12-10-2021","positive","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result ram= new Result ("166334","ram","2-1-2021","positive","34","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result naveen= new Result ("9833464","naveen","18-10-2021","positive","76","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//
//        Result rob= new Result ("17534","rob","26-02-2021","negative","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result bean= new Result ("1237532","bean","19-7-2021","negative","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result radha = new Result ("992334","radha","12-10-2020","positive","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//
//        Result sowmya= new Result ("086534","sowmya","12-10-2020","positive","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result umesh = new Result ("12334","raju","1-9-2021","negative","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result saroja= new Result ("100334","saroja","12-10-2021","positive","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//
//        Result geetha= new Result ("1865008","geetha","12-10-2021","negative","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result sandy= new Result ("87334","sandy","12-10-2020","positive","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//        Result john = new Result ("098334","john","12-10-2021","positive","21","abc","1232","male","bengaluru","2-2-2021","3-2-2021","swap");
//
//        ArrayList<Result > peopleList = new ArrayList<>();
//
//        peopleList.add(basava);
//        peopleList.add(ram);
//        peopleList.add(naveen);
//        peopleList.add(rob);
//        peopleList.add(bean);
//        peopleList.add(radha);
//
//        peopleList.add(sowmya);
//        peopleList.add(umesh);
//        peopleList.add(saroja);
//        peopleList.add(geetha);
//        peopleList.add(sandy) ;
//        peopleList.add(john);






