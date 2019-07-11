package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Accessors(chain = true)
@Data
@Table(name = "CHAT_LOG",schema="OPENFIRE")
public class ChatLog {
    @Id
    @KeySql(sql = "select sys_guid() from dual", order = ORDER.BEFORE)
    private String chatId;

    private String chatType;

    private String chatUserId;

    private String chatToUserId;

    private String chatTimestamp;

    private String chatBody;

    private String userName;


 }