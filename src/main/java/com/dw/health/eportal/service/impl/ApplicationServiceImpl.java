package com.dw.health.eportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.ApplicationMapper;
import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.entity.dto.ApplicationDTO;
import com.dw.health.eportal.service.ApplicationService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;

/**
 * Created by Administrator on 2019-2-20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApplicationServiceImpl implements ApplicationService{
    @Autowired
    private ApplicationMapper appMapper;
    @Override
    public void saveApplication(Application app) {
        Application application = appMapper.selectOne(new Application().setAppName(app.getAppName()));
        if (application != null){
            throw new BizException("该应用已经存在");
        }
        appMapper.insertSelective(BaseEntityUtil.insertEntity(app));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Application> listAppByType(int currentPage,int pageSize,String type) {
        PageHelper.startPage(currentPage,pageSize);
        List<Application> appList = appMapper.select(new Application().setType(type));
        return appList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int countAppByType(String type) {
        return appMapper.selectCount(new Application().setType(type));
    }

    @Override
    public void deleteAppList(List<Application> appList) {
        for (Application application : appList) {
            appMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(application));
        }
    }

    @Override
    public void updateApplication(Application app) {
        appMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(app));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ApplicationDTO> listAppDTO(ApplicationDTO appDTO) {
        PageHelper.startPage(appDTO.getCurrentPage(), appDTO.getPageSize());
        List<ApplicationDTO> appList = appMapper.listAppDTO(appDTO);
        return appList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ApplicationDTO getAppDTOByAppId(String id) {
        ApplicationDTO appDTO = appMapper.getAppDTOByAppId(id);
        return appDTO;
    }

}
