package com.usher.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Usher
 * @Description:
 */
@Data
public class NettyChatMsg implements Serializable {
    private static final long serialVersionUID = 6412993087956450487L;
    private String senderId;
    private String receiverId;
    private String msg;
    private String msgId;


}
