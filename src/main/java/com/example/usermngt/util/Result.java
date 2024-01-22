package com.example.usermngt.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(ResultEnum resultEnum, T s) {
        setResultEnum(resultEnum);
        setData(s);
    }


    private void setResultEnum(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    /**
     * 无数据返回
     * @return Result
     */
    public static Result success() {
        Result result = new Result();
        result.setResultEnum(ResultEnum.SUCCESS);

        return result;
    }

    /**
     * 有数据成功返回
     * @param data
     * @return Result
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultEnum(ResultEnum.SUCCESS);
        result.setData(data);

        return result;
    }


    /**
     * 业务错误
     * @param resultEnum
     * @return
     */
    public static Result failed(ResultEnum resultEnum) {
        Result result = new Result();
        result.setResultEnum(resultEnum);

        return result;
    }

    /**
     * 业务错误 -- 表单参数校验
     * @param resultEnum
     * @return
     */
    public static Result failed(ResultEnum resultEnum, List<String> data) {
        Result result = new Result();
        result.setResultEnum(resultEnum);
        result.setData(data);

        return  result;
    }

    /**
     * http状态错误
     * @param code
     * @param msg
     * @return
     */
    public static Result failed(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }


    /**
     * http状态错误
     * @param httpStatus
     * @return
     */
    public static Result failed(HttpStatus httpStatus) {
        Result result = new Result();
        result.setCode(httpStatus.value());
        result.setMsg(httpStatus.getReasonPhrase());

        return result;
    }

}
