window.app = {
    nettyServerUrl: 'ws://localhost:8088/ws',
    serverUrl: 'http://localhost:8080',
    imgServerUrl: 'http://122.152.205.72:88/usher/',

    isNotNull:function (str) {
        return str != null && str !== "" && str !== undefined;
    },

    /**
     * 消息提示框，H5+
     * @param msg
     * @param type
     */
    showToast: function(msg, type) {
        plus.nativeUI.toast(msg,
            {icon: "image/" + type + ".png", verticalAlign: "center"})
    },

    /**
     * 保存用户全局对象
     * @param user
     */
    setUserGlobalInfo: function(user) {
        var userInfoStr = JSON.stringify(user);
        plus.storage.setItem("userInfo", userInfoStr);
    },

    getUserGlobalInfo: function() {
        var userInfoStr = plus.storage.getItem("userInfo");
        return JSON.parse(userInfoStr);
    },

    userLogout:function () {
        plus.storage.removeItem("userInfo");
    }


}