package com.wasu.ptyw.galaxycrm.core.manager.cinema;

import java.util.List;

import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.core.manager.GalaxyFilmManager;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmDO;
import com.wasu.ptyw.galaxycrm.dal.query.cinema.CrmGalaxyFilmQuery;


public interface CrmGalaxyFilmManager extends GalaxyFilmManager {

    public List<GalaxyFilmDO> query(CrmGalaxyFilmQuery query) throws MyException;
}
