package com.dw.health.eportal.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.igniterealtime.restclient.entity.RosterItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dw.health.core.config.NginxConfig;
import com.dw.health.eportal.entity.openfire.ChatLog;
import com.dw.health.eportal.entity.openfire.Friend;
import com.dw.health.eportal.entity.openfire.Group;
import com.dw.health.eportal.entity.openfire.OfUser;
import com.dw.health.eportal.entity.openfire.Ofmucroom;
import com.dw.health.eportal.entity.openfire.OfmucroomDTO;
import com.dw.health.eportal.entity.openfire.OpenfireConfig;
import com.dw.health.eportal.service.OpenfireService;
import com.dw.health.eportal.util.CurrentUserUtil;
import com.dw.health.framework.consts.GlobalNames;
import com.dw.health.framework.web.Result;

import net.sf.json.JSONObject;


/**
 * @ClassName: OpenfireController
 * @Description: OpenfireController
 * @Author: lyx
 * @Date: 2019/4/11 14:25
 */
@Controller
@RequestMapping(path = "/op")
public class OpenfireController {
    @Autowired
    private OpenfireConfig openfireConfig;
    @Autowired
    private OpenfireService openfireService;

    @Autowired
    private NginxConfig nginxConfig;

    @PostMapping("/configure")
    @ResponseBody
    public Result<OpenfireConfig> getConfigure() {
        return new Result<>(openfireConfig);
    }

    @PostMapping("/openfireClient")
    @ResponseBody
    public Result<Boolean> getopenfireClient() {

        openfireService.openfireClient();
        return new Result<>(true);
    }


    @PostMapping("/getFriendByName")
    @ResponseBody
    public Result<List<Friend>> getFriendByName(String userName) {
        List<String> groups = new ArrayList<>();
        List<Friend> friends = new ArrayList<>();
        List<RosterItemEntity> rosterItemEntityList = openfireService.getRoster(userName);
        for (RosterItemEntity rosterItemEntity : rosterItemEntityList) {
            if ("null".equals(rosterItemEntity.getGroups().get(0))){
                List<String> myGroups = new ArrayList<>();
                myGroups.add(GlobalNames.OPENFIRE_NODEPT);
                rosterItemEntity.setGroups(myGroups);
            }
            if (!groups.contains(rosterItemEntity.getGroups().get(0))) {
                groups.add(rosterItemEntity.getGroups().get(0));
            }

        }
        for (String group : groups) {
            Stream<RosterItemEntity> rosterItemEntityStream = rosterItemEntityList.stream().filter(
                    rosterItemEntity -> group.equals(rosterItemEntity.getGroups().get(0))
            );
            List<RosterItemEntity> newRosterItemEntityList = rosterItemEntityStream.collect(Collectors.toList());
            Friend friend = new Friend();
            friend.setGroupname(group);
            friend.setId(group);
            List<OfUser> ofUsers = new ArrayList<>();
            for (RosterItemEntity rosterItemEntity : newRosterItemEntityList) {
                OfUser ofUser = new OfUser();
                String userID = rosterItemEntity.getJid().substring(0, rosterItemEntity.getJid().indexOf("@"));
                ofUser.setId(userID);
                ofUser.setUsername(rosterItemEntity.getNickname());
                ofUser.setAvatar(nginxConfig.getAvatar_url() + userID + ".jpg");
                ofUsers.add(ofUser);
            }
            friend.setList(ofUsers);
            friends.add(friend);
        }

        return new Result<>(friends);
    }

    @PostMapping("/getRoomByName")
    @ResponseBody
    public Result<List<Group>> getRoomByName(String jid) {
        List<Group> groups = new ArrayList<>();

        List<OfmucroomDTO> ofmucroomDTOList = openfireService.getRoomByJid(jid);
        for (OfmucroomDTO ofmucroomDTO : ofmucroomDTOList) {
            Group group = new Group();
            group.setId(ofmucroomDTO.getRoomid().toString()).setGroupname(ofmucroomDTO.getNaturalname()).setAvatar(nginxConfig.getAvatar_url() + "groupIcon.png");
            groups.add(group);
        }

        return new Result<>(groups);
    }

    @PostMapping("/getJidsByRoomId")
    @ResponseBody
    public Result<List<String>> getJidsByRoomId(String roomid) {
        return new Result<>(openfireService.getJidByRoomId(roomid));
    }

    @PostMapping("/getMembersByRoomId")
    @ResponseBody
    public String getMembersByRoomId(String id){

        List<OfUser> ofUsers = openfireService.getMembersByRoomId(id);
        for (OfUser ofUser : ofUsers) {
            ofUser.setAvatar(nginxConfig.getAvatar_url()+ofUser.getAvatar());
        }
        JSONObject jsonlist = new JSONObject();
        jsonlist.put("list",ofUsers);

        JSONObject jsonMembers = new JSONObject();
        jsonMembers.put("code","0");
        jsonMembers.put("msg","加载失败");
        jsonMembers.put("data",jsonlist);

        return  jsonMembers.toString();
    }

    @PostMapping("/getAllRoom")
    @ResponseBody
    public Result<List<Ofmucroom>> getAllRoom() {
        return new Result<>(openfireService.getAllRoom());
    }

    @PostMapping("/addRoster")
    @ResponseBody
    public Result<String> addRoster(String userName, String friendName, String friendRealName, String groupName) {
        openfireService.addRoster(userName, friendName, friendRealName, groupName);
        return new Result<>("添加成功!");
    }

    @PostMapping("deleteRoster")
    @ResponseBody
    public Result<String> deleteRoster(String friendName) {
        openfireService.deleteRoster(CurrentUserUtil.getCurrentUser().getUserName(),friendName);
        return new Result<>("删除成功!");
    }

    @GetMapping(path = "/chatLogPage")
    public String getChatLogPage(Model model) {
        model.addAttribute("user", CurrentUserUtil.getCurrentUser());
        return "/im/chatlog";
    }

    @PostMapping("/insertChatLog")
    @ResponseBody
    public Result<String> insertChatLog(ChatLog chatLog) {

        openfireService.insertChatLog(chatLog);
        return new Result<>("聊天记录插入成功");
    }

    @PostMapping("/getChatLog")
    @ResponseBody
    public Result<String> getChatLog(String mineName,String userName,String type) {

        ArrayList<String> datas = new ArrayList<String> ();

        List<ChatLog> chatLogs = openfireService.getChatLogByUserName(mineName,userName,type);
        for (ChatLog chatLog : chatLogs) {

            JSONObject jsonChatLog = new JSONObject();
            jsonChatLog.put("username",chatLog.getUserName());
            jsonChatLog.put("id",chatLog.getChatUserId());
            jsonChatLog.put("avatar",nginxConfig.getAvatar_url()+chatLog.getChatUserId()+".jpg");
            jsonChatLog.put("timestamp",Long.valueOf(chatLog.getChatTimestamp()));
            jsonChatLog.put("content",chatLog.getChatBody());
            datas.add(jsonChatLog.toString());
        }


        JSONObject jsonAll = new JSONObject();
        jsonAll.put("code","0");
        jsonAll.put("msg","加载失败");
        jsonAll.put("data",datas);


        return new Result<>(jsonAll.toString());
    }

    @PostMapping("/getOfflineFriends")
    @ResponseBody
    public Result<List<String>> getOfflineFriends(String userName){

        return new Result<>(openfireService.getOfflineFriends(userName));
    }
    @PostMapping("/createChatRoom")
    @ResponseBody
    public Result<String> createChatRoom(String userName, String roomName){

        try {
            openfireService.createRoom(userName+"@"+openfireConfig.getService_name(),roomName);
            return new Result<>("创建成功");
        } catch (Exception e) {
            return new Result<>("创建失败");
        }
    }
    @PostMapping("/joinChatRoom")
    @ResponseBody
    public Result<String> joinChatRoom(String userName, String roomId){

        String result = openfireService.joinChatRoom(userName+"@"+openfireConfig.getService_name(),roomId);

        return new Result<>(result);
    }
    @PostMapping("/quitChatRoom")
    @ResponseBody
    public Result<String> quitChatRoom(String userName, String roomId){

        //String result = openfireService.quitChatRoom(userName+"@"+openfireConfig.getService_name(),roomId);
        String result = openfireService.quitChatRoom(userName+"@"+openfireConfig.getService_name(),roomId);
        return new Result<>(result);
        //return new Result<>("success");
    }
    //页面跳转--------------------------------------------------------------
    @GetMapping("/addPage")
    public ModelAndView addPage(String userName, String deptName) {
        List<String> deptList = new ArrayList<>();
        String a[] = deptName.split(",");
        for (int i = 0; i < a.length; i++) {
            deptList.add(a[i]);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("im/addPage");
        mv.addObject("userName", userName);
        mv.addObject("deptName", deptList);
        return mv;
    }
    @GetMapping("/inviteToGroupPage")
    public ModelAndView inviteToGroupPage(String userName) {
        String minename = CurrentUserUtil.getCurrentUser().getUserName();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("im/inviteToGroupPage");
        mv.addObject("friendName",userName);
        mv.addObject("mineName",minename);
        return mv;
    }@GetMapping("/createGroupPage")
    public ModelAndView createGroupPage() {
        String minename = CurrentUserUtil.getCurrentUser().getUserName();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("im/createGroupPage");
        mv.addObject("mineName",minename);
        return mv;
    }

}
