package com.example.final_project_client.Presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.SearchResults;

/**
 * Created by TAMIR on 5/1/2018.
 */

public class ApartmentFullDescriptionActivity extends AppCompatActivity {
private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivity_apartment_full_description);

        Intent intent = getIntent();
        String description = intent.getStringExtra(ResultsActivity.APARTMENT_FULL_DESCRIPTION);

      content = (TextView)findViewById(R.id.txtFullDescription);
      content.setText(description);


    }
}

