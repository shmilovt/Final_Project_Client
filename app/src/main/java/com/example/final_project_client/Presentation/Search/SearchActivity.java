package com.example.final_project_client.Presentation.Search;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.final_project_client.Communication.NetworkController;
import com.example.final_project_client.Communication.NetworkListener;
import com.example.final_project_client.DTOs.SearchResultsDTO;
import com.example.final_project_client.Presentation.Results.DataHolder;
import com.example.final_project_client.Presentation.Results.ResultsActivity;
import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;


public class SearchActivity extends AppCompatActivity {

    private CategoriesManager categoriesManager;


    public SearchActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        NetworkController.getInstance(this);
        categoriesManager = CategoriesManager.getInstance(SearchActivity.this);
        if (categoriesManager.isOnFirstLaunch()) {
            initialCategories(categoriesManager);


            categoriesManager.displayCategory(new NeighborhoodCategoryFragment());
            categoriesManager.displayCategory(new CostCategoryFragment());
            categoriesManager.setOnFirstLaunch(false);
        } else {
            CategoriesManager.getInstance(this).setContext(this);
            categoriesManager = CategoriesManager.getInstance(this);
            categoriesManager.restoreCategories();
        }

    }


    public synchronized void openResultsActivity(View view) {
        final SearchActivity searchActivity = this;
        if (!hasNetwork()) {
            buildDialogNotNetwork(SearchActivity.this).show();
        } else {

            UserSearch userSearch = categoriesManager.convertToUserSearch();
            NetworkController.getInstance(this).searchApartments(userSearch, new NetworkListener<SearchResults>() {
                @Override
                public void getResult(SearchResults searchResults) {
                    Intent intent = new Intent(searchActivity, ResultsActivity.class);
                    //intent.putExtra(SEARCH_REASULTS, (new Gson()).toJson(searchResults));
                    DataHolder.getInstance().setIndex(0);
                    DataHolder.getInstance().setResultRecords(searchResults.getResultRecords());
                    DataHolder.getInstance().setOnFirstLaunch(true);
                    startActivity(intent);
                }
            }, new NetworkListener<String>() {
                @Override
                public void getResult(String errorString) {
                    buildDialogProblemConnectingToServer(SearchActivity.this, errorString).show();
                }
            });



        /*   String jsonString =
                    "{\"resultRecordDTOS\":[{\"apartmentID\":\"13\",\"street\":\"אלעזר בן יאיר\",\"number\":16,\"neighborhood\":\"שכונה ד\u0027\",\"floor\":-2,\"distanceFromUniversity\":18.0,\"cost\":1150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":4.0,\"numberOfRoomates\":3,\"dateOfPublish\":\"Tue May 01 05:38:46 IDT 2018\",\"text\":\"השותפה המקסימה שלנו Yarden Peretz עוזבת את הדירה. אני וGal Ben Maman מחפשים מישהי שתחליף את מקומה בחדר. דירת 4 חדרים חדשה משופצת לחלוטין עם חצר ענקית. הדירה באלעזר בן יאיר 16 כרבע שעה מאוניברסיטה. הדירה מרוהטת ויש בה הכל, רק להביא בגדים ולהכנס. עלות 1150 ש\\\"ח. כניסה מיידית :)\\nלפרטים:\\n0526516656\\n\",\"contacts\":[{\"name\":\"\",\"phone\":\"0526516656\"}],\"lat\":31.2628471,\"lon\":34.7904706}, "+
                            "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706}]}";
            System.out.println(jsonString);
            SearchResultsDTO searchResultsDTO = SearchResultsDTO.fromJSON(jsonString);
            SearchResults searchResults = new SearchResults(searchResultsDTO);
            Intent intent = new Intent(searchActivity, ResultsActivity.class);
            DataHolder.getInstance().setIndex(0);
            DataHolder.getInstance().setResultRecords(searchResults.getResultRecords());
            DataHolder.getInstance().setOnFirstLaunch(true);
            startActivity(intent);
*/
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
                        categoriesManager.displayCategory(which);
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
        builder.setMessage("אינטרנט אינו פועל. על מנת לחפש יש לוודא חיבור לאינטרנט.");
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
        manager.addCategory(neighborhoodCategoryFragment);
        manager.addCategory(costCategoryFragment);
        manager.addCategory(distanceFromUniversityCategoryFragment);
        manager.addCategory(numberOfRoomatesCategoryFragment);
        manager.addCategory(floorCategoryFragment);
        manager.addCategory(sizeCategoryFragment);
        manager.addCategory(numberOfRoomsCategoryFragment);
        manager.addCategory(furnitureCategoryFragment);
        manager.addCategory(gardenCategoryFragment);
        manager.addCategory(protectedSpaceCategoryFragment);
        manager.addCategory(warehouseCategoryFragment);
        manager.addCategory(balconyCategoryFragment);
        manager.addCategory(animalsCategoryFragment);

    }


}


