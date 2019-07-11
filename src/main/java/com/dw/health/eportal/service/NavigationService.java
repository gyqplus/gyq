package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Navigation;
import com.dw.health.eportal.entity.dto.NavigationDTO;

import java.util.List;

/**
 * @author gyq 
 * @version V1.0 
 * @ClassName: NavigationService
 * @Description:
 * @date 2019-3-7 10:04
 */
public interface NavigationService {
    String getNavigationHtml();

    String getNavigation();

    Navigation getNavigationById(String navId);

    List<Navigation> getNavigationListSimple();

    List<NavigationDTO> getNavigationListByPid(NavigationDTO navDTO);

    int addNav(Navigation nav);

    void deleteNav(List<Navigation> nav);

    void updateNav(Navigation nav);

    void deleteNavs(String navId);
}
