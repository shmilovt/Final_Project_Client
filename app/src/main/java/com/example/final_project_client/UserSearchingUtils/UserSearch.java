package com.example.final_project_client.UserSearchingUtils;

import com.example.final_project_client.DTOs.CategoryType;
import com.example.final_project_client.Presentation.Search.AnimalsCategoryFragment;
import com.example.final_project_client.Presentation.Search.BalconyCategoryFragment;
import com.example.final_project_client.Presentation.Search.CostCategoryFragment;
import com.example.final_project_client.Presentation.Search.DistanceFromUniversityCategoryFragment;
import com.example.final_project_client.Presentation.Search.FloorCategoryFragment;
import com.example.final_project_client.Presentation.Search.FurnitureCategoryFragment;
import com.example.final_project_client.Presentation.Search.GardenCategoryFragment;
import com.example.final_project_client.Presentation.Search.NeighborhoodCategoryFragment;
import com.example.final_project_client.Presentation.Search.NumberOfRoomatesCategoryFragment;
import com.example.final_project_client.Presentation.Search.NumberOfRoomsCategoryFragment;
import com.example.final_project_client.Presentation.Search.ProtectedSpaceCategoryFragment;
import com.example.final_project_client.Presentation.Search.SizeCategoryFragment;
import com.example.final_project_client.Presentation.Search.WarehouseCategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class UserSearch {

    private List<CategoryType> priorities;
    private String neighborhood;
    private DistanceFromUniversity distanceFromUniversity;
    private Floor floor;
    private Cost cost;
    private Size size;
    private Furniture furniture;
    private NumberOfRooms numberOfRooms;
    private NumberOfRoomates numberOfMates;



    public UserSearch() {
        priorities = new ArrayList<>();
        neighborhood = "";
        distanceFromUniversity = DistanceFromUniversity.unknown;
        floor = new Floor(-1, -1);
        cost = new Cost(-1, -1);
        size = new Size(-1, -1);
        furniture = Furniture.unknown;
        numberOfRooms = NumberOfRooms.unknown;
        numberOfMates = NumberOfRoomates.unknown;
    }


    public CategoryType[] getPriorities() {

        CategoryType [] categoryTypes = new CategoryType[priorities.size()];
        int i=0;
        for(CategoryType categoryType: priorities){
            categoryTypes[i] = categoryType;
            i++;
        }
        return categoryTypes;
    }


    public void setPriorities(List<CategoryType> priorities) {
        this.priorities = priorities;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public DistanceFromUniversity getDistanceFromUniversity() {
        return distanceFromUniversity;
    }

    public void setDistanceFromUniversity(DistanceFromUniversity distanceFromUniversity) {
        this.distanceFromUniversity = distanceFromUniversity;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public NumberOfRooms getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(NumberOfRooms numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public NumberOfRoomates getNumberOfMates() {
        return numberOfMates;
    }

    public void setNumberOfMates(NumberOfRoomates numberOfMates) {
        this.numberOfMates = numberOfMates;
    }


    public void addCategory(AnimalsCategoryFragment animalsCategoryFragment) {
            priorities.add(CategoryType.animals);
    }

    public void addCategory(BalconyCategoryFragment balconyCategoryFragment) {
        priorities.add(CategoryType.balcony);
    }

    public void addCategory(ProtectedSpaceCategoryFragment protectedSpaceCategoryFragment) {
        priorities.add(CategoryType.protectedSpace);
    }

    public void addCategory(WarehouseCategoryFragment warehouseCategoryFragment) {
        priorities.add(CategoryType.warehouse);
    }

    public void addCategory(GardenCategoryFragment gardenCategoryFragment) {
        priorities.add(CategoryType.animals);
    }

    public void addCategory(CostCategoryFragment costCategoryFragment) {
        int maxCost, minCost;
        String maxCostString = costCategoryFragment.getMaxCost();
        String minCostString = costCategoryFragment.getMinCost();
        if( maxCostString == null || maxCostString.equals(""))
            maxCost = -1;
        else{
            maxCost = Integer.parseInt(maxCostString);
        }
        if( minCostString == null || minCostString.equals(""))
            minCost = -1;
        else{
            minCost = Integer.parseInt(minCostString);
        }

        if(minCost!=-1 || maxCost!=-1){
            priorities.add(CategoryType.cost);
            cost = new Cost(minCost, maxCost);
        }
        else{
            cost = new Cost(-1, -1);
        }
    }

    public void addCategory(FloorCategoryFragment floorCategoryFragment) {
        int maxFloor, minFloor;
        String maxFloorString = floorCategoryFragment.getMaxFloor();
        String minFloorString = floorCategoryFragment.getMinFloor();
        if( maxFloorString == null || maxFloorString.equals(""))
            maxFloor = -1;
        else{
            maxFloor = Integer.parseInt(maxFloorString);
        }
        if( minFloorString == null || minFloorString.equals(""))
            minFloor = -1;
        else{
            minFloor = Integer.parseInt(minFloorString);
        }

        if(minFloor!=-1 || maxFloor!=-1){
            priorities.add(CategoryType.floor);
            floor = new Floor(minFloor, maxFloor);
        }

             else{
               floor = new Floor(-1, -1);
            }

    }

    public void addCategory(SizeCategoryFragment sizeCategoryFragment) {

        int maxSize, minSize;
        String maxSizeString = sizeCategoryFragment.getMaxSize();
        String minSizeString = sizeCategoryFragment.getMinSize();
        if( maxSizeString == null || maxSizeString.equals(""))
            maxSize = -1;
        else{
            maxSize = Integer.parseInt(maxSizeString);
        }
        if( minSizeString == null || minSizeString.equals(""))
            minSize = -1;
        else{
            minSize = Integer.parseInt(minSizeString);
        }

        if(minSize!=-1 || maxSize!=-1){
            priorities.add(CategoryType.apartmentSize);
            size = new Size(minSize, maxSize);
        }
        else{
            size = new Size(-1, -1);
        }
    }




    public void addCategory(NeighborhoodCategoryFragment neighborhoodCategoryFragment) {
        Neighborhood selectedNeighborhood = Neighborhood.findByName(neighborhoodCategoryFragment.getSelectedItem());
        if(selectedNeighborhood==null || selectedNeighborhood.equals(Neighborhood.unknown)){
            neighborhood = "";
        }
          else {
            priorities.add(CategoryType.neighborhood);
            neighborhood = selectedNeighborhood.getValue();
        }

    }


    public void addCategory(DistanceFromUniversityCategoryFragment distanceFromUniversityCategoryFragment) {
        DistanceFromUniversity selectedDistanceFromUniversity = DistanceFromUniversity.findByName(  distanceFromUniversityCategoryFragment.getSelectedItem());

        if(selectedDistanceFromUniversity == null || selectedDistanceFromUniversity.equals(DistanceFromUniversity.unknown)){
            distanceFromUniversity = DistanceFromUniversity.unknown;
        }
           else{
            distanceFromUniversity = selectedDistanceFromUniversity;
            priorities.add(CategoryType.distanceFromUniversity);
        }


    }

    public void addCategory(NumberOfRoomatesCategoryFragment numberOfRoomatesCategoryFragment) {
       NumberOfRoomates selectedNumberOfRoomates = NumberOfRoomates.findByName(  numberOfRoomatesCategoryFragment.getSelectedItem());
        if(selectedNumberOfRoomates == null || selectedNumberOfRoomates.equals(NumberOfRoomates.unknown)){
            numberOfMates = NumberOfRoomates.unknown;
        }
        else{
            priorities.add(CategoryType.numRoomates);
            numberOfMates = selectedNumberOfRoomates;
        }


    }

    public void addCategory(NumberOfRoomsCategoryFragment roomsCategoryFragment) {

        NumberOfRooms selectedNumberOfRooms = NumberOfRooms.findByName(roomsCategoryFragment.getSelectedItem());
        if(selectedNumberOfRooms == null || selectedNumberOfRooms.equals(NumberOfRooms.unknown)){
            numberOfRooms = NumberOfRooms.unknown;
        }
        else{
            priorities.add(CategoryType.numRooms);
            numberOfRooms = selectedNumberOfRooms;
        }



    }


    public void addCategory(FurnitureCategoryFragment furnitureCategoryFragment) {
        Furniture selectedFurniture = Furniture.findByName(furnitureCategoryFragment.getSelectedItem());
        if(selectedFurniture == null || selectedFurniture.equals(Furniture.unknown)){
            furniture = Furniture.unknown;
        }
         else{
            priorities.add(CategoryType.furniture);
            furniture = selectedFurniture;
        }


    }

    public String toString (){
        String print = "";
        for(CategoryType categoryType : priorities)
            print = print + printCategory(categoryType) + "\n";
        return print;
    }

    private String printCategory(CategoryType categoryType){
        switch (categoryType){
            case animals:
                return "animals: yes";
            case balcony:
                return "balcony: yes";
            case yard:
                return "garden: yes";
            case warehouse:
                return "warehouse: yes";
            case protectedSpace:
                return "protectedSpace: yes";
            case cost:
                return "costDTO: "+ cost.getMinCost()+" - "+ cost.getMaxCost();
            case apartmentSize:
                return "sizeDTO: "+ size.getMinSize()+" - "+ size.getMaxSize();
            case floor:
                return "floorDTO: "+ floor.getMinFloor()+" - "+ floor.getMaxFloor();
            case neighborhood:
                return "neighborhood: "+neighborhood;
            case numRooms:
                return "rooms: "+numberOfRooms.getName()+" "+numberOfRooms.getValue();
            case furniture:
                return "furniture: "+furniture.getName()+" "+furniture.getValue();
            case numRoomates:
                return "roomates: "+numberOfMates.getName()+" "+numberOfMates.getValue();
            case distanceFromUniversity:
                return "distanceFromUniversity: "+distanceFromUniversity.getName()+" "+distanceFromUniversity.getValue();
                default:
                    return "unrecognize";

        }
    }

}
