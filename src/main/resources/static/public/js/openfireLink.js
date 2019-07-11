// XMPP服务器配置
var OpenfireConfigure = getOpenfireConfigure();
// nginx服务器配置
var NginxConfig = getNginxConfig();
// XMPP服务器BOSH地址
var BOSH_SERVICE = OpenfireConfigure.bosh_service;
//XMPP服务器域名
var SERVICE_NAME = OpenfireConfigure.service_name;
//XMPP服务器密码
var SERVICE_PASSWORD = OpenfireConfigure.service_password;
// XMPP连接
var connection = new Strophe.Connection(BOSH_SERVICE);
// 当前状态是否连接
var connected = false;
//自己用户信息
var MineUser;
//rest连接
var restLink = false;
//加好友--好友
var friend = {};


//获取openfire配置
function getOpenfireConfigure() {
    var openfireConfigure = new AjaxRequest("/op/configure", null).execute();
    return openfireConfigure;
}

function getNginxConfig() {
    var nginxConfig = new AjaxRequest("/nginx/getNginxConfig", null).execute();
    return nginxConfig;
}
// 页面初始化
$(function () {

    //获取用户信息
    MineUser = new AjaxRequest("/user/getCurrentUser", null).execute();
    //连接REST，返回true
    restLink = new AjaxRequest("/op/openfireClient", null).execute();
    if (!restLink) {
        alert("REST连接失败");
    }
    loadIm(true);

});

function getFriends() {
    var friend = new AjaxRequest("/op/getFriendByName", {userName: MineUser.userName}, null).execute();
    return friend;
}

function getRoomByName() {
    var group = new AjaxRequest("/op/getRoomByName", {jid: MineUser.userName + '@' + SERVICE_NAME}, null).execute();
    return group;
}
function loadIm(min) {
    layui.use('layim', function (layim) {
            // 通过BOSH连接XMPP服务器
            if (!connected) {
                connection.connect(MineUser.userName + "@" + SERVICE_NAME, SERVICE_PASSWORD, onConnect);
            }

            // 连接状态改变的事件
            function onConnect(status) {
                if (status == Strophe.Status.CONNFAIL) {
                    alert("聊天连接失败！");
                } else if (status == Strophe.Status.AUTHFAIL) {
                    alert("聊天登录失败！");
                } else if (status == Strophe.Status.DISCONNECTED) {
                    alert("聊天连接断开！");
                    connected = false;
                } else if (status == Strophe.Status.CONNECTED) {
                    console.log("连接成功，可以开始聊天了！");
                    connected = true;

                    // 当接收到<message>节，调用onMessage回调函数
                    connection.addHandler(onMessage, null, 'message', null, null, null);
                    // 首先要发送一个<presence>给服务器（initial presence）

                    //监听下线
                    connection.addHandler(onPresenceOffline, null, 'presence', 'unavailable', null, null);
                    connection.addHandler(onPresenceOffline, null, 'presence', null , null, null);
                    //上线
                    connection.send($pres().tree());
                    //上线后通知所有人
                    connection.send($msg({to: 'all@broadcast.openfire', id: 'login'}).tree());
                    //所有房间上线
                    /*var groups = getRoomByName();
                    for (var i in groups) {

                        var group = groups[i];
                        connection.send($pres({from:MineUser.userName+'@'+SERVICE_NAME, to:group.id+'@conference.openfire/'+MineUser.userName}).c('x',{xmlns:'http://jabber.org/protocol/muc'}).c('item',{affiliation:'admin',role:'moderator'}).tree());
                    }*/


                }
            };
            //基础配置
            layim.config({
                brief: false, //是否简约模式（如果true则不显示主面板）
                title: '通讯录',
                min: min,
                notice: false,
                voice: false,
                maxLength: 2000,
                init: {
                    mine: {
                        username: MineUser.realName,
                        id: MineUser.userName,
                        status: 'online',
                        sign: "这是个性签名！",
                        avatar: NginxConfig.avatar_url + MineUser.userName + '.jpg'
                    }
                    , friend: getFriends()
                    , group: getRoomByName()
                } //获取主面板列表信息，下文会做进一步介绍

                //获取群员接口（返回的数据格式见下文）
                , members: {
                    url: '/op/getMembersByRoomId' //接口地址（返回的数据格式见下文）
                    , type: 'post' //默认get，一般可不填
                    , data: {} //额外参数
                }

                //上传图片接口（返回的数据格式见下文），若不开启图片上传，剔除该项即可
                , uploadImage: {
                    url: '/ftp/uploadAvatar' //接口地址
                    , type: 'post' //默认post
                }

                //上传文件接口（返回的数据格式见下文），若不开启文件上传，剔除该项即可
                , uploadFile: {
                    url: '/ftp/uploadChatFile' //接口地址
                    , type: 'post' //默认post
                }
                //扩展工具栏，下文会做进一步介绍（如果无需扩展，剔除该项即可）
                /*, tool: [{
                    alias: 'code' //工具别名
                    , title: '代码' //工具名称
                    , icon: '&#xe64e;' //工具图标，参考图标文档
                }]*/

               // , msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
                , find: '/op/addPage?userName=' + MineUser.userName + '&deptName=' + MineUser.deptName
                , chatLog: 'op/chatLogPage' //聊天记录页面地址，若不开启，剔除该项即可

            });


            //发消息
            layim.on("sendMessage", function (res) {
                var timestamp = (new Date()).getTime();
                var mine = res.mine; //包含我发送的消息及我的信息
                var to = res.to;
                if (to.type == "friend") {
                    if (connected) {
                        // 创建一个<message>元素并发送
                        var msg = $msg({
                            to: to.id + '@' + SERVICE_NAME,
                            from: mine.id + '@' + SERVICE_NAME,
                            type: 'chat',
                            name: mine.name
                        }).c("body", mine.content).up().c('avatar', mine.avatar).up().c('username', mine.username).up().c('toType', to.type).up().c('timestamp', timestamp);
                        connection.send(msg.tree());
                        new AjaxRequest("/op/insertChatLog", {
                            chatType: 0,
                            chatUserId: mine.id,
                            chatToUserId: to.id,
                            chatTimestamp: timestamp,
                            chatBody: mine.content
                        }).execute();
                    }
                }
                if (to.type == "group") {
                    var jids = new AjaxRequest("/op/getJidsByRoomId", {roomid: to.id}, null).execute();
                    for (var i in jids) {
                        var jid = jids[i];
                        if (jid != mine.id + '@' + SERVICE_NAME) {
                            if (connected) {
                                // 创建一个<message>元素并发送
                                var msg = $msg({
                                    to: jid,
                                    from: mine.id + '@' + SERVICE_NAME,
                                    type: 'chat',
                                    name: mine.name
                                }).c("body", mine.content).up().c('avatar', mine.avatar).up().c('groupname', to.name).up().c('username', mine.username).up().c('toType', to.type).up().c('timestamp', timestamp).up().c('roomId', to.id);
                                connection.send(msg.tree());

                            } else {
                                alert("请先登录！");
                            }
                        }

                    }
                    new AjaxRequest("/op/insertChatLog", {
                        chatType: 1,
                        chatUserId: mine.id,
                        chatToUserId: to.id,
                        chatTimestamp: timestamp,
                        chatBody: mine.content
                    }).execute();
                }

            });


            // 收消息处理
            function onMessage(msg) {
                console.log(msg);
                // 解析出<message>的from、type属性，以及body子元素
                var to = msg.getAttribute('to');
                var id = msg.getAttribute('id');
                var from = msg.getAttribute('from');
                var type = msg.getAttribute('type');
                var body = msg.getElementsByTagName('body');
                var toType = msg.getElementsByTagName('toType');
                var username = msg.getElementsByTagName('username');
                var avatar = msg.getElementsByTagName('avatar');
                var timestamp = msg.getElementsByTagName('timestamp');
                timestamp = Strophe.getText(timestamp[0]);
                //好友上线消息
                if (to == 'all@broadcast.' + SERVICE_NAME && id == 'login') {
                    layim.setFriendStatus(from.substring(0, from.indexOf('@')), 'online');
                }
                //好友聊天消息
                if (Strophe.getText(toType[0]) == "friend" && body.length > 0) {
                    layim.getMessage({
                        username: Strophe.getText(username[0]) //消息来源用户名
                        , avatar: Strophe.getText(avatar[0])//"http://tp1.sinaimg.cn/1571889140/180/40030060651/1" //消息来源用户头像
                        , id: from.substring(0, from.indexOf('@'))//消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
                        , type: Strophe.getText(toType[0])//"group" //聊天窗口来源类型，从发送消息传递的to里面获取
                        , content: Strophe.getText(body[0]) //消息内容
                        //, cid: 0 //消息id，可不传。除非你要对消息进行一些操作（如撤回）
                        //, mine: false //是否我发送的消息，如果为true，则会显示在右方
                        //, fromid: "2" //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
                        , timestamp: parseInt(timestamp) //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
                    });

                }
                //群聊天消息
                if (Strophe.getText(toType[0]) == "group" && body.length > 0) {
                    var roomId = msg.getElementsByTagName('roomId');
                    layim.getMessage({
                        username: Strophe.getText(username[0]) //消息来源用户名
                        , avatar: Strophe.getText(avatar[0])//"http://tp1.sinaimg.cn/1571889140/180/40030060651/1" //消息来源用户头像
                        , id: Strophe.getText(roomId[0])//消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
                        , type: Strophe.getText(toType[0])//"group" //聊天窗口来源类型，从发送消息传递的to里面获取
                        , content: Strophe.getText(body[0]) //消息内容
                        //, cid: 0 //消息id，可不传。除非你要对消息进行一些操作（如撤回）
                        //, mine: mineMes //是否我发送的消息，如果为true，则会显示在右方
                        //, fromid: "2" //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
                        , timestamp: parseInt(timestamp)//服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
                    });

                }
                return true;
            }
            var offlines = new AjaxRequest("/op/getOfflineFriends", {userName:MineUser.userName}).execute();
            for (var i in offlines) {
                var offline = offlines[i];
                layim.setFriendStatus(offline, 'offline'); //设置指定好友在线，即头像置灰
            }


            //下线处理
            function onPresenceOffline(presence) {
                console.log(presence);
                var from = presence.getAttribute('from');
                layim.setFriendStatus(from.substring(0, from.indexOf('@')), 'offline');
            }
            $(".layui-layim-remark").css('display', 'none');
            $(".layim-tool-skin").css('display', 'none');
            $(".layim-tool-about").css('display', 'none');
          //  $("#layui-layim-chatlog").scrollTop(0);
        }
    );
}
