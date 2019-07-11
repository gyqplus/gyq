package com.dw.health.eportal.service.impl;

import com.dw.health.eportal.dao.RelUserDeptMapper;
import com.dw.health.eportal.entity.RelUserDept;
import com.dw.health.eportal.service.RelUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RelUserDeptServiceImpl implements RelUserDeptService {


    @Autowired
    private RelUserDeptMapper relUserDeptMapper;


    @Override
    public void saveRel(RelUserDept relUserDept){
        relUserDeptMapper.insertSelective(relUserDept);
    }


    @Override
    public void deleteRel(List<RelUserDept> relUserDepts){
        for(RelUserDept relUserDept: relUserDepts) {
            relUserDeptMapper.deleteByPrimaryKey(relUserDept);
        }
    }
}
