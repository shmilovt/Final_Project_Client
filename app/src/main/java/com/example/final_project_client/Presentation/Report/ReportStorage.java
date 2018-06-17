package com.example.final_project_client.Presentation.Report;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by TAMIR on 6/17/2018.
 */

public class ReportStorage {
    private Set<String> reports;
    private static ReportStorage Instance = null;

    private ReportStorage(){
        reports = new HashSet<>();
    }

    public static ReportStorage getInstance(){
        if(Instance != null)
            return  Instance;
        else{
            Instance = new ReportStorage();
            return Instance;
        }
    }

    public boolean isReportExist(Report report){
        String reportJson = (new Gson()).toJson(report);
        if(reports.contains(reportJson ))
            return true;
        return false;
    }

    public void addReport(Report report){
        String reportJson = (new Gson()).toJson(report);
        reports.add(reportJson);
    }


}
