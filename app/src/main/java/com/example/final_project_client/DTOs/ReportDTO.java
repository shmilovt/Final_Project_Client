package com.example.final_project_client.DTOs;

import com.example.final_project_client.Presentation.Report.Field;
import com.example.final_project_client.Presentation.Report.Report;
import com.google.gson.Gson;

/**
 * Created by TAMIR on 5/31/2018.
 */


    public class ReportDTO {
        private String apartmentID;
        private Field field;
        private String contentInGson;


    public ReportDTO(Report report) {
        apartmentID = report.getApartmentID();
        field = report.getField();
        contentInGson = report.getContentInGson();
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

    public static String toJSON(ReportDTO reportDTO) {
            return new Gson().toJson(reportDTO);

    }
}

