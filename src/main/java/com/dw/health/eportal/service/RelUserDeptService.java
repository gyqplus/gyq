package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.RelUserDept;

import java.util.List;

public interface RelUserDeptService {
    void saveRel(RelUserDept relUserDept);
    void deleteRel(List<RelUserDept> relUserDepts);
}
