package com.usher.exception;

import com.usher.enums.SearchFriendsStatusEnum;

/**
 * @Author: Usher
 * @Description:
 */
public class ImException extends RuntimeException{
    private Integer code;

    public ImException(SearchFriendsStatusEnum searchFriendsStatusEnum) {
        super(searchFriendsStatusEnum.getMsg());
        this.code = searchFriendsStatusEnum.getStatus();
    }
    public ImException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}