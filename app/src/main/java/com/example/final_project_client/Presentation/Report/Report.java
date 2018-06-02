package com.example.final_project_client.Presentation.Report;

import com.example.final_project_client.DTOs.ReportDTO;

/**
 * Created by TAMIR on 5/31/2018.
 */

public class Report {
    private String apartmentID;
    private Field field;
    private String contentInGson;

    public Report(){}
    public Report(String apartmentID, Field field, String contentInGson) {
        this.apartmentID = apartmentID;
        this.field = field;
        this.contentInGson = contentInGson;
    }


    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getContentInGson() {
        return contentInGson;
    }

    public void setContentInGson(String contentInGson) {
        this.contentInGson = contentInGson;
    }


}
