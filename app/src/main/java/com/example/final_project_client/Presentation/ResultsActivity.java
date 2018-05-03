package com.example.final_project_client.Presentation;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.FragmentTransaction;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.ResultRecord;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {

    public static final String  APARTMENT_FULL_DESCRIPTION ="APARTMENT_FULL_DESCRIPTION";
    private MapViewFragment mapViewFragment;
    private ListViewFragment listViewFragment;
    public static final int MaxDisplay = 15;
    private Button btnMode;
    private Button btnRecentlyResults;
    private Button btnNextResults;
    private ResultRecord [] resultRecords;
    private int index;




    public ResultsActivity(){
        index = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        index = DataHolder.getInstance().getIndex();
        resultRecords = DataHolder.getInstance().getResultRecords();

        if(DataHolder.getInstance().isOnFirstLaunch()) {

            mapViewFragment = new MapViewFragment();
            listViewFragment = new ListViewFragment();

            DataHolder.getInstance().setMapViewFragment(mapViewFragment);
            DataHolder.getInstance().setListViewFragment(listViewFragment);
        }
        else{
            mapViewFragment = DataHolder.getInstance().getMapViewFragment();
            listViewFragment = DataHolder.getInstance().getListViewFragment();
            mapViewFragment.updateData(this);



        }
        btnMode = findViewById(R.id.BtnMode);
        btnRecentlyResults = findViewById(R.id.BtnRecentlyResults);
        btnNextResults = findViewById(R.id.BtnNextResults);

        if (index == 0)
            btnRecentlyResults.setEnabled(false);

        mapViewFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapViewFragment).commit();
        btnMode.setText(getString(R.string.record_mode));

        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnMode.getText().equals(getString(R.string.map_mode))) {
                    btnMode.setText(getString(R.string.record_mode));
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, mapViewFragment);
                    transaction.commit();
                } else {
                    btnMode.setText(getString(R.string.map_mode));
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, listViewFragment);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Gson gson = new Gson();
        index = savedInstanceState.getInt("index");
        String resultRecordsJson = savedInstanceState.getString("resultRecords");
        resultRecords = gson.fromJson(resultRecordsJson, ResultRecord[].class);
        System.out.println("restore state...");


    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
      outState.putInt("index", index);
      String resultRecordsJson =  gson.toJson(resultRecords);
      outState.putString("resultRecords", resultRecordsJson);




    }

    public synchronized void nextResults(View view) {
        if(index+MaxDisplay<resultRecords.length){
            index = index+MaxDisplay;
            mapViewFragment.updateData(this);

            if(index+MaxDisplay>=resultRecords.length){
                btnNextResults.setEnabled(false);
            }
            if(index-MaxDisplay>=0){
                btnRecentlyResults.setEnabled(true);
            }
        }


    }

    public synchronized void previousResults(View view) {
        if(index-MaxDisplay>=0){
            index = index-MaxDisplay;
            mapViewFragment.updateData(this);

            if(index-MaxDisplay<0){
                btnRecentlyResults.setEnabled(false);
            }

            if(index+MaxDisplay<=resultRecords.length){
                btnNextResults.setEnabled(true);
            }
        }


    }

    public List<Coordinate>  getCoordinates() {
        List<Coordinate> coordinates = new ArrayList<>();
        for(int i=index; i<index+MaxDisplay && i<resultRecords.length; i++){
            Coordinate coordinate = new Coordinate(resultRecords[i].getLat(), resultRecords[i].getLon());
            coordinates.add(coordinate);
        }

        return coordinates;
    }

    public void getData(ResultsView mapViewFragment) {
        mapViewFragment.updateData(this);
    }

    public void openApartmentFullDescription(int index){
        DataHolder.getInstance().setIndex(this.index);
        DataHolder.getInstance().setResultRecords(resultRecords);
        int numberOfRecord = this.index + index;
        ResultRecord resultRecord = resultRecords[numberOfRecord];


        ApartmentFullDescription apartmentFullDescription = new ApartmentFullDescription(resultRecord);
        Intent intent = new Intent(this, ApartmentFullDescriptionActivity.class);
        intent.putExtra("APARTMENT_FULL_DESCRIPTION", apartmentFullDescription.toString());
        startActivity(intent);

    }
}
