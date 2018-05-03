package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/28/2018.
 */

public enum DistanceFromUniversity {
    unknown(-1, "לא נבחר"),
    until5(5, "עד 5 דקות"),
    until10(10, "עד 10 דקות"),
    until15(15, "עד 15 דקות"),
    until20(20, "עד 20 דקות");

    private final int value;
    private final String name;

    private DistanceFromUniversity(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static DistanceFromUniversity findByName(String name) {
        for (DistanceFromUniversity distanceFromUniversity : DistanceFromUniversity.values()) {
            if (name.equals(distanceFromUniversity.getName()))
                return distanceFromUniversity;
        }
        return null;
    }


}
