package com.example.final_project_client.DTOs;

/**
 * Created by TAMIR on 4/27/2018.
 */

public class ResultRecordDTO {


    private String apartmentID;
    private String street;
    private int number;
    private String neighborhood;
    private int floor;
    private double distanceFromUniversity;
    private int cost;
    private int size;
    private boolean balcony;
    private boolean yard;
    private boolean animals;
    private boolean warehouse ;
    private boolean protectedSpace;
    private int furniture;
    private double numberOfRooms;
    private int numberOfRoomates;
    private String dateOfPublish; //just date without time
    private String text;
    private ContactDTO [] contacts;
    private double lat; // קווי אורך
    private double lon; // קווי רוחב

    public ResultRecordDTO(){}


    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getDistanceFromUniversity() {
        return distanceFromUniversity;
    }

    public void setDistanceFromUniversity(double distanceFromUniversity) {
        this.distanceFromUniversity = distanceFromUniversity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isYard() {
        return yard;
    }

    public void setYard(boolean yard) {
        this.yard = yard;
    }

    public boolean isAnimals() {
        return animals;
    }

    public void setAnimals(boolean animals) {
        this.animals = animals;
    }

    public boolean isWarehouse() {
        return warehouse;
    }

    public void setWarehouse(boolean warehouse) {
        this.warehouse = warehouse;
    }

    public boolean isProtectedSpace() {
        return protectedSpace;
    }

    public void setProtectedSpace(boolean protectedSpace) {
        this.protectedSpace = protectedSpace;
    }

    public int getFurniture() {
        return furniture;
    }

    public void setFurniture(int furniture) {
        this.furniture = furniture;
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

    public String getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(String dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ContactDTO[] getContacts() {
        return contacts;
    }

    public void setContacts(ContactDTO[] contacts) {
        this.contacts = contacts;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
