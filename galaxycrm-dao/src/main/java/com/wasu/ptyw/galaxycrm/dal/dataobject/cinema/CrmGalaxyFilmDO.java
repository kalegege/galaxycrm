package com.wasu.ptyw.galaxycrm.dal.dataobject.cinema;


import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.ibatis.type.Alias;

import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmDO;

@Alias("CrmGalaxyFilmDO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CrmGalaxyFilmDO extends GalaxyFilmDO {

}
