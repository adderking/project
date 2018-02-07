package com.scisdata.web.enumeration;

public enum DirectionEnum {

    NORTH_TO_SOUTH("北向南"),
    SOUTH_TO_NORTH("南向北"),
    EAST_TO_WEST("东向西"),
    WEST_TO_EAST("西向东");

    private String value;

    DirectionEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static DirectionEnum val(String operate) {
        for(DirectionEnum s : values()) {    //values()方法返回enum实例的数组
            if(operate.equals(s.getValue()))
                return s;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(DirectionEnum.val("东向西"));
    }

}
