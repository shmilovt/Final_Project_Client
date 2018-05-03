package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/21/2018.
 */

public enum NumberOfRoomates {
    unknown(-1, "לא נבחר"),
    couple(0, "זוג"),
    one(1, "ללא שותפים"),
    two(2, "2 שותפים"),
    three(3, "3 שותפים"),
    four(4, "4 שותפים"),
    fiveOrMore(5, "5 שותפים ומעלה");


    private final int value;
    private final String name;
    private NumberOfRoomates(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static NumberOfRoomates findByName(String name) {
        for(NumberOfRoomates numberOfRoomates : NumberOfRoomates.values()){
            if(numberOfRoomates.getName().equals(name))
                return numberOfRoomates;
        }
        return null;
    }
}

