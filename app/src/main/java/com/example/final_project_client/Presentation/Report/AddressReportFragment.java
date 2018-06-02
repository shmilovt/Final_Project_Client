package com.example.final_project_client.Presentation.Report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.final_project_client.DTOs.AddressDTO;
import com.example.final_project_client.R;
import com.google.gson.Gson;


public class AddressReportFragment extends Fragment implements ReportFragment {


    private View mView;
    private EditText streetContent;
    private EditText buildingNumberContent;
    private Spinner neighborhoodContentSpinner;

    public AddressReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_address_report, container, false);
        streetContent = (EditText) mView.findViewById(R.id.streetContent);
        buildingNumberContent = (EditText) mView.findViewById(R.id.buildingNumberContent);
        neighborhoodContentSpinner = (Spinner) mView.findViewById(R.id.spinnerNeighborhoodContent);

        buildingNumberContent.addTextChangedListener(new TextWatcher() {
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
                    if(buidingNumberContentInteger == 0)
                        buildingNumberContent.setText("");
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

    @Override
    public String getContent() {
        String streetContentString = streetContent.getText().toString();
        streetContentString = getformattedStreetString(streetContentString);
        if (streetContentString.compareTo("") == 0)
            streetContentString = null;

        String buildingNumberContentString = buildingNumberContent.getText().toString();
        Integer buidingNumberContentInteger;
        try {
            buidingNumberContentInteger = Integer.parseInt(buildingNumberContentString);
        } catch (NumberFormatException e) {
            buidingNumberContentInteger = null;
        }

        String neighborhoodContentString;
        int selectedItemPosition = neighborhoodContentSpinner.getSelectedItemPosition();
        if (selectedItemPosition == 0)
            neighborhoodContentString = null;
        else
            neighborhoodContentString = neighborhoodContentSpinner.getSelectedItem().toString();
        if (streetContentString != null)
            System.out.println(streetContentString);
        if (buidingNumberContentInteger != null)
            System.out.println(buidingNumberContentInteger);
        if (neighborhoodContentString != null)
            System.out.println(neighborhoodContentString);
        if (buidingNumberContentInteger != null || neighborhoodContentString != null || streetContentString != null) {
            AddressDTO addressDTO = new AddressDTO(streetContentString, buidingNumberContentInteger, neighborhoodContentString);
            return new Gson().toJson(addressDTO);
        } else {
            return null;
        }
    }

    private String getformattedStreetString(String unformattedStreetString) {
        int i = 0;
        for (char ch : unformattedStreetString.toCharArray()) {
            if (ch != ' ') {
                break;
            }
            i++;
        }

        return unformattedStreetString.substring(i);
    }
}
