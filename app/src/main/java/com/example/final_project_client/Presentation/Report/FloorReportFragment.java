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


public class FloorReportFragment extends Fragment implements ReportFragment {
    private View mView;
    private EditText floorContent;


    public FloorReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mView =  inflater.inflate(R.layout.fragment_floor_report, container, false);
       floorContent = (EditText) mView.findViewById(R.id.floorContent);
       floorContent.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
             try{
                Integer floor = Integer.parseInt(s.toString());
                if(floor>30 || floor <-1)
                    floorContent.setText("");
             }
             catch (NumberFormatException e){

             }
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
        Integer content;
        String contentString = floorContent.getText().toString();
        try{
            content = Integer.parseInt(contentString);
        }
        catch (NumberFormatException e){
            content = null;
        }

        if(content!=null)
        {
            return new Gson().toJson(content);
        }
        else{
            return null;
        }
    }
}
