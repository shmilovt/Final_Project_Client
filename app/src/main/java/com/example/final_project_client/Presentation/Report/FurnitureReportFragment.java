package com.example.final_project_client.Presentation.Report;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.final_project_client.R;
import com.google.gson.Gson;

/**
 * Created by TAMIR on 5/30/2018.
 */

public class FurnitureReportFragment extends Fragment implements ReportFragment {
    private View mView;
    private Button fullFurnitureBtn;
    private Button partialFurnitureBtn;
    private Button dontHaveFurnitureBtn;
    private Integer furnitureReportSelection = null;

    public FurnitureReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_furniture_report, container, false);
        fullFurnitureBtn = (Button) mView.findViewById(R.id.fullFurnitureBtn);
        partialFurnitureBtn = (Button) mView.findViewById(R.id.partialFurnitureBtn);
        dontHaveFurnitureBtn = (Button) mView.findViewById(R.id.dontHaveFurnitureBtn);
        partialFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
        fullFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
        dontHaveFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
        fullFurnitureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullFurnitureBtn.setBackgroundResource(R.drawable.choosen_button);
                partialFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
                dontHaveFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
                furnitureReportSelection = 2;
            }
        });

        partialFurnitureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partialFurnitureBtn.setBackgroundResource(R.drawable.choosen_button);
                fullFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
                dontHaveFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
                furnitureReportSelection = 1;
            }
        });

        dontHaveFurnitureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dontHaveFurnitureBtn.setBackgroundResource(R.drawable.choosen_button);
                fullFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
                partialFurnitureBtn.setBackgroundResource(android.R.drawable.btn_default);
                furnitureReportSelection = 0;
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
        if (furnitureReportSelection != null) {
            return new Gson().toJson(furnitureReportSelection);
        } else {
            return null;
        }
    }
}
