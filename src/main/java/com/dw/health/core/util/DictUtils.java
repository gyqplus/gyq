package com.dw.health.core.util;

import com.dw.health.eportal.entity.DictItem;
import com.github.pagehelper.PageException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字典（code）工具
 *
 * @author 卢燕辉
 * @date 2019.3.19
 */
public class DictUtils {
    public static List<DictItem> parseArrayCode(String arrayCode) {
        try {
            List<DictItem> dictItems = new ArrayList<>();
            Arrays.stream(arrayCode.split(",")).map(kv -> kv.split(":")).forEach(array -> {
                DictItem dictItem = new DictItem();
                dictItem.setValue(array[0]);
                dictItem.setContent(array[1]);
                dictItems.add(dictItem);
            });
            return dictItems;
        } catch (Exception e) {
            throw new PageException("arrayCode");
        }
    }
}
