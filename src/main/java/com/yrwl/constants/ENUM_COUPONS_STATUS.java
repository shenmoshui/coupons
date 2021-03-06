package com.yrwl.constants;

import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  shentao
 * @date    2020-02-12
 */
@Getter
@AllArgsConstructor
public enum ENUM_COUPONS_STATUS {
    WAIT(0, "待使用", "优惠券待使用"),
    OVER(1, "已使用", "优惠券已经使用"),
    NO_USE(2, "未到使用期", "优惠券未到使用期"),
    EXPIRY(3, "已过期", "优惠券已过期"),
    DISCARD(4, "废弃", "优惠券已废弃");

    private int code;

    private String name;

    private String tips;

    public static String getTipsByCode(int code){
        for(ENUM_COUPONS_STATUS status : ENUM_COUPONS_STATUS.values()){
            if(status.getCode() == code){

                return status.getTips();
            }
        }

        throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_0);
    }
}
