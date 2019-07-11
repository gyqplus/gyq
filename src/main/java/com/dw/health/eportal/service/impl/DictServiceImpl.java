package com.dw.health.eportal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.DictItemMapper;
import com.dw.health.eportal.dao.DictMapper;
import com.dw.health.eportal.entity.Dict;
import com.dw.health.eportal.entity.dto.DictDTO;
import com.dw.health.eportal.service.DictService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;

@Service
@Transactional(rollbackFor = Exception.class)
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictItemMapper dictItemMapper;
    @Override
    public void saveDict(Dict dict){
        Dict dict1 = dictMapper.selectByPrimaryKey(dict.getTypeId());
        if (dict1 != null) {
            throw new BizException("该字典类型已经存在");
        }
        dictMapper.insertSelective(BaseEntityUtil.insertEntity(dict));
    }


    @Override
    public void deleteDict(List<Dict> list){
        for (Dict dict : list) {
            dictMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(dict));
            dictItemMapper.deleteItemByTypeId(BaseEntityUtil.deleteEntity(dict));
        }
    }


    @Override
    public void updateDict(Dict dict, String typeIdOld){
        Map<String, Object> param = new HashMap<>();
        param.put("dict",BaseEntityUtil.updateEntity(dict));
        param.put("typeIdOld", typeIdOld);
        dictMapper.updateDictByTypeId(param);
        dictItemMapper.updateItemByTypeId(param);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Dict> listDict(DictDTO dictDTO){
        PageHelper.startPage(dictDTO.getCurrentPage(), dictDTO.getPageSize());
        List<Dict> list = dictMapper.listDict(dictDTO);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Dict getDictByid(String id) {
        return dictMapper.selectByPrimaryKey(id);
    }
}
