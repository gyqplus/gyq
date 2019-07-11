package com.dw.health.eportal.dao;

import com.dw.health.eportal.entity.openfire.Ofmucroom;
import tk.mybatis.mapper.common.BaseMapper;

public interface OfMucRoomMapper extends BaseMapper<Ofmucroom> {

    Integer getNextRoomId();

    Ofmucroom getRoomByRoomId(Integer roomId);
}