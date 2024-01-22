package com.example.usermngt.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Slf4j
public class FormValidate {
    // 校验表单参数
    public static void validateField(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            String errorMsg = fieldErrors.get(0).getDefaultMessage();
            throw new BizException(
                    ResultEnum.PARAMS_EXCEPTION.getCode(), errorMsg);
        }
    }
}
