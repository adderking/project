package com.zxm.load;


public enum  Direction {

    WEST("西方"),
    EAST("东方"),
    NORTH("北方"),
    SOUTH("南方"),

    NORTH_TO_SOUTH("北向南"),
    SOUTH_TO_NORTH("南向北"),
    EAST_TO_WEST("东向西"),
    WEST_TO_EAST("西向东");

    private String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Direction val(String operate) {
        for(Direction s : values()) {    //values()方法返回enum实例的数组
            if(operate.equals(s.getValue()))
                return s;
        }
        return null;
    }

}
