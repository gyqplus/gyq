package com.dw.health.eportal.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dw.health.eportal.dao.NavigationMapper;
import com.dw.health.eportal.entity.Navigation;

/**
 * @Author: qjf
 * @Date: 2019/3/15
 */
@Component
public class NavigationManager {
    @Autowired
    private NavigationMapper navMapper;

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

    public void changeStruct(){
        List<Navigation> allNav = navMapper.selectAll();
        List<Navigation> cNavList = getChildren(allNav, "0");
        for (Navigation cNav : cNavList) {
            updateStruct(allNav, cNav);
        }

    }

    public void updateStruct(List<Navigation> allNav, Navigation pNav){
        List<Navigation> cNavlist = getChildren(allNav, pNav.getNavId());
        // 如果是叶子节点
        if (cNavlist == null || cNavlist.size() == 0){
            // 设置为叶子节点, 设置图标
            pNav.setLeafFlag(true);
            pNav.setIcon("fa fa-cog");
            navMapper.updateByPrimaryKeySelective(pNav);
        }else {
            // 如果不是叶子节点
            // 设置为非叶子节点，设置图标
            pNav.setLeafFlag(false);
            pNav.setIcon("fa fa-cogs");
            navMapper.updateByPrimaryKeySelective(pNav);
            for (Navigation cNav : cNavlist) {
                updateStruct(allNav, cNav);
            }
        }
    }

}
