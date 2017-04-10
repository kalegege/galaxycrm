package com.wasu.ptyw.galaxycrm.core.manager.cinema.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.core.manager.impl.GalaxyFilmManagerImpl;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmDO;
import com.wasu.ptyw.galaxy.dal.persist.DAOException;
import com.wasu.ptyw.galaxycrm.core.manager.cinema.CrmGalaxyFilmManager;
import com.wasu.ptyw.galaxycrm.dal.dao.cinema.CrmGalaxyFilmDAO;
import com.wasu.ptyw.galaxycrm.dal.query.cinema.CrmGalaxyFilmQuery;


@Service("crmGalaxyFilmManager")
public class CrmGalaxyFilmManagerImpl extends GalaxyFilmManagerImpl implements CrmGalaxyFilmManager {
    
    @Resource
    private CrmGalaxyFilmDAO crmGalaxyFilmDao;
    
    @Override
    public List<GalaxyFilmDO> query(CrmGalaxyFilmQuery query) throws MyException {
        try {
            if (query.getPageSize() < Integer.MAX_VALUE) {
                int count = crmGalaxyFilmDao.queryCount(query);
                if (count == 0) {
                    return Lists.newArrayList();
                }
                query.setTotalItem(count);
            }
            return crmGalaxyFilmDao.query(query);
        } catch (DAOException e) {
            throw new MyException(ResultCode.DAO_DEF_EXCEPTION, e, query);
        }
    }
    
}
