package com.usher.mapper;

import java.util.List;

import com.usher.base.MyMapper;
import com.usher.pojo.Users;
import com.usher.pojo.vo.FriendRequestVO;
import com.usher.pojo.vo.MyFriendsVO;

import org.springframework.stereotype.Repository;

public interface UsersMapperCustom extends MyMapper<Users> {

    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVO> queryMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String> msgIdList);

}