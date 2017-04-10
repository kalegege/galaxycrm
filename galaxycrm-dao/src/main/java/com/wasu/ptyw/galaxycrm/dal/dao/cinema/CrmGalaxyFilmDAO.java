package com.wasu.ptyw.galaxycrm.dal.dao.cinema;

import java.util.List;

import com.wasu.ptyw.galaxy.dal.dao.GalaxyFilmDAO;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.dal.query.cinema.CrmGalaxyFilmQuery;


public interface CrmGalaxyFilmDAO extends GalaxyFilmDAO {

    List<GalaxyFilmDO> query(CrmGalaxyFilmQuery query) throws DAOException;
}
