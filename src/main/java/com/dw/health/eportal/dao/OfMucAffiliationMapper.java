package com.dw.health.eportal.dao;

import com.dw.health.eportal.entity.openfire.OfUser;
import com.dw.health.eportal.entity.openfire.Ofmucaffiliation;
import com.dw.health.eportal.entity.openfire.OfmucroomDTO;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

public interface OfMucAffiliationMapper extends BaseMapper<Ofmucaffiliation> {


    List<OfmucroomDTO> getOfmucroomDTObyJID(String jid);

    List<OfUser> getMembersByRoomId(Map map);
}