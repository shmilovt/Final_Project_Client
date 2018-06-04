package com.example.final_project_client.Presentation.FullDescription;

import com.example.final_project_client.UserSearchingUtils.ResultRecord;

/**
 * Created by TAMIR on 5/28/2018.
 */

public class ApartmentFullDescriptionActivityDataHolder {
    private ResultRecord resultRecord;
    private static ApartmentFullDescriptionActivityDataHolder INSTANCE = null;


    private ApartmentFullDescriptionActivityDataHolder(){

    }

    public static ApartmentFullDescriptionActivityDataHolder getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ApartmentFullDescriptionActivityDataHolder();
            return INSTANCE;
        }
        else{
            return INSTANCE;
        }
    }

    public ResultRecord getResultRecord() {
        return resultRecord;
    }

    public void setResultRecord(ResultRecord resultRecord) {
        this.resultRecord = resultRecord;
    }
}
