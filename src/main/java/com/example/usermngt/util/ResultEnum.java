package com.example.usermngt.util;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResultEnum {
    // 成功状态码
    SUCCESS(200, "success"),

    // 错误状态码
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "not found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 业务状态码
    NO_TOKEN_FOUND(4001, "no token found"),
    TOKEN_ILLEGAL(4002, "token illegal"),
    TOKEN_EXPIRED(4003, "token expired"),
    USER_NOT_EXIST(10001, "User not exist"),
    USER_ALREADY_EXISTS(10002, "user already exist"),
    WRONG_PASSWORD(1003, "wrong password"),
    DATA_NOT_EXIST(20003, "data not exist"),
    PARAMS_EXCEPTION(20004, "params exception")
    ;


    private int code;
    private String msg;

}
