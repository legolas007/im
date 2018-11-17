package com.usher.enums;

/**
 * @Author: Usher
 * @Description:
 * 忽略或者通过好友请求的枚举类
 */
public enum OperatorFriendRequestTypeEnum {

    IGNORE(0, "忽略"),
    PASS(1, "通过");

    public final Integer type;
    public final String msg;

    OperatorFriendRequestTypeEnum(Integer type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public static String getMsgByType(Integer type) {
        for (OperatorFriendRequestTypeEnum operType : OperatorFriendRequestTypeEnum.values()) {
            if (operType.getType().equals(type)) {
                return operType.msg;
            }
        }
        return null;
    }

}
