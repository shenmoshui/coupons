package com.yrwl.api.controller;

import com.yrwl.annotation.Auth;
import com.yrwl.api.dto.ConsumeCouponsDTO;
import com.yrwl.api.dto.ReceiveCouponsDTO;
import com.yrwl.api.service.CouponsService;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.common.model.ValidList;
import com.yrwl.constants.CommonConst;
import com.yrwl.utils.DistributedLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author shentao
 * @date 2020-02-12
 */
@Api(tags = "2、优惠券")
@RestController
@RequestMapping(CommonConst.VERSION + "coupons")
@Slf4j
@Auth
public class CouponsController {

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private DistributedLock distributedLock;

    @PostMapping("receive")
    @ApiOperation(value = "领取优惠券", notes = "领取优惠券接口")
    public ResponseObject<String> receiveCoupons(@RequestHeader String appId, @Valid @RequestBody ReceiveCouponsDTO receiveCouponsDTO){
        boolean locked = false;
        String key = CommonConst.LOCK_COUPONS + appId + receiveCouponsDTO.getCouponsSeq();
        try {
            locked = distributedLock.distributedLock(key, appId, "5");
            if(locked){

                return ResponseObject.success(couponsService.receiveCoupons(appId, receiveCouponsDTO));
            }
        }finally {
            if(locked){
                distributedLock.distributedUnlock(key, appId);
            }
        }

        return ResponseObject.fail();
    }

    @PostMapping("consume")
    @ApiOperation(value = "使用优惠券", notes = "使用优惠券接口")
    @ApiImplicitParam(name = "check", value = "是否校验叠加,1:校验，其他:不校验")
    public ResponseObject<String> consumeCoupons(@RequestHeader String appId, @Valid @RequestBody ValidList<ConsumeCouponsDTO> consumeCouponsDTOS, @RequestParam String check){

        couponsService.consumeCoupons(appId, consumeCouponsDTOS, "1".equals(check) ? true : false);

        return ResponseObject.success();
    }

    @PostMapping("discard")
    @ApiOperation(value = "废弃优惠券", notes = "废弃优惠券接口")
    public ResponseObject<String> discardCoupons(@Valid @RequestBody ValidList<ConsumeCouponsDTO> consumeCouponsDTOS){

        couponsService.discardCoupons(consumeCouponsDTOS);

        return ResponseObject.success();
    }
}
