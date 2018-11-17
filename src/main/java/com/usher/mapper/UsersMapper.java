package com.usher.mapper;

import com.usher.pojo.Users;
import com.usher.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper extends MyMapper<Users> {
}