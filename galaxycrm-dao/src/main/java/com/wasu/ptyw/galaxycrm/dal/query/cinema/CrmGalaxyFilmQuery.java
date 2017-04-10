package com.wasu.ptyw.galaxycrm.dal.query.cinema;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.query.GalaxyFilmQuery;


@Alias("CrmGalaxyFilmQuery")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmGalaxyFilmQuery extends GalaxyFilmQuery {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public String likeName;
    
}
