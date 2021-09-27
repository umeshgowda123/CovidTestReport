package com.example.covidtestreport;

import static android.view.LayoutInflater.*;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends ArrayAdapter<CovidMasterData> {
    private static final String TAG = "PersonListAdapter";
    private Context mContext;
    int mResource;
    List<CovidMasterData> test;

    public PersonListAdapter(@NonNull Context context, int resource, @NonNull List<CovidMasterData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        test = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) 
    {
     
        String SRF_id = getItem(position).getSRFID();
        String name = getItem(position).getNameOfPatient();
        String  date = getItem(position).getSwabCollectedOn();
        String result = getItem(position).getResult();
       String age = getItem(position).getAge();

         String name_of_lab = getItem(position).getNameOfTestingLab();
         String icmr = getItem(position).getICMRID();
         String gender = getItem(position).getGender();
        String district_number = getItem(position).getDistrictPNUMBER();
        String sample_date = getItem(position).getSwabCollectedOn();
         String result_date = getItem(position).getPOSITIVECONFIMRATIONDATE();
        String state_number = getItem(position).getStatePNUMBER();


        LayoutInflater inflater = from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvSRF = (TextView) convertView.findViewById(R.id.SRF_id);
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvDate = (TextView) convertView.findViewById(R.id.date);
        TextView tvResult = (TextView) convertView.findViewById(R.id.result);
        TextView tvAge = (TextView) convertView.findViewById(R.id.age);
        TextView tvname_of_lab = (TextView) convertView.findViewById(R.id.name_of_lab);
        TextView tvicmr = (TextView) convertView.findViewById(R.id.icmr);

        TextView tvgender = (TextView) convertView.findViewById(R.id.gender);
        TextView tvDistrict_NUMBER = (TextView) convertView.findViewById(R.id.address);
        TextView tvSwab_Collection = (TextView) convertView.findViewById(R.id.sample_date);
        TextView tvresult_date = (TextView) convertView.findViewById(R.id.result_date);
        TextView tvState_Number = (TextView) convertView.findViewById(R.id.type_of_test);
        LinearLayout testLay = convertView.findViewById(R.id.testLay);
      //  TableLayout table_id = convertView.findViewById(R.id.table_id);

        tvSRF.setText(SRF_id);
        tvName.setText(name);
        tvDate.setText(date);
        tvResult.setText(result);
        tvAge.setText(age);
        tvname_of_lab.setText(name_of_lab);
        tvicmr.setText(icmr);
        tvgender.setText(gender);
        tvDistrict_NUMBER.setText(district_number);
        tvSwab_Collection.setText(sample_date);
        tvresult_date.setText(result_date);
        tvState_Number.setText(state_number);



        testLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("testClickPosition1", ""+tvSRF.getText().toString());
                Log.d("testClickPosition2", ""+tvName.getText().toString());
                Log.d("testClickPosition3", ""+tvDate.getText().toString());
                Log.d("testClickPosition4", ""+tvResult.getText().toString());
                Log.d("testClickPosition5", ""+tvAge.getText().toString());

                Log.d("testClickPosition6", ""+tvname_of_lab.getText().toString());
                Log.d("testClickPosition7", ""+tvicmr.getText().toString());
                Log.d("testClickPosition8", ""+tvgender.getText().toString());
                Log.d("testClickPosition9", ""+tvDistrict_NUMBER.getText().toString());
                Log.d("testClickPosition10", ""+tvSwab_Collection.getText().toString());
                Log.d("testClickPosition11", ""+tvresult_date.getText().toString());
                Log.d("testClickPosition12", ""+tvState_Number.getText().toString());

//
//                Log.d("on click on",+mContext.get(position));
//                Toast.makeText(mContext,mResource.get(position),Toast.LENGTH_LONG).show();



                Intent i = new Intent(mContext, All_details.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("tvSRF", ""+tvSRF.getText().toString());
                i.putExtra("tvName",""+tvName.getText().toString());
                i.putExtra("tvname_of_lab",""+tvname_of_lab.getText().toString());
                i.putExtra("tvicmr",""+tvicmr.getText().toString());
                i.putExtra("tvgender",""+tvgender.getText().toString());
                i.putExtra("tvDistrict_NUMBER",""+tvDistrict_NUMBER.getText().toString());
                i.putExtra("tvSwab_Collection",""+tvSwab_Collection.getText().toString());
                i.putExtra("tvresult_date",""+tvresult_date.getText().toString());
                i.putExtra("tvState_Number",""+tvState_Number.getText().toString());
                i.putExtra("tvDate", ""+tvDate.getText().toString());
                i.putExtra("tvResult", ""+tvResult.getText().toString());
                i.putExtra("tvAge", ""+tvAge.getText().toString());

                mContext.startActivity(i);

            }
        });
        
        return convertView;
    }
}


