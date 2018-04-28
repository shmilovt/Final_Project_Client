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
import com.example.final_project_client.DTOs.CategoryType;
import com.example.final_project_client.DTOs.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;
import com.google.gson.Gson;

import java.util.HashMap;


public class SearchActivity extends AppCompatActivity {
    private static final int TOTAL_AMOUNT_OF_CATEGORIES = 13;
    private int amountOfCategoriesInused;
    private Button btnSearch;
    private HashMap<CategoryType, Fragment> availableCategories;
    private HashMap<CategoryType, String> categoriesPrintNames;
    private CategoryType [] displayedCategories = new CategoryType[TOTAL_AMOUNT_OF_CATEGORIES];
    private Integer[] levels = {R.id.level1, R.id.level2, R.id.level3, R.id.level4, R.id.level5, R.id.level6
            , R.id.level7, R.id.level8, R.id.level9, R.id.level10, R.id.level11, R.id.level12, R.id.level13};




    public static final String SEARCH_REASULTS = "com.example.final_project_client.SEARCH_RESULTS";


    public SearchActivity() {
        initialCategoriesPrintName();
        amountOfCategoriesInused = 0;
    }

    private void initialCategoriesPrintName(){
        categoriesPrintNames = new HashMap<>();
        categoriesPrintNames.put(CategoryType.floor, "קומה");
        categoriesPrintNames.put(CategoryType.cost, "מחיר");
        categoriesPrintNames.put(CategoryType.apartmentSize, "גודל דירה (מ\"ר)");
        categoriesPrintNames.put(CategoryType.distanceFromUniversity, "מרחק מהאוניברסיטה (דקות הליכה)");
        categoriesPrintNames.put(CategoryType.furniture, "ריהוט");
        categoriesPrintNames.put(CategoryType.neighborhood, "שכונה");
        categoriesPrintNames.put(CategoryType.numRoomates, "מספר שותפים");
        categoriesPrintNames.put(CategoryType.numRooms, "מספר חדרים");
        categoriesPrintNames.put(CategoryType.protectedSpace, "מרחב מוגן");
        categoriesPrintNames.put(CategoryType.warehouse, "מחסן");
        categoriesPrintNames.put(CategoryType.yard, "גינה/חצר");
        categoriesPrintNames.put(CategoryType.balcony, "מרפסת");
        categoriesPrintNames.put(CategoryType.animals, "חיות");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NetworkController.getInstance(this);
        setContentView(R.layout.activity_search);
        btnSearch = findViewById(R.id.BtnSearch);
        if (savedInstanceState == null) {
            System.out.println("null save instance state!");
            availableCategories = new HashMap<>();
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
            availableCategories.put(CategoryType.cost, costCategoryFragment);
            availableCategories.put(CategoryType.floor, floorCategoryFragment);
            getFragmentManager().beginTransaction().add(levels[0], costCategoryFragment).commit();
            displayedCategories[0] = CategoryType.cost;
            getFragmentManager().beginTransaction().add(levels[1], floorCategoryFragment).commit();
            displayedCategories[1] = CategoryType.floor;
            getFragmentManager().beginTransaction().add(levels[2], neighborhoodCategoryFragment).commit();
            displayedCategories[2] = CategoryType.neighborhood;
            getFragmentManager().beginTransaction().add(levels[3], distanceFromUniversityCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[4], sizeCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[5], numberOfRoomsCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[6], numberOfRoomatesCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[7], furnitureCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[8], gardenCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[9], warehouseCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[10], protectedSpaceCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[11], animalsCategoryFragment).commit();
            getFragmentManager().beginTransaction().add(levels[12], balconyCategoryFragment).commit();
            amountOfCategoriesInused = 2;
        }
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

    private String [] getInUsedCategories(){
        String [] usedCategories = new String [amountOfCategoriesInused];
         for(int i=0; i<amountOfCategoriesInused; i++){
          usedCategories[i] = categoriesPrintNames.get(displayedCategories[i]);
        }
     return usedCategories;
    }

    private String [] getNotInUsedCategories(){
        String [] unusedCategories = new String [TOTAL_AMOUNT_OF_CATEGORIES - amountOfCategoriesInused];
        int i = 0 ;
        for(CategoryType categoryType : displayedCategories){
           if(categoryType!=null){
               unusedCategories[i] = categoriesPrintNames.get(categoryType);
               i++;
           }
        }
        return unusedCategories;
    }
    public synchronized void clickOnBtnAddCategory(View view) {
        AlertDialog.Builder alertDialog =  onCreateDialogAddCategory(SearchActivity.this);
        alertDialog.show();

    }

    public synchronized void clickOnBtnRemoveCategory(View view) {
        onCreateDialogRemoveCategory(SearchActivity.this).show();
    }

    public AlertDialog.Builder onCreateDialogRemoveCategory(Context context) {
        final String [] inusedCategories = getInUsedCategories();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("הסרת קטגוריה")
                .setItems(getInUsedCategories(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CategoryType selectedCategoryType  = null;
                        for(CategoryType categoryType: CategoryType.values()){
                            if(categoriesPrintNames.get(categoryType).equals(inusedCategories[which])) {
                                selectedCategoryType = categoryType;
                                break;
                            }
                        }

                         removeCategory(selectedCategoryType);
                    }
                }).setNegativeButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder;
    }

    private void removeCategory(CategoryType categoryType){

    }

    public AlertDialog.Builder onCreateDialogAddCategory(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("הוספת קטגוריה")
                .setItems(getNotInUsedCategories(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                }).setNegativeButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });;
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

    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}


