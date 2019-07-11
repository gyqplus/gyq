package com.dw.health.eportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dw.health.eportal.dao.DictItemMapper;
import com.dw.health.eportal.entity.DictItem;
import com.dw.health.eportal.entity.dto.DictItemDTO;
import com.dw.health.eportal.service.DictItemService;
import com.dw.health.eportal.util.BaseEntityUtil;
import com.dw.health.framework.exception.BizException;
import com.github.pagehelper.PageHelper;

/**字典项增删改查
 * @Author: qjf
 * @Date: 2019/3/19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictItemServiceImpl implements DictItemService {
    @Autowired
    private DictItemMapper dictItemMapper;
    @Override
    public void save(DictItem dictItem) {
        int count = dictItemMapper.selectCount(new DictItem().setValue(dictItem.getValue()).setTypeId(dictItem.getTypeId()));
        if (count > 0){
            throw new BizException("该字典项已经存在");
        }
        dictItemMapper.insertSelective(BaseEntityUtil.insertEntity(dictItem));
    }

    @Override
    public void deleteDictItem(List<DictItem> dictItemList) {
        for (DictItem dictItem : dictItemList) {
            dictItemMapper.deleteByPrimaryKey(BaseEntityUtil.deleteEntity(dictItem));
        }
    }

    @Override
    public void updateDictItem(DictItem dictItem) {
        dictItemMapper.updateByPrimaryKeySelective(BaseEntityUtil.updateEntity(dictItem));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DictItem> listDictItem(DictItemDTO dictItemDTO) {
        PageHelper.startPage(dictItemDTO.getCurrentPage(), dictItemDTO.getPageSize());
        return dictItemMapper.select(new DictItem().setTypeId(dictItemDTO.getTypeId())
                .setValue(dictItemDTO.getValue()).setContent(dictItemDTO.getContent()));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public DictItem getDictItemById(String id) {
        return dictItemMapper.selectByPrimaryKey(id);
    }
}
