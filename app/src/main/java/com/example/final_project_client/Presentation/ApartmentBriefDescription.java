package com.example.final_project_client.Presentation;

/**
 * Created by TAMIR on 5/23/2018.
 */

public class ApartmentBriefDescription {
    private int cost;
    private String neighborhood;
    private String street;
    private int buildingNumber;
    private double numberOfRooms;
    private int numberOfRoomates;


    public ApartmentBriefDescription(int cost, String neighborhood, String street, int buildingNumber, double numberOfRooms, int numberOfRoomates) {
        this.cost = cost;
        this.neighborhood = neighborhood;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.numberOfRooms = numberOfRooms;
        this.numberOfRoomates = numberOfRoomates;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public double getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(double numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfRoomates() {
        return numberOfRoomates;
    }

    public void setNumberOfRoomates(int numberOfRoomates) {
        this.numberOfRoomates = numberOfRoomates;
    }
}
