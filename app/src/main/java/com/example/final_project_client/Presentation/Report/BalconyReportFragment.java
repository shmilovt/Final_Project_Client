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

public class BalconyReportFragment extends Fragment implements ReportFragment {

    private View mView;
    private Button haveBalconyBtn;
    private Button dontHaveBalconyBtn;
    private Boolean hasBalcony = null;

    public BalconyReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_positive_negative_report, container, false);
        haveBalconyBtn = (Button)mView.findViewById(R.id.positiveBtn);
        dontHaveBalconyBtn = (Button)mView.findViewById(R.id.negativeBtn);
        dontHaveBalconyBtn.setBackgroundResource(android.R.drawable.btn_default);
        haveBalconyBtn.setBackgroundResource(android.R.drawable.btn_default);
        haveBalconyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveBalconyBtn.setBackgroundResource(R.drawable.choosen_button);
                dontHaveBalconyBtn.setBackgroundResource(android.R.drawable.btn_default);
                hasBalcony = true;
            }
        });

        dontHaveBalconyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dontHaveBalconyBtn.setBackgroundResource(R.drawable.choosen_button);
                haveBalconyBtn.setBackgroundResource(android.R.drawable.btn_default);
                hasBalcony = false;
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
        if(hasBalcony != null){
            return new Gson().toJson(hasBalcony);
        }
        else{
            return null;
        }
    }

}
