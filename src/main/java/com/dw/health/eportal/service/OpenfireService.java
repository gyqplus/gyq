package com.dw.health.eportal.service;

import com.dw.health.eportal.entity.openfire.ChatLog;
import com.dw.health.eportal.entity.openfire.OfUser;
import com.dw.health.eportal.entity.openfire.Ofmucroom;
import com.dw.health.eportal.entity.openfire.OfmucroomDTO;
import org.igniterealtime.restclient.entity.RosterItemEntity;
import org.igniterealtime.restclient.entity.UserEntity;

import java.util.List;

public interface OpenfireService {
    /**
     * @Author: lyx
     * @Description: REST连接
     * @Param:
     * @return:
    **/
    void openfireClient();
    /**
     * @Author: lyx
     * @Description: 查询of用户
     * @Param:
     * @return:
    **/
    UserEntity getUser(String userName);

    /**
     * @Author: lyx
     * @Description: 查询of用户好友
     * @Param:
     * @return:
    **/
    List<RosterItemEntity> getRoster(String userName);

    /**
     * @Author: lyx
     * @Description: 删除of好友
     * @Param: 用户ID，好友JID
     * @return: 
    **/
    void deleteRoster(String userName,String friendName);

    /**
     * @Author: lyx
     * @Description: 加好友
     * @Param:
     * @return:
    **/
    void addRoster(String userName,String friendName,String friendRealName,String groupName);

    /**
     * @Author: lyx
     * @Description: 创建房间（群）
     * @Param: 
     * @return: 
    **/
    int createRoom(String jid, String chatroomName) throws Exception;

    /**
     * @Author: lyx
     * @Description: 修改房间名（群）
     * @Param:
     * @return:
    **/
    void updateRoom(String chatRoomName, String newRoomName);

    /**
     * @Author: lyx
     * @Description: 加入房间
     * @Param:
     * @return:
    **/
    String joinChatRoom(String jid,String roomId);

    /**
     * @Author: lyx
     * @Description: 根据jid查询房间
     * @Param:
     * @return:
    **/
    List<OfmucroomDTO> getRoomByJid(String jid);


    /**
     * @Author: lyx
     * @Description: 退出房间
     * @Param:
     * @return:
    **/
    String quitChatRoom(String jid,String roomID);

    /**
     * @Author: lyx
     * @Description: 删除房间
     * @Param:
     * @return:
    **/
    void deleteRoom(String roomName);

    /**
     * @Author: lyx
     * @Description: 创建用户
     * @Param:
     * @return:
    **/
    void createUser(String userName,String realName);


    /**
     * @Author: lyx
     * @Description: 根据房间ID查人 jid
     * @Param:
     * @return:
    **/
    List<String> getJidByRoomId(String roomid);

    /**
     * @Author: lyx
     * @Description: 根据房间ID查群员信息
     * @Param:
     * @return:
    **/
    List<OfUser> getMembersByRoomId(String roomid);

    /**
     * 获取全部群组
     * @return
     */
    List<Ofmucroom> getAllRoom();

    /**
     * @Author: lyx
     * @Description: 获取历史聊天记录
     * @Param:
     * @return:
    **/
    List<ChatLog> getChatLogByUserName(String mineName,String userName,String type);

    /**
     * @Author: lyx
     * @Description: 插入聊天记录
     * @Param:
     * @return:
    **/
    void insertChatLog(ChatLog chatLog);

    /**
     * @Author: lyx
     * @Description: 获取离线好友集合
     * @Param:
     * @return:
    **/
    List<String> getOfflineFriends(String userName);
}
