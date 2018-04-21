package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.final_project_client.Communication.NetworkController;
import com.example.final_project_client.Communication.NetworkListener;
import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.CategoryType;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchActivity extends AppCompatActivity {
    private Button btnSearch;
    private HashMap<CategoryType, Fragment> availableCategories;
    private CategoryType [] displayedCategories = new CategoryType[13];
    private Integer[] levels = {R.id.level1, R.id.level2, R.id.level3, R.id.level4, R.id.level5, R.id.level6
    , R.id.level7, R.id.level8, R.id.level9, R.id.level10, R.id.level11, R.id.level12, R.id.level13 };

    public static final String SEARCH_REASULTS = "com.example.final_project_client.SEARCH_RESULTS";


    public SearchActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NetworkController.getInstance(this);
        setContentView(R.layout.activity_search);
        btnSearch = findViewById(R.id.BtnSearch);
        if(savedInstanceState==null){
            System.out.println("null save instance state!");
        availableCategories = new HashMap<>();
        CostCategoryFragment costCategoryFragment = new CostCategoryFragment();
        FloorCategoryFragment floorCategoryFragment = new FloorCategoryFragment();
        availableCategories.put(CategoryType.cost, costCategoryFragment);
        availableCategories.put(CategoryType.floor, floorCategoryFragment);
        getFragmentManager().beginTransaction().add(levels[0], costCategoryFragment).commit();
        displayedCategories[0] = CategoryType.cost;
        getFragmentManager().beginTransaction().add(levels[1], floorCategoryFragment).commit();
        displayedCategories[1] = CategoryType.floor;}
    }


    public void openResultsActivity(View view) {
        final SearchActivity searchActivity = this;
        checkNetwork();
        UserSearch userSearch = new UserSearch();
        NetworkController.getInstance(this).searchApartments(userSearch, new NetworkListener<SearchResults>() {
            @Override
            public void getResult(SearchResults searchResults) {
                System.out.println((new Gson()).toJson(searchResults));
                Intent intent = new Intent(searchActivity, ResultsActivity.class);
                intent.putExtra(SEARCH_REASULTS, (new Gson()).toJson(searchResults));
                startActivity(intent);
            }
        });

    }

    private void checkNetwork() {
        if (!isConnected(SearchActivity.this)) buildDialog(SearchActivity.this).show();
    }


    public AlertDialog.Builder buildDialog(Context context) {
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
}


