package com.wasu.ptyw.galaxycrm.dal.enums.cinema;

import lombok.Getter;


public enum AssetTypeEnum {

    NONE(-1,""),
    MOVIETRAILER(13,"预告片"),
    MOVIE(36,"电影");
    
    private @Getter Integer code;
    
    private @Getter String name;
    
    private AssetTypeEnum(Integer code,String name){
        this.code=code;
        this.name=name;
    }
    
    public static AssetTypeEnum get(Integer code) {
        for (AssetTypeEnum e : AssetTypeEnum.values()) {
            if (e.getCode()==code)
                return e;
        }
        return NONE;
    }
    
    public static AssetTypeEnum getByName(String name) {
        for (AssetTypeEnum e : AssetTypeEnum.values()) {
            if (e.name().equals(name))
                return e;
        }
        return NONE;
    }
    
}
