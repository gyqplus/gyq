package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.Dict;
import com.dw.health.eportal.entity.dto.DictDTO;
import java.util.List;

/**
 *
 * @ClassName: DictService
 * @Description: 字典service接口
 * @author yy 
 * @date 2019-03-19 18:35
 * @version V1.0 
 */

public interface DictService {


    /**
     * 新增字典项
     * author:yy
     * 2019-03-19
     */
    void saveDict(Dict record);


    /**
     * 修改字典项
     * author:yy
     * 2019-03-19
     */
    void updateDict(Dict dict, String typeIdOld);


    /**
     * 删除字典项
     * author:yy
     * 2019-03-19
     */
    void deleteDict(List<Dict> list);


    /**
     * 查询所有字典项
     * author:yy
     * 2019-03-19
     */
    List<Dict> listDict(DictDTO dictDTO);

    Dict getDictByid(String id);
}
