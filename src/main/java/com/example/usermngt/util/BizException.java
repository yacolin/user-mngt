package com.example.usermngt.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private ResultEnum resultEnum;

    private List<String> data;

    public BizException(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public BizException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
