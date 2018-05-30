package com.example.final_project_client.Presentation.Report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.final_project_client.R;
import com.google.gson.Gson;


public class GardenReportFragment extends Fragment implements ReportFragment {

    private View mView;
    private Button haveGardenBtn;
    private Button dontHaveGardenBtn;
    private Boolean hasGarden = null;

    public GardenReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_positive_negative_report, container, false);
        haveGardenBtn = (Button)mView.findViewById(R.id.positiveBtn);
        dontHaveGardenBtn = (Button)mView.findViewById(R.id.negativeBtn);
        dontHaveGardenBtn.setBackgroundResource(android.R.drawable.btn_default);
        haveGardenBtn.setBackgroundResource(android.R.drawable.btn_default);
        haveGardenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveGardenBtn.setBackgroundResource(R.drawable.choosen_button);
                dontHaveGardenBtn.setBackgroundResource(android.R.drawable.btn_default);
                hasGarden = true;
            }
        });

        dontHaveGardenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dontHaveGardenBtn.setBackgroundResource(R.drawable.choosen_button);
                haveGardenBtn.setBackgroundResource(android.R.drawable.btn_default);
                hasGarden = false;
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public String getContent() {
        if(hasGarden != null){
            return new Gson().toJson(hasGarden);
        }
        else{
            return null;
        }
    }

}