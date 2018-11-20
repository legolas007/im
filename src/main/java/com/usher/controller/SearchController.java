package com.usher.controller;

import com.usher.enums.OperatorFriendRequestTypeEnum;
import com.usher.enums.SearchFriendsStatusEnum;
import com.usher.pojo.Users;
import com.usher.pojo.vo.MyFriendsVO;
import com.usher.pojo.vo.UsersVO;
import com.usher.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import com.usher.service.UserService;
import com.usher.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Usher
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("u")
public class SearchController {
    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    /**
     * 搜索好友接口
     *
     * @param myUserId
     * @param friendUsername
     * @return
     */
    @PostMapping("/search")
    public JsonResult searchUser(String myUserId, String friendUsername) {
        if (StringUtils.isBlank(myUserId)
                || StringUtils.isBlank(friendUsername)) {
            return JsonResult.errorMsg("");
        }

        Integer status = searchService.preconditionSearchFriends(myUserId, friendUsername);
        if (status.equals(SearchFriendsStatusEnum.SUCCESS.status)) {
            Users user = searchService.queryUserInfoByUsername(friendUsername);
            UsersVO usersVO = new UsersVO();
            BeanUtils.copyProperties(user, usersVO);
            return JsonResult.ok(usersVO);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JsonResult.errorMsg(errorMsg);
        }
    }

    /**
     * 发送添加好友的接口
     *
     * @param myUserId
     * @param friendUsername
     * @return
     * @throws Exception
     */
    @PostMapping("/addFriendRequest")
    public JsonResult addFriendRequest(String myUserId, String friendUsername)
            throws Exception {
        if (StringUtils.isBlank(myUserId)
                || StringUtils.isBlank(friendUsername)) {
            return JsonResult.errorMsg("");
        }

        Integer status = searchService.preconditionSearchFriends(myUserId, friendUsername);
        if (status.equals(SearchFriendsStatusEnum.SUCCESS.status)) {
            searchService.sendFriendRequest(myUserId, friendUsername);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JsonResult.errorMsg(errorMsg);
        }

        return JsonResult.ok();
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping("/queryFriendRequests")
    public JsonResult queryFriendRequests(@RequestParam("userId") String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("");
        }

        // 1. 查询用户接受到的朋友申请
        return JsonResult.ok(searchService.queryFriendRequestList(userId));
    }

    /**
     * 接受方 通过或者忽略朋友请求
     *
     * @param acceptUserId
     * @param sendUserId
     * @param operType
     * @return
     */
    @PostMapping("/operFriendRequest")
    public JsonResult operFriendRequest(String acceptUserId, String sendUserId,
                                        Integer operType) {

        if (StringUtils.isBlank(acceptUserId)
                || StringUtils.isBlank(sendUserId)
                || operType == null) {
            return JsonResult.errorMsg("");
        }
        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
            return JsonResult.errorMsg("");
        }

        if (operType.equals(OperatorFriendRequestTypeEnum.IGNORE.type)) {
            //  判断如果忽略好友请求，则直接删除好友请求的数据库表记录
            searchService.deleteFriendRequest(sendUserId, acceptUserId);
        } else if (operType.equals(OperatorFriendRequestTypeEnum.PASS.type)) {
            // 判断如果是通过好友请求，则互相增加好友记录到数据库对应的表
            //	   然后删除好友请求的数据库表记录
            searchService.passFriendRequest(sendUserId, acceptUserId);
        }

        // 数据库查询好友列表
        List<MyFriendsVO> myFirends = searchService.queryMyFriends(acceptUserId);

        return JsonResult.ok(myFirends);
    }

    /**
     * @Description: 查询我的好友列表
     */
    @PostMapping("/myFriends")
    public JsonResult myFriends(String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("");
        }
        List<MyFriendsVO> myFirends = searchService.queryMyFriends(userId);

        return JsonResult.ok(myFirends);
    }

}
