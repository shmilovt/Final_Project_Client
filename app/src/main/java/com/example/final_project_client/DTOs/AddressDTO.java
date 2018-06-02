package com.example.final_project_client.DTOs;

public class AddressDTO {
    private String street;
    private Integer numOfBuilding;
    private String neighborhood;

    public AddressDTO(){}

    public AddressDTO(String street, Integer numOfBuilding, String neighborhood) {
        this.street = street;
        this.numOfBuilding = numOfBuilding;
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumOfBuilding() {
        return numOfBuilding;
    }

    public void setNumOfBuilding(Integer numOfBuilding) {
        this.numOfBuilding = numOfBuilding;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}