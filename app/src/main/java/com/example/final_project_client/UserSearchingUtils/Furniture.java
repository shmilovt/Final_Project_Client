package com.example.final_project_client.UserSearchingUtils;

public enum Furniture {
    unknown(-1, "לא נבחר"),
    without(0, "ללא ריהוט"),
    partial(1, "ריהוט חלקי"),
    full(2, "ריהוט מלא");



    private final int value;
    private final String name;
    private Furniture(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Furniture findByName(String name) {
        for(Furniture furniture: Furniture.values()){
            if(furniture.name.equals(name))
                return furniture;
        }
        return null;
    }
}
