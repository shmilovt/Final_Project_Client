package com.example.final_project_client.DTOs;

import com.example.final_project_client.UserSearchingUtils.UserSearch;
import com.google.gson.Gson;

public class UserSearchDTO {

    private CategoryType[] priorities;
    private String neighborhood;
    private DistanceFromUniversity distanceFromUniversity;
    private Floor floor;
    private Cost cost;
    private Size size;
    private Furniture furniture;
    private double numberOfRooms;
    private NumberOfRoomates numberOfMates;
    private boolean balcony;
    private boolean animals;
    private boolean protectedSpace;
    private boolean yard;
    private boolean warehouse;

    public UserSearchDTO(){}
    public UserSearchDTO(UserSearch userSearch){
        priorities = userSearch.getPriorities();
        neighborhood = userSearch.getNeighborhood();
        distanceFromUniversity = userSearch.getDistanceFromUniversity();
        floor = userSearch.getFloor();
        cost = userSearch.getCost();
        furniture = userSearch.getFurniture();
        numberOfRooms = userSearch.getNumberOfRooms();
        numberOfMates = userSearch.getNumberOfMates();
        balcony = userSearch.isBalcony();
        animals =  userSearch.isAnimals();
        protectedSpace = userSearch.isProtectedSpace();
        yard = userSearch.isYard();
        warehouse = userSearch.isWarehouse();
        size = userSearch.getSize();
    }



    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public CategoryType[] getPriorities() {
        return priorities;
    }


    public void setPriorities(CategoryType[] priorities) {
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

    public double getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(double numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public NumberOfRoomates getNumberOfMates() {
        return numberOfMates;
    }

    public void setNumberOfMates(NumberOfRoomates numberOfMates) {
        this.numberOfMates = numberOfMates;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isAnimals() {
        return animals;
    }

    public void setAnimals(boolean animals) {
        this.animals = animals;
    }

    public boolean isProtectedSpace() {
        return protectedSpace;
    }

    public void setProtectedSpace(boolean protectedSpace) {
        this.protectedSpace = protectedSpace;
    }

    public boolean isYard() {
        return yard;
    }

    public void setYard(boolean yard) {
        this.yard = yard;
    }

    public boolean isWarehouse() {
        return warehouse;
    }

    public void setWarehouse(boolean warehouse) {
        this.warehouse = warehouse;
    }
}
