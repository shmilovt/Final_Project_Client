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

public class WarehouseReportFragment extends Fragment implements ReportFragment{
    private View mView;
    private Button haveWarehouseBtn;
    private Button dontHaveWarehouseBtn;
    private Integer hasWarehouse = null;

    public WarehouseReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_positive_negative_report, container, false);
        haveWarehouseBtn = (Button)mView.findViewById(R.id.positiveBtn);
        dontHaveWarehouseBtn = (Button)mView.findViewById(R.id.negativeBtn);
        haveWarehouseBtn.setText(R.string.haveWarehouse);
        dontHaveWarehouseBtn.setText(R.string.dontHaveWarehouse);
        dontHaveWarehouseBtn.setBackgroundResource(android.R.drawable.btn_default);
        haveWarehouseBtn.setBackgroundResource(android.R.drawable.btn_default);
        haveWarehouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveWarehouseBtn.setBackgroundResource(R.drawable.choosen_button);
                dontHaveWarehouseBtn.setBackgroundResource(android.R.drawable.btn_default);
                hasWarehouse = 1;
            }
        });

        dontHaveWarehouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dontHaveWarehouseBtn.setBackgroundResource(R.drawable.choosen_button);
                haveWarehouseBtn.setBackgroundResource(android.R.drawable.btn_default);
                hasWarehouse = 0;
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
        if(hasWarehouse != null){
            return new Gson().toJson(hasWarehouse);
        }
        else{
            return null;
        }
    }

}
