package com.example.final_project_client.Presentation.Report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.final_project_client.Presentation.ApartmentFullDescriptionActivity;
import com.example.final_project_client.Presentation.ReportCategory;
import com.example.final_project_client.R;
import com.google.gson.Gson;

public class ReportActivity extends AppCompatActivity {
    private ReportCategory reportCategory;
    private TextView sourceText;
    private FrameLayout reportFormFrameLayout;
    private ReportFragment reportFragment;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent intent = getIntent();
        sourceText = (TextView) findViewById(R.id.sourceTextReport);
        sourceText.setText(intent.getExtras().getString(ApartmentFullDescriptionActivity.SOURCE_TEXT));
        reportFormFrameLayout = (FrameLayout) findViewById(R.id.reportForm);
        reportCategory = (new Gson()).fromJson(intent.getExtras().getString(ApartmentFullDescriptionActivity.CATEGORY), ReportCategory.class);

        switch (reportCategory) {
            case furniture:
                break;
            case protectedSpace:
                break;
            case cost:
                CostReportFragment costReportFragment = new CostReportFragment();
                reportFragment = costReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, costReportFragment).commit();
                break;
            case size:
                SizeReportFragment sizeReportFragment = new SizeReportFragment();
                reportFragment = sizeReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, sizeReportFragment).commit();
                break;
            case rooms:
                RoomsReportFragment roomsReportFragment = new RoomsReportFragment();
                reportFragment = roomsReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, roomsReportFragment).commit();
                break;
            case garden:
                GardenReportFragment gardenReportFragment = new GardenReportFragment();
                reportFragment = gardenReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, gardenReportFragment).commit();
                break;
            case balcony:
                BalconyReportFragment balconyReportFragment = new BalconyReportFragment();
                reportFragment = balconyReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, balconyReportFragment).commit();
                break;
            case floor:
                FloorReportFragment floorReportFragment = new FloorReportFragment();
                reportFragment = floorReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, floorReportFragment).commit();
                break;
            case address:
                AddressReportFragment addressReportFragment = new AddressReportFragment();
                reportFragment = addressReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, addressReportFragment).commit();
                break;
            case animals:
                AnimalsReportFragment animalsReportFragment = new AnimalsReportFragment();
                reportFragment = animalsReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, animalsReportFragment).commit();
                break;
            case roomates:
                RoomatesReportFragment roomatesReportFragment = new RoomatesReportFragment();
                reportFragment = roomatesReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, roomatesReportFragment).commit();
                break;
            case warehouse:
                break;
            default:
                break;
        }

        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setBackgroundResource(android.R.drawable.btn_default);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportFragment!=null){
                    String content = reportFragment.getContent();
                    if(content != null){
                        System.out.println(content);
                      //networking
                    }
                    else{
                      //prompt to fill at least on field;
                    }
                }
            }
        });


    }
}
