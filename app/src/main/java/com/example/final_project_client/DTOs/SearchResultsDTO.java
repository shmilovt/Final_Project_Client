package com.example.final_project_client.DTOs;

import com.google.gson.Gson;

/**
 * Created by TAMIR on 4/21/2018.
 */

public class SearchResultsDTO {
    private ResultRecordDTO[] resultRecordDTOS;
    public SearchResultsDTO(){}
    public SearchResultsDTO(ResultRecordDTO[] resultRecordDTOS){

        this.resultRecordDTOS = resultRecordDTOS;

    }
    public ResultRecordDTO[] getResultRecordDTOS() {
        return resultRecordDTOS;
    }

    public void setResultRecordDTOS(ResultRecordDTO[] resultRecordDTOS) {
        this.resultRecordDTOS = resultRecordDTOS;
    }

    public static String toJSON(SearchResultsDTO searchResultsDTO){
        Gson gson = new Gson();
        return gson.toJson(searchResultsDTO);
    }

    public static SearchResultsDTO fromJSON(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, SearchResultsDTO.class);
    }
}
