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


public class SizeReportFragment extends Fragment implements ReportFragment {

    private View mView;
    private EditText editTextSize;

    public SizeReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_size_report, container, false);
        editTextSize = mView.findViewById(R.id.editTextSize);
        editTextSize.addTextChangedListener(new TextWatcher() {
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
                        editTextSize.setText("");
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

    private Double getSize() {
        Double size;
        try {
            size = Double.parseDouble(editTextSize.getText().toString());
        } catch (NumberFormatException e) {
            size = null;
        }
        return size;
    }


    @Override
    public String getContent() {
        Double size = getSize();
        if (size != null)
            return new Gson().toJson(size);
        else{
            return null;
        }
    }
}
