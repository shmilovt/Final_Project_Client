package com.example.final_project_client.Presentation.Report;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.final_project_client.Communication.NetworkController;
import com.example.final_project_client.Communication.NetworkListener;
import com.example.final_project_client.Presentation.ApartmentFullDescriptionActivity;
import com.example.final_project_client.Presentation.ReportCategory;
import com.example.final_project_client.R;
import com.google.gson.Gson;

public class ReportActivity extends AppCompatActivity {
    private TextView reportActivityFieldTitle;
    private Field field;
    private TextView sourceText;
    private ReportFragment reportFragment;
    private TextView reportCategoryTitle;
    private String apartmentID;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent intent = getIntent();
        apartmentID = (intent.getExtras()).getString(ApartmentFullDescriptionActivity.APARTMENT_ID);
        sourceText = (TextView) findViewById(R.id.sourceTextReport);
        sourceText.setText(intent.getExtras().getString(ApartmentFullDescriptionActivity.SOURCE_TEXT));
        field = (new Gson()).fromJson(intent.getExtras().getString(ApartmentFullDescriptionActivity.CATEGORY), Field.class);
        reportCategoryTitle = (TextView) findViewById(R.id.categoryReportTitleTxt);
        reportActivityFieldTitle = (TextView)findViewById(R.id.reportActivityFieldTitle);

        switch (field) {
            case furniture:
                reportActivityFieldTitle.setText(R.string.furnitureFullDescription);
                FurnitureReportFragment furnitureReportFragment = new FurnitureReportFragment();
                reportFragment = furnitureReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, furnitureReportFragment).commit();
                break;
            case protectedSpace:
                reportActivityFieldTitle.setText(R.string.protectedSpaceFullDescription);
                ProtectedSpaceReportFragment protectedSpaceReportFragment = new ProtectedSpaceReportFragment();
                reportFragment = protectedSpaceReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, protectedSpaceReportFragment).commit();
                break;
            case cost:
                reportActivityFieldTitle.setText(R.string.cost);
                CostReportFragment costReportFragment = new CostReportFragment();
                reportFragment = costReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, costReportFragment).commit();
                break;
            case apartmentSize:
                reportActivityFieldTitle.setText(R.string.size);
                SizeReportFragment sizeReportFragment = new SizeReportFragment();
                reportFragment = sizeReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, sizeReportFragment).commit();
                break;
            case numRooms:
                reportActivityFieldTitle.setText(R.string.rooms);
                RoomsReportFragment roomsReportFragment = new RoomsReportFragment();
                reportFragment = roomsReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, roomsReportFragment).commit();
                break;
            case yard:
                reportActivityFieldTitle.setText(R.string.gardenFullDescription);
                GardenReportFragment gardenReportFragment = new GardenReportFragment();
                reportFragment = gardenReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, gardenReportFragment).commit();
                break;
            case balcony:
                reportActivityFieldTitle.setText(R.string.balconyFullDescription);
                BalconyReportFragment balconyReportFragment = new BalconyReportFragment();
                reportFragment = balconyReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, balconyReportFragment).commit();
                break;
            case floor:
                reportActivityFieldTitle.setText(R.string.floor);
                FloorReportFragment floorReportFragment = new FloorReportFragment();
                reportFragment = floorReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, floorReportFragment).commit();
                break;
            case address:
                reportActivityFieldTitle.setText(R.string.address);
                AddressReportFragment addressReportFragment = new AddressReportFragment();
                reportFragment = addressReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, addressReportFragment).commit();
                break;
            case animals:
                reportActivityFieldTitle.setText(R.string.animalsFullDescription);
                AnimalsReportFragment animalsReportFragment = new AnimalsReportFragment();
                reportFragment = animalsReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, animalsReportFragment).commit();
                break;
            case numRoomates:
                reportActivityFieldTitle.setText(R.string.roomates);
                RoomatesReportFragment roomatesReportFragment = new RoomatesReportFragment();
                reportFragment = roomatesReportFragment;
                (getSupportFragmentManager().beginTransaction()).add(R.id.reportForm, roomatesReportFragment).commit();
                break;
            case warehouse:
                reportActivityFieldTitle.setText(R.string.warehouseFullDescription);
                WarehouseReportFragment warehouseReportFragment = new WarehouseReportFragment();
                reportFragment = warehouseReportFragment;
                (getFragmentManager().beginTransaction()).add(R.id.reportForm, warehouseReportFragment).commit();
                break;
            default:
                break;
        }

        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setBackgroundResource(android.R.drawable.btn_default);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportFragment != null) {
                    String content = reportFragment.getContent();
                    if (content != null) {
                        System.out.println(content);
                        Report report = new Report(apartmentID, field, content);
                        sendReport(report);
                    } else {

                        buildDialogNotFillFields(ReportActivity.this).show();

                    }
                }
            }
        });
    }


    public synchronized void sendReport(Report report) {

        if (!hasNetwork()) {
            buildDialogNotNetwork(ReportActivity.this).show();
        } else {

            NetworkController.getInstance(this).sendReport(report, new NetworkListener<String>() {
                @Override
                public void getResult(String response) {

                    buildDialogSuccess(ReportActivity.this).show();

                }
            }, new NetworkListener<String>() {
                @Override
                public void getResult(String errorString) {
                    buildDialogProblemConnectingToServer(ReportActivity.this, errorString).show();
                }
            });


        }
    }


    private boolean hasNetwork() {
        return isConnected(ReportActivity.this);
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;

    }

    public AlertDialog.Builder buildDialogNotNetwork(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("אין חיבור אינטרנט");
        builder.setMessage("אינטרנט אינו פועל. על מנת לחפש תדאג\\י לחיבור אינטרנט.");
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }


    public AlertDialog.Builder buildDialogSuccess(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("הדיווח נשלח!");
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                hideKeyboard((Activity) context);
                ReportActivity.super.onBackPressed();
            }
        });

        return builder;
    }


    public AlertDialog.Builder buildDialogNotFillFields(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("לא נשלח");
        builder.setMessage("יש להקפיד למלא לפחות שדה מילוי אחד!");
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }


    public AlertDialog.Builder buildDialogProblemConnectingToServer(Context context, String errorString) {
        String message = "היישום אינו מצליח לתקשר עם השרת.";
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("בעיית חיבור לשרת");
        builder.setMessage(message + "\n" + errorString);
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
