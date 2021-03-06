package com.usher.service.impl;

import com.usher.enums.SearchFriendsStatusEnum;
import com.usher.mapper.FriendsRequestMapper;
import com.usher.mapper.MyFriendsMapper;
import com.usher.mapper.UsersMapperCustom;
import com.usher.netty.UserChannelRel;
import com.usher.pojo.FriendsRequest;
import com.usher.pojo.MyFriends;
import com.usher.pojo.vo.FriendRequestVO;
import com.usher.pojo.vo.MyFriendsVO;
import com.usher.utils.FastDFSClient;
import com.usher.utils.FileUtils;
import com.usher.utils.QRCodeUtils;
import io.netty.channel.Channel;
import org.n3r.idworker.Sid;
import com.usher.mapper.UsersMapper;
import com.usher.pojo.Users;
import com.usher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author: Usher
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        Users result = userMapper.selectOne(user);
        return result != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String pwd) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", pwd);

        return userMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUser(Users user) {
        String userId = sid.nextShort();

        // 为每个用户生成一个唯一的二维码
        String qrCodePath = "D://user" + userId + "qrcode.png";
        // u_qrcode:[username]
        qrCodeUtils.createQRCode(qrCodePath, "u_qrcode:" + user.getUsername());
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);

        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setQrcode(qrCodeUrl);
        user.setId(userId);
        userMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(Users user) {
        userMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    Users queryUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
