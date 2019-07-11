package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Navigation;
import com.dw.health.eportal.entity.dto.NavigationDTO;

import java.util.List;

public interface NavigationMapper extends BaseMapper<Navigation> {
    List<NavigationDTO> selectByPid(String pid);

    void deleteNavs(String navId);
}