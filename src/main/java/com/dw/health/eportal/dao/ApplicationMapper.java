package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.entity.dto.ApplicationDTO;
import java.util.List;

public interface ApplicationMapper extends BaseMapper<Application> {
    List<ApplicationDTO> listAppDTO(ApplicationDTO appDTO);

    ApplicationDTO getAppDTOByAppId(String id);
}