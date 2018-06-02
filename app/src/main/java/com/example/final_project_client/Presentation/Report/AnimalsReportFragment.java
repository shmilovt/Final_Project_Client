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

public class AnimalsReportFragment extends Fragment implements ReportFragment{

    private View mView;
    private Button permittedAnimalsBtn;
    private Button forbiddenAnimalsBtn;
    private Integer isAnimalsPermitted = null;

    public AnimalsReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_positive_negative_report, container, false);
        permittedAnimalsBtn = (Button)mView.findViewById(R.id.positiveBtn);
        forbiddenAnimalsBtn = (Button)mView.findViewById(R.id.negativeBtn);
        forbiddenAnimalsBtn.setBackgroundResource(android.R.drawable.btn_default);
        permittedAnimalsBtn.setBackgroundResource(android.R.drawable.btn_default);
        permittedAnimalsBtn.setText(R.string.permittedAnimals);
        forbiddenAnimalsBtn.setText(R.string.forbiddenAnimals);
        permittedAnimalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permittedAnimalsBtn.setBackgroundResource(R.drawable.choosen_button);
                forbiddenAnimalsBtn.setBackgroundResource(android.R.drawable.btn_default);
                isAnimalsPermitted = 1;
            }
        });

        forbiddenAnimalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forbiddenAnimalsBtn.setBackgroundResource(R.drawable.choosen_button);
                permittedAnimalsBtn.setBackgroundResource(android.R.drawable.btn_default);
                isAnimalsPermitted = 0;
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
        if(isAnimalsPermitted != null){
            return new Gson().toJson(isAnimalsPermitted);
        }
        else{
            return null;
        }
    }
}
