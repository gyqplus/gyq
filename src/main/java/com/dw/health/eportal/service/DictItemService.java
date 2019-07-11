package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.DictItem;
import com.dw.health.eportal.entity.dto.DictItemDTO;
import java.util.List;
/**
 * 字典项增删改查
 * @Author: qjf
 * @Date: 2019/3/19
 */
public interface DictItemService {
    void save(DictItem dictItem);

    void deleteDictItem(List<DictItem> dictItemList);

    void updateDictItem(DictItem dictItem);

    List<DictItem> listDictItem(DictItemDTO dictItemDTO);

    DictItem getDictItemById(String id);
}
