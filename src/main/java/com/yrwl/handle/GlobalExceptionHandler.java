package com.yrwl.handle;

import com.yrwl.common.model.ResponseObject;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author shentao
 * @date  2019-12-13
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseObject businessException(Exception e){

        ErrorCode errorCode = ErrorCode.ERROR_CODE_0;
        if (e instanceof BusinessException){
            log.info("find businessException:e={}",e.getMessage());
            errorCode = ((BusinessException) e).getErrorCode();
            if(errorCode == null){

                return ResponseObject.fail(((BusinessException) e).getCode(), ((BusinessException) e).getMsg());
            }
        }

        return ResponseObject.fail(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseObject methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        log.error("find MethodArgumentNotValidHandler:e={}", LogUtils.getStackTraceInfo(e));
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String msg = fieldErrors.stream().findFirst().map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage())).orElse(null);

        return ResponseObject.fail("0", msg);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseObject exception(Exception e){
        log.error("find exception:e={}", LogUtils.getStackTraceInfo(e));

        return ResponseObject.fail(ErrorCode.ERROR_CODE_0);
    }

}
