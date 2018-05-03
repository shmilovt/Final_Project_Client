package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/29/2018.
 */

public enum NumberOfRooms{
    unknown(-1, "לא נבחר"),
    one(1, "דירת יחיד"),
    two(2, "2 חדרים"),
    three(3, "3 חדרים"),
    four(4, "4 חדרים"),
    five(5, "5 חדרים"),
    sixAndMore(6, "6 חדרים ומעלה");

    private final int value;
    private final String name;
    private NumberOfRooms(int value, String name) {
        this.value = value;
        this.name=  name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    public static NumberOfRooms findByName(String name) {
        for(NumberOfRooms numberOfRooms : NumberOfRooms.values()){
            if(numberOfRooms.getName().equals(name))
                return numberOfRooms;
        }
        return null;
    }
}
