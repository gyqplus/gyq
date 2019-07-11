package com.dw.health.eportal.dao;

import com.dw.health.framework.mybatis.BaseMapper;
import com.dw.health.eportal.entity.openfire.ChatLog;

import java.util.List;

public interface ChatLogMapper extends BaseMapper<ChatLog> {

    List<ChatLog> getFriendChatLogByName(ChatLog chatLog);

    List<ChatLog> getGroupChatLogByName(ChatLog chatLog);
}