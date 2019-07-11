package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "OFMUCROOM",schema="OPENFIRE")
public class Ofmucroom {

    private Integer serviceid = 1;

    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String name;

    private Integer roomid;

    private String creationdate = "00"+System.currentTimeMillis();

    private String modificationdate = "00"+System.currentTimeMillis();

    private String naturalname;

    private String description;

    private String lockeddate ="000000000000000";

    private String emptydate = "00"+System.currentTimeMillis();

    private Integer canchangesubject = 0;

    private Integer maxusers = 0;

    private Integer publicroom = 1;

    private Integer moderated = 1;

    private Integer membersonly = 0;

    private Integer caninvite = 0;

    private String roompassword;

    private Integer candiscoverjid = 1;

    private Integer logenabled = 1;

    private String subject;

    private Integer rolestobroadcast = 7;

    private Integer usereservednick = 0;

    private Integer canchangenick = 1;

    private Integer canregister = 1;

    private Integer allowpm = 0;


}