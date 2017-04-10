package com.wasu.ptyw.galaxycrm.core.ao.cinema;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.wasu.ptyw.galaxy.common.dataobject.Result;
import com.wasu.ptyw.galaxy.common.dataobject.ResultCode;
import com.wasu.ptyw.galaxy.common.exception.MyException;
import com.wasu.ptyw.galaxy.core.ao.GalaxyFilmAO;
import com.wasu.ptyw.galaxy.dal.dataobject.GalaxyFilmDO;
import com.wasu.ptyw.galaxy.dal.query.GalaxyFilmQuery;
import com.wasu.ptyw.galaxycrm.core.manager.cinema.CrmGalaxyFilmManager;
import com.wasu.ptyw.galaxycrm.dal.query.cinema.CrmGalaxyFilmQuery;

@Service("crmGalaxyFilmAO")
@Slf4j
public class CrmGalaxyFilmAO extends GalaxyFilmAO {

    @Resource
    private CrmGalaxyFilmManager crmGalaxyFilmManager;
    
    public Result<List<GalaxyFilmDO>> query(CrmGalaxyFilmQuery query) {
        Result<List<GalaxyFilmDO>> result = new Result<List<GalaxyFilmDO>>(false);
        try {
            if (query == null) {
                return setErrorMessage(result, ResultCode.PARAM_INPUT_INVALID);
            }
            List<GalaxyFilmDO> list = crmGalaxyFilmManager.query(query);
            result.setValue(list);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("query error," + query, e);
            if (e instanceof MyException) {
                setErrorMessage(result, ((MyException) e).getResultCode());
            } else {
                setErrorMessage(result, ResultCode.SYSTEM_ERROR);
            }
        }
        return result;
    }
}
