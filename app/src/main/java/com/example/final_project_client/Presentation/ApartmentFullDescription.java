package com.example.final_project_client.Presentation;

import com.example.final_project_client.UserSearchingUtils.Contact;
import com.example.final_project_client.UserSearchingUtils.ResultRecord;

/**
 * Created by TAMIR on 5/1/2018.
 */

public class ApartmentFullDescription {
    private String address;
    private String neighborhood;
    private String cost;
    private String floor;
    private String date;
    private String numOfRooms;
    private String numOfRoomates;
    private String apartmentSize;
    private String distaceFromUniversity;
    private String contacts;
    private String txt;
    private String balcony;
    private String animals;
    private String warehouse;
    private String yard;
    private String protectedSpace;
    private String furniture;

    public ApartmentFullDescription(ResultRecord resultRecord) {
        address = resultRecord.getStreet() + " " + resultRecord.getNumber();
        neighborhood = resultRecord.getNeighborhood();
        cost = "" + resultRecord.getCost();
        floor = "" + resultRecord.getFloor();
        date = resultRecord.getDateOfPublish();
        numOfRooms = "" + resultRecord.getNumberOfRooms();
        numOfRoomates = "" + resultRecord.getNumberOfRoomates();
        apartmentSize = "" + resultRecord.getSize();
        distaceFromUniversity = "" + (int) resultRecord.getDistanceFromUniversity();
        txt = resultRecord.getText();
        contacts = "";
        if (resultRecord.isAnimals()) {
            animals = "מותר";
        } else {
            animals = "אסור";
        }
        if (resultRecord.isBalcony()) {
            balcony = "יש";
        } else {
            balcony = "אין";
        }
        if (resultRecord.isProtectedSpace()) {
            protectedSpace = "יש";
        } else {
            protectedSpace = "אין";
        }
        if (resultRecord.isWarehouse()) {
            warehouse = "יש";
        } else {
            warehouse = "אין";
        }
        if (resultRecord.isYard()) {
            yard = "יש";
        } else {
            yard = "אין";
        }
        switch (resultRecord.getFurniture()) {
            case 0:
                furniture = "אין";
                break;
            case 1:
                furniture = "ריהוט חלקי";
                break;
            case 2:
                furniture = "ריהוט מלא";
                break;
            default:
                furniture = "";
                break;
        }
        for (Contact contact : resultRecord.getContacts()) {
            if (!contact.getName().equals(""))
                contacts = contacts + contact.getName() + " : " + contact.getPhone() + "\n";
            else
                contacts = contacts + contact.getPhone() + "\n";

        }

    }

    public String toString() {
        String output;
        date = "תאריך פרסום: " + date;
        address = "כתובת: " + address;
        neighborhood = "שכונה: " + neighborhood;
        cost = "מחיר: " + cost;
        floor = "קומה: " + floor;
        numOfRoomates = "מספר שותפים: " + numOfRoomates;
        numOfRooms = "מספר חדרים: " + numOfRooms;
        apartmentSize = "גודל דירה: " + apartmentSize + " מ\"ר";
        distaceFromUniversity = "מרחק הליכה מאוניברסיטה: " + distaceFromUniversity + " דקות";
        contacts = "אנשי קשר: " + "\n" + contacts;
        txt = "מקור: " + "\n" + txt;
        balcony = "מרפסת: " + balcony;
        yard = "גינה/חצר: " + yard;
        protectedSpace = "מרחב מוגן: " + protectedSpace;
        animals = "חיות: " + animals;
        warehouse = "מחסן: " + warehouse;


        output = date + "\n\n" + address + "\n\n" + neighborhood + "\n\n" + floor + "\n\n" + cost + "\n\n" + numOfRoomates + "\n\n" + numOfRooms + "\n\n" + apartmentSize + "\n\n" + distaceFromUniversity
                + "\n\n" + balcony
                + "\n" + yard
                + "\n" + warehouse
                + "\n" + protectedSpace
                + "\n" + animals
                + "\n" + furniture;
        if (!furniture.equals("")) {
            output = output + "\n\n" + furniture;
        }

        output = output + "\n\n" + contacts + "\n\n" + txt;
        return output;
    }
}
