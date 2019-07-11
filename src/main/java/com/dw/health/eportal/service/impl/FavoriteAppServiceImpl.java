package com.dw.health.eportal.service.impl;

import com.dw.health.eportal.dao.ApplicationMapper;
import com.dw.health.eportal.dao.RelFavoriteUserAppMapper;
import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.entity.RelFavoriteUserApp;
import com.dw.health.eportal.service.FavoriteAppService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户收藏增删改查
 * @Author: qjf
 * @Date: 2019/3/4
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FavoriteAppServiceImpl implements FavoriteAppService {
    @Autowired
    private RelFavoriteUserAppMapper favoriteAppMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Application> listFavoriteAppByUserId(String userId, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<RelFavoriteUserApp> userAppList = favoriteAppMapper.select(new RelFavoriteUserApp().setUserId(userId));
        List<Application> appList = new ArrayList<>();
        if (userAppList != null&&!userAppList.isEmpty()){
            for (RelFavoriteUserApp favoriteUserApp : userAppList) {
                Application app = applicationMapper.selectByPrimaryKey(favoriteUserApp.getApplicationId());
                appList.add(app);
            }
        }
        return appList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int countAppIdByUserId(String userId) {
        return favoriteAppMapper.selectCount(new RelFavoriteUserApp().setUserId(userId));
    }
}
