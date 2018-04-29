package com.example.final_project_client.Presentation;

import android.app.Activity;
import android.content.Context;

import com.example.final_project_client.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by TAMIR on 4/29/2018.
 */

public class CategoriesManager {
    private static int [] levels = {R.id.level1, R.id.level2, R.id.level3, R.id.level4, R.id.level5, R.id.level6
            , R.id.level7, R.id.level8, R.id.level9, R.id.level10, R.id.level11, R.id.level12, R.id.level13};
    private int numOfDisplayedCategories;
    private int totalNumberOfCategories;
    private List<CategoryFragment> displayedCategories;
    private List<CategoryFragment>  notDisplayedCategories;
    private Context context;

    public CategoriesManager(Context context){
        numOfDisplayedCategories = 0;
        totalNumberOfCategories = 0;
        displayedCategories = new ArrayList<>();
        notDisplayedCategories = new ArrayList<>();
        this.context = context;
    }

    public void addCategory(CategoryFragment categoryFragment){
        notDisplayedCategories.add(categoryFragment);
        totalNumberOfCategories++;
    }

   public String[] getPrintedNamesOfDisplayedCategories(){
        String [] categoriesNames = new String[numOfDisplayedCategories];
        for(int i=0; i<numOfDisplayedCategories; i++){
            categoriesNames[i] = displayedCategories.get(i).getName();
        }
        return categoriesNames;
    }

    public String[] getPrintedNamesOfNotDisplayedCategories(){
        String [] categoriesNames = new String[notDisplayedCategories.size()];
        int i=0;
        for(CategoryFragment categoryFragment : notDisplayedCategories){
            categoriesNames[i] = categoryFragment.getName();
            i++;
        }
        return categoriesNames;
    }

    public void removeCategory(int categoryIndex){
        CategoryFragment choosenCategory = displayedCategories.get(categoryIndex);
        int lastPriorityBeforeRemove = numOfDisplayedCategories;
        int priorityOfRemovedCategory = choosenCategory.getPriority();

        numOfDisplayedCategories--;
        displayedCategories.remove(choosenCategory);
        choosenCategory.setPriority(-1);
        ((Activity)context).getFragmentManager().beginTransaction().remove(choosenCategory).commit();

        if(lastPriorityBeforeRemove != priorityOfRemovedCategory) {
            ((Activity) context).getFragmentManager().beginTransaction().remove(displayedCategories.get(displayedCategories.size()-1)).commit();

            int j = numOfDisplayedCategories-1;
            for (int i = lastPriorityBeforeRemove; i>priorityOfRemovedCategory ;i-- ){
                System.out.println(j);
                ((Activity) context).getFragmentManager().beginTransaction().remove(displayedCategories.get(j)).commit();
                ((Activity) context).getFragmentManager().executePendingTransactions();
                ((Activity) context).getFragmentManager().beginTransaction().replace(levels[j] , displayedCategories.get(j)).commit();
                displayedCategories.get(j).setPriority(displayedCategories.get(j).getPriority()-1);
                j--;
            }

        }

        notDisplayedCategories.add(choosenCategory);


    }

    public void addCategory(int categoryIndex){
        CategoryFragment choosenCategory = notDisplayedCategories.get(categoryIndex);
           numOfDisplayedCategories++;
           notDisplayedCategories.remove(choosenCategory);
           displayedCategories.add(choosenCategory);
           choosenCategory.setPriority(numOfDisplayedCategories);
           ((Activity)context).getFragmentManager().beginTransaction().add(levels[numOfDisplayedCategories-1], choosenCategory).commit();


    }



}
