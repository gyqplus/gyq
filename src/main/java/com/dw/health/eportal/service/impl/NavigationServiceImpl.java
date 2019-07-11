package com.dw.health.eportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.NavigationMapper;
import com.dw.health.eportal.entity.Navigation;
import com.dw.health.eportal.entity.dto.NavigationDTO;
import com.dw.health.eportal.manager.NavigationManager;
import com.dw.health.eportal.mo.NavigationDTOMapper;
import com.dw.health.eportal.service.NavigationService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
/**
 * @author gyq 
 * @version V1.0 
 * @ClassName: UserServiceImpl
 * @Description: 用户service接口实现
 * @date 2019-2-28 8:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NavigationServiceImpl implements NavigationService {
    @Autowired
    private NavigationMapper navigationMapper;
    @Autowired
    private NavigationManager navManager;

    private List<NavigationDTO> getChildrenDTO(List<NavigationDTO> allNavDTO, String pNavId) {
        return allNavDTO.stream().filter(cNav -> cNav.getPid().equals(pNavId)).collect(Collectors.toList());
    }
    /**
     * 获取子节点
     *
     * @param allNav 所有节点
     * @param pNavId 父节点
     * @return children
     */
    private List<Navigation> getChildren(List<Navigation> allNav, String pNavId) {
        return allNav.stream().filter(cNav -> cNav.getPid().equals(pNavId)).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getNavigation() {
        String result;
        List<Navigation> allNav = navigationMapper.selectAll();
        List<NavigationDTO> allNavDTO = NavigationDTOMapper.navMapper.toNavigationDTOs(allNav);
        List<NavigationDTO> cNavList = getChildrenDTO(allNavDTO, "0");
        for (NavigationDTO cNav : cNavList) {
            if (!cNav.getLeafFlag()) {
                cNav.setChildren(genNavigation(allNavDTO, cNav));
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(cNavList);
        } catch (Exception e) {
            throw new BizException(e);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Navigation getNavigationById(String navId) {
        return navigationMapper.selectByPrimaryKey(navId);
    }

    @Override
    public int addNav(Navigation nav) {
        int count = navigationMapper.selectCount(new Navigation().setName(nav.getName()));
        if (count > 0){
            throw new BizException("该菜单已经存在");
        }
        int insertRowNum = navigationMapper.insertSelective(BaseEntityUtil.insertEntity(nav));
        navManager.changeStruct();
        return insertRowNum;
    }

    @Override
    public void deleteNav(List<Navigation> nav) {
        for (Navigation navigation : nav) {
            int count = navigationMapper.selectCount(new Navigation().setPid(navigation.getNavId()));
            if (count > 0){
                throw new BizException("当前菜单含有子菜单，禁止删除");
            }
            // 逻辑删除该节点
            navigationMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(navigation));
        }
        navManager.changeStruct();
    }

    @Override
    public void updateNav(Navigation nav) {
        navigationMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(nav));
    }

    @Override
    public void deleteNavs(String navId) {
        navigationMapper.deleteNavs(navId);
        navManager.changeStruct();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Navigation> getNavigationListSimple() {
        return navigationMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<NavigationDTO> getNavigationListByPid(NavigationDTO navDTO) {
        PageHelper.startPage(navDTO.getCurrentPage(), navDTO.getPageSize());
        return navigationMapper.selectByPid(navDTO.getPid());
    }

    private List<NavigationDTO> genNavigation(List<NavigationDTO> allNav, NavigationDTO pNav) {
        List<NavigationDTO> cNavList = getChildrenDTO(allNav, pNav.getId());
        for (NavigationDTO cNav : cNavList) {
            if (!cNav.getLeafFlag()) {
                cNav.setChildren(genNavigation(allNav, cNav));
            }
        }
        return cNavList;
    }

    /**
     * 获取树形菜单(导航)
     * gyq
     *
     * @return
     */
    @Override
    public String getNavigationHtml() {
        List<Navigation> allNav = navigationMapper.selectAll();
        List<Navigation> cNavList = getChildren(allNav, "0");

        StringBuilder sb = new StringBuilder();
        sb.append(" <ul class='sidebar-menu' data-widget='tree'> ");

        for (Navigation cNav : cNavList) {
            if (cNav.getLeafFlag()) {
                sb.append(" <li > ");
                sb.append(" 	<a pageurl=' ");
                sb.append(cNav.getUrl());
                sb.append(" 	' onclick='addTab(this);'> ");
                sb.append(" 		<i class='fa fa-link'></i> ");
                sb.append(" 		<span> ");
                sb.append(cNav.getName());
                sb.append(" 		</span> ");
                sb.append(" 	</a> ");
                sb.append(" </li> ");
            } else {
                sb.append(" <li class='treeview'> ");
                sb.append(genNavigationHtml(allNav, cNav));
                sb.append(" </li> ");
            }
        }
        sb.append(" </ul> ");

        return sb.toString().replace("\t", "");
    }

    /**
     * 递归实现生成根的所有子节点并拼html串
     * gyq
     *
     * @param pNav
     * @param allNav
     * @return
     */
    private String genNavigationHtml(List<Navigation> allNav, Navigation pNav) {
        StringBuilder sb = new StringBuilder();
        sb.append(" <a href='#' > ");
        sb.append(" 	<i class='fa fa-link'></i> ");
        sb.append(" 	<span> ");
        sb.append(pNav.getName());
        sb.append(" 	</span> ");
        sb.append(" 	<span class='pull-right-container'> ");
        sb.append(" 	<i class='fa fa-angle-left pull-right'></i> ");
        sb.append(" 	</span> ");
        sb.append(" </a> ");
        sb.append(" <ul class='treeview-menu'> ");

        List<Navigation> cNavList = getChildren(allNav, pNav.getNavId());
        for (Navigation cNav : cNavList) {
            if (cNav.getLeafFlag()) {
                sb.append(" <li > ");
                sb.append(" 	<a pageurl=' ");
                sb.append(cNav.getUrl());
                sb.append(" 	' onclick='addTab(this);'> ");
                sb.append(" 		<i class='fa fa-link'></i> ");
                sb.append(" 		<span> ");
                sb.append(cNav.getName());
                sb.append(" 		</span> ");
                sb.append(" 	</a> ");
                sb.append(" </li> ");
            } else {
                sb.append(" <li class='treeview'> ");
                sb.append(genNavigationHtml(allNav, cNav));
                sb.append(" </li> ");
            }
        }
        sb.append(" </ul> ");

        return sb.toString();
    }
}
