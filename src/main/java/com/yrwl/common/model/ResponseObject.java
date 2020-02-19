package com.yrwl.common.model;


import com.yrwl.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author  shentao
 * @date    2019-12-24
 */
@Getter
@Setter
@ApiModel(value = "ResponseObject", description = "统一返回实体")
public class ResponseObject<T> {

    private static final String CODE_SUCCESS = "1";

    @ApiModelProperty(value = "返回码", name = "code", example = "1:成功，其他:失败")
    private String code;
    @ApiModelProperty(value = "返回内容")
    private T data;
    @ApiModelProperty(value = "返回信息", name = "message", example = "请求成功")
    private String msg;

    public ResponseObject(){}

    public ResponseObject(String code, String... msg){
        this.code = code;
        if(msg != null && msg.length > 0){
            this.msg = msg[0];
        }
    }

    public ResponseObject(String code, T data, String... msg){
        this.code = code;
        this.data = data;
        if(msg != null && msg.length > 0){
            this.msg = msg[0];
        }
    }

    public static <T> ResponseObject<T> success(){
        return new ResponseObject(CODE_SUCCESS, "请求成功!");
    }

    public static <T> ResponseObject<T> success(T data){
        return new ResponseObject(CODE_SUCCESS, data,"请求成功!");
    }

    public static <T> ResponseObject<T> success(T data, String... msg){
        return new ResponseObject(CODE_SUCCESS, data, msg);
    }

    public static ResponseObject successForPage(List list){
        return new ResponseObject(CODE_SUCCESS, new PageResultModel<>(list),"请求成功!");
    }

    public static <T> ResponseObject<T> fail(){
        return new ResponseObject(ErrorCode.ERROR_CODE_0.getErrorCode(), ErrorCode.ERROR_CODE_0.getErrorMsg());
    }

    public static <T> ResponseObject<T> fail(ErrorCode errorCode){
        return new ResponseObject(errorCode.getErrorCode(), errorCode.getErrorMsg());
    }

    public static <T> ResponseObject<T> fail(String code, String msg){

        return new ResponseObject(code, msg);
    }

}
