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

public class ProtectedSpaceReportFragment extends Fragment implements ReportFragment {
    private View mView;
    private Button haveProtectedSpaceBtn;
    private Button dontHaveProtectedSpaceBtn;
    private Integer hasProtectedSpace = null;

    public ProtectedSpaceReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_positive_negative_report, container, false);
        haveProtectedSpaceBtn = (Button)mView.findViewById(R.id.positiveBtn);
        dontHaveProtectedSpaceBtn = (Button)mView.findViewById(R.id.negativeBtn);
        haveProtectedSpaceBtn.setText(R.string.haveProtectedSpace);
        dontHaveProtectedSpaceBtn.setText(R.string.dontHaveProtectedSpace);
        dontHaveProtectedSpaceBtn.setBackgroundResource(R.drawable.report_not_selected_button_background);
        haveProtectedSpaceBtn.setBackgroundResource(R.drawable.report_not_selected_button_background);
        haveProtectedSpaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveProtectedSpaceBtn.setBackgroundResource(R.drawable.choosen_button);
                dontHaveProtectedSpaceBtn.setBackgroundResource(R.drawable.report_not_selected_button_background);
                hasProtectedSpace = 1;
            }
        });

        dontHaveProtectedSpaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dontHaveProtectedSpaceBtn.setBackgroundResource(R.drawable.choosen_button);
                haveProtectedSpaceBtn.setBackgroundResource(R.drawable.report_not_selected_button_background);
                hasProtectedSpace = 0;
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
        if(hasProtectedSpace != null){
            return new Gson().toJson(hasProtectedSpace);
        }
        else{
            return null;
        }
    }
}
