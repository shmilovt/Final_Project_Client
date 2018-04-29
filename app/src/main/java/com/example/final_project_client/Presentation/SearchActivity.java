package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.ULocale;
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
import com.example.final_project_client.DTOs.CategoryType;
import com.example.final_project_client.DTOs.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;
import com.google.gson.Gson;

import java.util.HashMap;


public class SearchActivity extends AppCompatActivity {

    private CategoriesManager categoriesManager;
    public static final String SEARCH_REASULTS = "com.example.final_project_client.SEARCH_RESULTS";


    public SearchActivity() {
        categoriesManager = new CategoriesManager(this);
        initialCategories(categoriesManager);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        NetworkController.getInstance(this);
        setContentView(R.layout.activity_search);


    }


    public synchronized void openResultsActivity(View view) {
        final SearchActivity searchActivity = this;
        if (!hasNetwork()) {
            buildDialogNotNetwork(SearchActivity.this).show();
        } else {
            UserSearch userSearch = new UserSearch();
            NetworkController.getInstance(this).searchApartments(userSearch, new NetworkListener<SearchResults>() {
                @Override
                public void getResult(SearchResults searchResults) {
                    System.out.println((new Gson()).toJson(searchResults));
                    Intent intent = new Intent(searchActivity, ResultsActivity.class);
                    intent.putExtra(SEARCH_REASULTS, (new Gson()).toJson(searchResults));
                    startActivity(intent);
                }
            }, new NetworkListener<String>() {
                @Override
                public void getResult(String errorString) {
                    buildDialogProblemConnectingToServer(SearchActivity.this, errorString).show();
                }
            });
        }
    }

    private boolean hasNetwork() {
        return isConnected(SearchActivity.this);
    }


    public synchronized void clickOnBtnAddCategory(View view) {
        AlertDialog.Builder alertDialog = onCreateDialogAddCategory(SearchActivity.this);
        alertDialog.show();

    }

    public synchronized void clickOnBtnRemoveCategory(View view) {
        onCreateDialogRemoveCategory(SearchActivity.this).show();
    }

    public AlertDialog.Builder onCreateDialogRemoveCategory(Context context) {
        String[] inusedCategories = categoriesManager.getPrintedNamesOfDisplayedCategories();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("הסרת קטגוריה")
                .setItems(inusedCategories, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        categoriesManager.removeCategory(which);
                    }
                }).setNegativeButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder;
    }


    public AlertDialog.Builder onCreateDialogAddCategory(Context context) {
        final String[] unusedCategories = categoriesManager.getPrintedNamesOfNotDisplayedCategories();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("הוספת קטגוריה")
                .setItems(unusedCategories, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        categoriesManager.addCategory(which);
                    }
                }).setNegativeButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        ;
        return builder;
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


    public AlertDialog.Builder buildDialogProblemConnectingToServer(Context context, String errorString) {
        String message = "היישום אינו מצליח לתקשר עם השרת.";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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


    private void initialCategories(CategoriesManager manager) {
        CostCategoryFragment costCategoryFragment = new CostCategoryFragment();
        FloorCategoryFragment floorCategoryFragment = new FloorCategoryFragment();
        NeighborhoodCategoryFragment neighborhoodCategoryFragment = new NeighborhoodCategoryFragment();
        DistanceFromUniversityCategoryFragment distanceFromUniversityCategoryFragment = new DistanceFromUniversityCategoryFragment();
        SizeCategoryFragment sizeCategoryFragment = new SizeCategoryFragment();
        NumberOfRoomsCategoryFragment numberOfRoomsCategoryFragment = new NumberOfRoomsCategoryFragment();
        NumberOfRoomatesCategoryFragment numberOfRoomatesCategoryFragment = new NumberOfRoomatesCategoryFragment();
        FurnitureCategoryFragment furnitureCategoryFragment = new FurnitureCategoryFragment();
        GardenCategoryFragment gardenCategoryFragment = new GardenCategoryFragment();
        ProtectedSpaceCategoryFragment protectedSpaceCategoryFragment = new ProtectedSpaceCategoryFragment();
        WarehouseCategoryFragment warehouseCategoryFragment = new WarehouseCategoryFragment();
        BalconyCategoryFragment balconyCategoryFragment = new BalconyCategoryFragment();
        AnimalsCategoryFragment animalsCategoryFragment = new AnimalsCategoryFragment();
        manager.addCategory(costCategoryFragment);
        manager.addCategory(floorCategoryFragment);
        manager.addCategory(neighborhoodCategoryFragment);
        manager.addCategory(distanceFromUniversityCategoryFragment);
        manager.addCategory(sizeCategoryFragment);
        manager.addCategory(numberOfRoomsCategoryFragment);
        manager.addCategory(numberOfRoomatesCategoryFragment);
        manager.addCategory(furnitureCategoryFragment);
        manager.addCategory(gardenCategoryFragment);
        manager.addCategory(protectedSpaceCategoryFragment);
        manager.addCategory(warehouseCategoryFragment);
        manager.addCategory(balconyCategoryFragment);
        manager.addCategory(animalsCategoryFragment);
    }
}


