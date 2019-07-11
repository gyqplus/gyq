package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Application;
import java.util.List;

/**
 * 用户收藏增删改查
 * @Author: qjf
 * @Date: 2019/3/4
 */
public interface FavoriteAppService {
    /**
     * 根据用户id查询用户收藏的app
     * @param userId
     * @return List <Application>
     */
    List <Application> listFavoriteAppByUserId(String userId, Integer currentPage, Integer pageSize);

    /**
     * 根据用户id查询用户收藏的app数目
     * @param userId
     * @return Integer
     */
    int countAppIdByUserId(String userId);
}
