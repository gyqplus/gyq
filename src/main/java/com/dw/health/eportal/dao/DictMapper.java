package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Dict;
import com.dw.health.eportal.entity.dto.DictDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface DictMapper extends BaseMapper<Dict>{
    List<Dict> listDict(DictDTO dictDTO);

    int updateDictByTypeId(@Param("param")Map<String, Object> param);
}