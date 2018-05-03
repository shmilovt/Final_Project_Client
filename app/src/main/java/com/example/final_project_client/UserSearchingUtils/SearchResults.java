package com.example.final_project_client.UserSearchingUtils;

import com.example.final_project_client.DTOs.ResultRecordDTO;
import com.example.final_project_client.DTOs.SearchResultsDTO;
import com.google.gson.Gson;

/**
 * Created by TAMIR on 4/21/2018.
 */

public class SearchResults {
    private ResultRecord [] resultRecords;
    public SearchResults(SearchResultsDTO searchResultsDTO){

        ResultRecordDTO [] resultRecordDTOS = searchResultsDTO.getResultRecordDTOS();
        int size = resultRecordDTOS.length;
        resultRecords = new ResultRecord[resultRecordDTOS.length];

        for(int i = 0; i<size; i++)
            resultRecords[i] = new ResultRecord(resultRecordDTOS[i]);

    }


    public static SearchResults fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, SearchResults.class);
    }

    public ResultRecord[] getResultRecords() {
        return resultRecords;
    }

    public void setResultRecords(ResultRecord[] resultRecords) {
        this.resultRecords = resultRecords;
    }
}
