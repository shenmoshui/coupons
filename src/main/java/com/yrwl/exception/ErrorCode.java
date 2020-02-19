package com.yrwl.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  shentao
 * @date    2019-12-24
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /******************校验类********************/
    ERROR_CODE_10001("10001", "Token不能为空"),
    ERROR_CODE_10002("10002", "Token已失效"),
    ERROR_CODE_10003("10003", "Token校验失败"),
    ERROR_CODE_10004("10004", "AppId不能为空"),
    ERROR_CODE_10005("10005", "AppId与Token不一致"),
    ERROR_CODE_10006("10006", "未授权"),
    /******************优惠券********************/
    ERROR_CODE_20001("20001", "优惠券不存在"),
    ERROR_CODE_20002("20002", "优惠券不能一起叠加"),
    ERROR_CODE_20003("20003", "已达到优惠券叠加上限"),
    ERROR_CODE_20004("20004", "优惠券批次不存在"),
    ERROR_CODE_20005("20005", "优惠券废弃异常"),
    ERROR_CODE_20006("20006", "更新文件失败"),
    ERROR_CODE_20007("20007", "删除文件失败"),
    ERROR_CODE_20008("20008", "恢复文件失败"),
    ERROR_CODE_20009("20009", ""),
    ERROR_CODE_20010("20010", ""),
    /******************通用类********************/
    ERROR_CODE_0("0", "请求失败"),
    ERROR_CODE_1("1", "请求参数有误");

    private String errorCode;

    private String errorMsg;

}
