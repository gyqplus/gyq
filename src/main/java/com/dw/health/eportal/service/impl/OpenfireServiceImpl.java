package com.dw.health.eportal.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.dw.health.eportal.dao.ChatLogMapper;
import com.dw.health.eportal.dao.OfMucAffiliationMapper;
import com.dw.health.eportal.dao.OfMucRoomMapper;
import com.dw.health.eportal.dao.OfPresenceMapper;
import com.dw.health.eportal.entity.openfire.*;
import com.dw.health.eportal.service.OpenfireService;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.*;
import org.igniterealtime.restclient.enums.SupportedMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: OpenfireServiceImpl
 * @Description: TODO
 * @Author: lyx
 * @Date: 2019/4/13 13:34
 */
@Service
public class OpenfireServiceImpl implements OpenfireService {

    @Autowired
    private OpenfireConfig openfire;

    @Autowired
    private OfMucRoomMapper ofMucRoomMapper;

    @Autowired
    private OfMucAffiliationMapper ofMucAffiliationMapper;

    @Autowired
    private OfPresenceMapper ofPresenceMapper;

    @Autowired
    OfMucRoomMapper mucRoomMapper;

    @Autowired
    ChatLogMapper chatLogMapper;

    private RestApiClient restApiClient;


    @Override
    public void openfireClient() {

        AuthenticationToken authenticationToken = new AuthenticationToken(openfire.getRest_key());
        restApiClient = new RestApiClient(openfire.getServer_url(), openfire.getServer_port(), authenticationToken, SupportedMediaType.JSON);
    }

    @Override
    public UserEntity getUser(String userName) {


        return restApiClient.getUser(userName);
    }

    @Override
    public List<RosterItemEntity> getRoster(String userName) {
        List<RosterItemEntity> rosterItemEntityList = new ArrayList<>();
        try {
            RosterEntities rosterEntities = restApiClient.getRoster(userName);
            rosterItemEntityList = rosterEntities.getRoster();
        }catch (Exception e){
            return rosterItemEntityList;
        }

        return rosterItemEntityList;
    }

    @Override
    public void deleteRoster(String userName, String friendName) {
        String friendJid = friendName + "@" + openfire.getService_name();
        restApiClient.deleteRosterEntry(userName, friendJid);
    }

    @Override
    public void addRoster(String userName, String friendName, String friendRealName, String groupName) {
        // Create a user roster entry (Possible values for subscriptionType are: -1 (remove), 0 (none), 1 (to), 2 (from), 3 (both))
        String friendJid = friendName + "@" + openfire.getService_name();
        RosterItemEntity rosterItemEntity = new RosterItemEntity(friendJid, friendRealName, 3);
        // Groups are optional
        List<String> groups = new ArrayList<String>();
        groups.add(groupName);
        rosterItemEntity.setGroups(groups);
        restApiClient.addRosterEntry(userName, rosterItemEntity);
    }

    @Override
    public int createRoom(String jid, String chatroomName) throws Exception {

        String url = openfire.getServer_url() + ":" + openfire.getServer_port() + "/plugins/restapi/v1/chatrooms";

        String uuid = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        Long nowData = new Date().getTime();
        jsonObject.put("roomName", uuid);
        jsonObject.put("naturalName", chatroomName);
        jsonObject.put("description", "");
        jsonObject.put("subject", "");
        jsonObject.put("creationDate", nowData);
        jsonObject.put("modificationDate", nowData);
        jsonObject.put("maxUsers", "0");
        jsonObject.put("persistent", "true");
        jsonObject.put("publicRoom", "true");
        jsonObject.put("registrationEnabled", "true");
        jsonObject.put("canAnyoneDiscoverJID", "true");
        jsonObject.put("canOccupantsChangeSubject", "true");
        jsonObject.put("canOccupantsInvite", "true");
        jsonObject.put("canChangeNickname", "true");
        jsonObject.put("logEnabled", "true");
        jsonObject.put("loginRestrictedToNickname", "false");
        jsonObject.put("membersOnly", "false");
        jsonObject.put("moderated", "true");
        jsonObject.put("broadcastPresenceRoles", "[\"moderator\",\"participant\",\"visitor\"]");
        jsonObject.put("owners", "[\"" + jid + "\"]");

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .header("Authorization", openfire.getRest_key())
                .addHeader("Connection", "close").addHeader("MultipleDevicesAuth", "true")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.code();

    }

    @Override
    public void updateRoom(String chatRoomName, String newRoomName) {
        Ofmucroom ofMucRoom = new Ofmucroom();
        ofMucRoom.setNaturalname(newRoomName).setName(chatRoomName);
        ofMucRoomMapper.updateByPrimaryKeySelective(ofMucRoom);
    }

    @Override
    public String joinChatRoom(String jid, String roomId) {

        Ofmucroom ofMucRoom = ofMucRoomMapper.getRoomByRoomId(Integer.valueOf(roomId));

        List<String> jids =this.getJidByRoomId(ofMucRoom.getRoomid().toString());
        if(jids !=null && jids.contains(jid)){
            return "已加入该群";
        }else{
            Ofmucaffiliation ofmucaffiliation = new Ofmucaffiliation();
            ofmucaffiliation.setRoomid(ofMucRoom.getRoomid()).setJid(jid);
            ofMucAffiliationMapper.insertSelective(ofmucaffiliation);

            restApiClient.addOwner(ofMucRoom.getName(), jid);

            return "加入成功";
        }

    }

    @Override
    public List<OfmucroomDTO> getRoomByJid(String jid) {

        return ofMucAffiliationMapper.getOfmucroomDTObyJID(jid);
    }

    @Override
    public String quitChatRoom(String jid, String roomID) {

        Ofmucaffiliation ofmucaffiliation = new Ofmucaffiliation();
        ofmucaffiliation.setJid(jid).setRoomid(Integer.valueOf(roomID));
        ofMucAffiliationMapper.delete(ofmucaffiliation);
        return "退出成功";
    }

    @Override
    public void deleteRoom(String roomName) {

        restApiClient.deleteChatRoom(roomName);
    }

    @Override
    public void createUser(String userName,String realName) {
        UserEntity userEntity = new UserEntity(userName, realName, "", openfire.getService_password());
        restApiClient.createUser(userEntity);
    }

    @Override
    public List<String> getJidByRoomId(String roomid) {
        Integer roomidNum = Integer.valueOf(roomid);
        Ofmucaffiliation ofmucaffiliation = new Ofmucaffiliation();
        ofmucaffiliation.setRoomid(roomidNum);
        List<String> jids = new ArrayList<>();
        List<Ofmucaffiliation> ofmucaffiliations = ofMucAffiliationMapper.select(ofmucaffiliation);
        for (Ofmucaffiliation ofmucaffiliation1 : ofmucaffiliations) {
            jids.add(ofmucaffiliation1.getJid());
        }
        return jids;
    }

    @Override
    public List<OfUser> getMembersByRoomId(String roomid) {

        Map map = new HashMap();
        map.put("roomid",Integer.valueOf(roomid));
        map.put("serverName","@"+openfire.getService_name());
        return ofMucAffiliationMapper.getMembersByRoomId(map);
    }

    @Override
    public List<Ofmucroom> getAllRoom() {
        return mucRoomMapper.selectAll();
    }

    @Override
    public List<ChatLog> getChatLogByUserName(String mineName, String userName, String type) {
        List<ChatLog> chatLogs = new ArrayList<>();
        if ("friend".equals(type)){
            ChatLog chatLog = new ChatLog();
            chatLog.setChatUserId(mineName).setChatToUserId(userName);
            chatLogs = chatLogMapper.getFriendChatLogByName(chatLog);

        }
        if ("group".equals(type)){
            ChatLog chatLog = new ChatLog();
            chatLog.setChatUserId(mineName).setChatToUserId(userName);
            chatLogs = chatLogMapper.getGroupChatLogByName(chatLog);
        }
        return chatLogs;
    }

    @Override
    public void insertChatLog(ChatLog chatLog) {
        chatLogMapper.insertSelective(chatLog);
    }

    @Override
    public List<String> getOfflineFriends(String userName) {
        List<String> ofJid = new ArrayList<>();
        List<String> ofpr = new ArrayList<>();
        List<RosterItemEntity> rosterItemEntities=this.getRoster(userName);
        for (RosterItemEntity rosterItemEntity : rosterItemEntities) {
            ofJid.add(rosterItemEntity.getJid().substring(0,rosterItemEntity.getJid().indexOf("@")));
        }

        List<OfPresence> ofPresences =ofPresenceMapper.selectAll();
        for (OfPresence ofPresence : ofPresences) {
            ofpr.add(ofPresence.getUsername());
        }


        List<String> Offline = new ArrayList<>();

        for (int i = 0; i < ofJid.size(); i++) {
            if (ofpr.contains(ofJid.get(i)))
                Offline.add(ofJid.get(i));
        }

        return Offline;
    }


}
