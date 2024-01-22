package com.example.usermngt.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
    }

    // 获取URI
    public static String getRequestURI(WebRequest webRequest) {
        String requestUri = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        return requestUri;
    }

    // 处理所有未被其他方法处理的异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        Result result = Result.failed(HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("message: {}", e.getMessage());

        return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 处理400错误（例如，请求体无法解析或参数验证失败）
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequestException(Exception e) {
        Result result = Result.failed(ResultEnum.BAD_REQUEST);

        log.error("message: {}", e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    // 404错误
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNotFoundException(NoHandlerFoundException e) {
        Result result = Result.failed(ResultEnum.NOT_FOUND);
        return new ResponseEntity(result, HttpStatus.NOT_FOUND);
    }

    // 处理HTTP客户端错误或服务器错误
    @ExceptionHandler(value = {HttpClientErrorException.class, HttpServerErrorException.class})
    public ResponseEntity<Object> handleHttpException(Exception e) {
        Result result = Result.failed(ResultEnum.INTERNAL_SERVER_ERROR);
        HttpStatusCode status = ((HttpStatusCodeException) e).getStatusCode();
        log.error("message: {}", e.getMessage());

        return new ResponseEntity<>(result, status);
    }


    // 自定义业务逻辑异常处理
    @ExceptionHandler(value = BizException.class)
    public Result bizExceptionHandler(BizException e) {
        if (e.getCode() != 0) {
            log.error("{}", e.getMsg());
            return Result.failed(e.getCode(), e.getMsg());
        }
        return Result.failed(e.getResultEnum(), e.getData());
    }
}
