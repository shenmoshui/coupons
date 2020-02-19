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
public enum ENUM_COUPONS_PROPERTY_STATE {

    NO(0, "领取未开始", "未到领取时间"),
    OK(1, "可领取", "优惠券数量充足"),
    STOP(2, "停止领取", "优惠券暂不能领取"),
    OVER(3, "已领完", "优惠券已领完");

    private int code;

    private String name;

    private String tips;

    public static String getTipsByCode(int code){
        for(ENUM_COUPONS_PROPERTY_STATE status : ENUM_COUPONS_PROPERTY_STATE.values()){
            if(status.getCode() == code){

                return status.getTips();
            }
        }

        throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_0);
    }
}
