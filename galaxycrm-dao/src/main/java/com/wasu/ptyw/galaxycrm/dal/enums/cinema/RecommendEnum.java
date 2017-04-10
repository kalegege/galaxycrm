package com.wasu.ptyw.galaxycrm.dal.enums.cinema;

import lombok.Getter;


public enum RecommendEnum {

    NONE(0,""),
    TODAY(1,"今日推荐"),
    REYING(2,"院线热映"),
    BOOKING(3,"在线购票");
    
    private @Getter Integer code;
    
    private @Getter String name;
    
    private RecommendEnum(Integer code,String name){
        this.code=code;
        this.name=name;
    }
    
    public static RecommendEnum get(Integer code) {
        for (RecommendEnum e : RecommendEnum.values()) {
            if (e.getCode()==code)
                return e;
        }
        return NONE;
    }
    
    public static RecommendEnum getByName(String name) {
        for (RecommendEnum e : RecommendEnum.values()) {
            if (e.name().equals(name))
                return e;
        }
        return NONE;
    }
}
