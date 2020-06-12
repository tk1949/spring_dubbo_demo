package org.example.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    private String tokenID;
    private String accountID;
    private String loginName;
    private long loginTime;
    private long createTime;
    private long uploadTime;
    private int status;
}