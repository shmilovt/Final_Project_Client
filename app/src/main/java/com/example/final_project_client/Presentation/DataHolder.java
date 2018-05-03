package com.example.final_project_client.Presentation;

import com.example.final_project_client.UserSearchingUtils.ResultRecord;

/**
 * Created by TAMIR on 5/3/2018.
 */

public class DataHolder {
    private ResultRecord [] resultRecords;
    private int index;

    private MapViewFragment mapViewFragment;
    private ListViewFragment listViewFragment;
    private boolean onFirstLaunch;

    private static DataHolder INSTANCE = null;

    private DataHolder(){
        resultRecords = new ResultRecord[0];
        index = 0;
        onFirstLaunch = true;
    }

    public static DataHolder getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DataHolder();
            return INSTANCE;
        }
        else{
            return INSTANCE;
        }
    }


    public void setIndex(int index){
        this.index = index;
    }

    public  void  setResultRecords(ResultRecord [] resultRecords){
        this.resultRecords = resultRecords;

    }

    public ResultRecord[] getResultRecords() {
        return resultRecords;
    }

    public int getIndex() {
        return index;
    }


    public MapViewFragment getMapViewFragment() {
        return mapViewFragment;
    }

    public void setMapViewFragment(MapViewFragment mapViewFragment) {
        this.mapViewFragment = mapViewFragment;
    }

    public ListViewFragment getListViewFragment() {
        return listViewFragment;
    }

    public void setListViewFragment(ListViewFragment listViewFragment) {
        this.listViewFragment = listViewFragment;
    }

    public boolean isOnFirstLaunch() {
        return onFirstLaunch;
    }

    public void setOnFirstLaunch(boolean onFirstLaunch) {
        this.onFirstLaunch = onFirstLaunch;
    }
}
