package com.dw.health.eportal.dao;
import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Dict;
import com.dw.health.eportal.entity.DictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictItemMapper extends BaseMapper<DictItem> {
    List<DictItem> selectByTypeId(String typeId);

    int deleteItemByTypeId(Dict dict);

    int updateItemByTypeId(@Param("param") Map<String, Object> param);
}