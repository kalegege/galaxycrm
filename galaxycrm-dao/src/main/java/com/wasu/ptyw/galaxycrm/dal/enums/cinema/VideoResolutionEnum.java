package com.wasu.ptyw.galaxycrm.dal.enums.cinema;

import lombok.Getter;

public enum VideoResolutionEnum {
    
    NONE(-1,""),
    SD(0, "标清"), 
    HD(1, "高清");
    
    private @Getter Integer code;
    
    private @Getter String name;
    
    private VideoResolutionEnum(Integer code,String name){
        this.code=code;
        this.name=name;
    }
    
    public static VideoResolutionEnum get(Integer code) {
        for (VideoResolutionEnum e : VideoResolutionEnum.values()) {
            if (e.getCode()==code)
                return e;
        }
        return null;
    }
    
    public static VideoResolutionEnum getByName(String name) {
        for (VideoResolutionEnum e : VideoResolutionEnum.values()) {
            if (e.name().equals(name))
                return e;
        }
        return null;
    }
}
