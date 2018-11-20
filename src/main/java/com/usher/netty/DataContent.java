package com.usher.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Usher
 * @Description:
 */
@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = 8049612939620569770L;
    //动作类型
    private Integer action;
    private NettyChatMsg nettyChatMsg;
    //扩展字段
    private String extend;

}
