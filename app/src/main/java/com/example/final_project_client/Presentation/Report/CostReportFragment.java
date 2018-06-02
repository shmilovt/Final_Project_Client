package com.example.final_project_client.Presentation.Report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.final_project_client.R;
import com.google.gson.Gson;


public class CostReportFragment extends Fragment implements ReportFragment {


    private View mView;
    private EditText editTextCost;

    public CostReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cost_report, container, false);
        editTextCost = mView.findViewById(R.id.editTextCost);
        editTextCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer buidingNumberContentInteger;
                try {
                    buidingNumberContentInteger = Integer.parseInt(s.toString());
                    if (buidingNumberContentInteger == 0)
                        editTextCost.setText("");
                } catch (NumberFormatException e) {

                }
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private Integer getCost() {
        Integer cost;
        try {
            cost = Integer.parseInt(editTextCost.getText().toString());
        } catch (NumberFormatException e) {
            cost = null;
        }
        return cost;
    }


    @Override
    public String getContent() {
        Integer cost = getCost();
        if (cost != null)
            return new Gson().toJson(cost);
        else{
            return null;
        }
    }
}
