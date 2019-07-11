package com.dw.health.eportal.util;

import java.util.Date;

import org.apache.shiro.SecurityUtils;

import com.dw.health.framework.entity.BaseDO;

/**
 * 操作实体重复的操作
 * @Author: qjf
 * @Date: 2019/3/14
 */
public class BaseEntityUtil<T> {
	
	private BaseEntityUtil() {}
	
    /**
     * 获取操作员id
     * @return
     */
    public static String getOperatorId(){
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 新增时，设置操作员id，创建时间，更新时间，删除标志
     * @param t
     * @param <T>
     * @return
     */
    public static<T> T insertEntity(T t){
        if (t instanceof BaseDO){
            ((BaseDO) t).setOperatorId(getOperatorId());
            ((BaseDO) t).setCreateTime(new Date());
            ((BaseDO) t).setModifyTime(new Date());
            ((BaseDO) t).setDeleteFlag(false);
        }
        return t;
    }

    /**
     * 修改时，设置操作员id，更新时间。
     * @param t
     * @param <T>
     * @return
     */
    public static<T> T updateEntity(T t){
        if (t instanceof BaseDO){
            ((BaseDO) t).setOperatorId(getOperatorId());
            ((BaseDO) t).setModifyTime(new Date());
        }
        return t;
    }

    /**
     * 删除时，设置操作员id，删除标志，删除时间
     * @param t
     * @param <T>
     * @return
     */
    public static<T> T deleteEntity(T t){
        if (t instanceof BaseDO){
            ((BaseDO) t).setOperatorId(getOperatorId());
            ((BaseDO) t).setDeleteFlag(true);
            ((BaseDO) t).setDeleteTime(new Date());
        }
        return t;
    }

}
