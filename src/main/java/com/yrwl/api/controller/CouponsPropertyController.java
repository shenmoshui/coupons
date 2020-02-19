package com.yrwl.api.controller;

import com.yrwl.annotation.Auth;
import com.yrwl.api.dto.AddCouponsPropertyDTO;
import com.yrwl.api.dto.CouponsPropertyDTO;
import com.yrwl.api.dto.IssueCouponsPropertyDTO;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.service.CouponsService;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.common.model.PageResultModel;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.constants.CommonConst;
import com.yrwl.constants.ENUM_COUPONS_PROPERTY_STATE;
import com.yrwl.constants.ENUM_COUPONS_PROPERTY_STATUS;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.utils.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Api(tags = "1、优惠券属性")
@RestController
@RequestMapping(CommonConst.VERSION + "property")
@Slf4j
@Auth
public class CouponsPropertyController {

    @Autowired
    private CouponsService couponsService;

    @GetMapping("list")
    @ApiOperation(value = "获取优惠券属性列表", notes = "优惠券属性列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10", required = true)
    })
    public ResponseObject<PageResultModel<CouponsPropertyEntity>> listUser(@RequestParam int current, @RequestParam int size, @RequestHeader String appId){

        PageQueryModel pageQueryModel = new PageQueryModel(current, size);
        pageQueryModel.addParameter("appId", appId);

        return ResponseObject.successForPage(couponsService.listCouponsProperty(pageQueryModel));
    }

    @PostMapping("create")
    @ApiOperation(value = "创建优惠券", notes = "创建优惠券接口")
    public ResponseObject<String> addCouponsProperty(@Valid @RequestBody AddCouponsPropertyDTO addCouponsPropertyDTO){
        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(addCouponsPropertyDTO, couponsPropertyDTO);
        String couponsSeq = UUID.UU64();
        log.info("优惠券批次:{}", couponsSeq);
        couponsPropertyDTO.setCouponsSeq(couponsSeq);
        couponsService.addCouponsProperty(couponsPropertyDTO);

        return ResponseObject.success(couponsSeq);
    }

    @PostMapping("issue")
    @ApiOperation(value = "发布优惠券", notes = "发布优惠券接口")
    public ResponseObject<String> issueCouponsProperty(@Valid @RequestBody IssueCouponsPropertyDTO issueCouponsPropertyDTO){
        couponsService.issueCouponsProperty(issueCouponsPropertyDTO);

        return ResponseObject.success();
    }

    @PostMapping("stopConsume")
    @ApiOperation(value = "停止使用优惠券", notes = "停止使用优惠券接口")
    public ResponseObject<String> stopConsumeCouponsProperty(@Valid @RequestBody IssueCouponsPropertyDTO issueCouponsPropertyDTO){
        CouponsPropertyEntity couponsPropertyEntity = couponsService.getCouponsProperty(issueCouponsPropertyDTO.getAppId(), issueCouponsPropertyDTO.getCouponsSeq());
        if(couponsPropertyEntity == null){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20004);
        }//状态不是已发布，不能停止
        else if(ENUM_COUPONS_PROPERTY_STATUS.OK.getCode() != couponsPropertyEntity.getStatus()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(couponsPropertyEntity.getStatus()));
        }

        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(issueCouponsPropertyDTO, couponsPropertyDTO);
        couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATUS.STOP.getCode());
        couponsService.updateCouponsProperty(couponsPropertyDTO);

        return ResponseObject.success();
    }

    @PostMapping("renewConsume")
    @ApiOperation(value = "恢复使用优惠券", notes = "恢复使用优惠券接口")
    public ResponseObject<String> renewConsumeCouponsProperty(@Valid @RequestBody IssueCouponsPropertyDTO issueCouponsPropertyDTO){
        CouponsPropertyEntity couponsPropertyEntity = couponsService.getCouponsProperty(issueCouponsPropertyDTO.getAppId(), issueCouponsPropertyDTO.getCouponsSeq());
        if(couponsPropertyEntity == null){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20004);
        }//状态不是已停止，不能恢复
        else if(ENUM_COUPONS_PROPERTY_STATUS.STOP.getCode() != couponsPropertyEntity.getStatus()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(couponsPropertyEntity.getStatus()));
        }

        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(issueCouponsPropertyDTO, couponsPropertyDTO);
        couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATUS.OK.getCode());
        couponsService.updateCouponsProperty(couponsPropertyDTO);

        return ResponseObject.success();
    }

    @PostMapping("discard")
    @ApiOperation(value = "废弃优惠券批次", notes = "废弃优惠券批次接口")
    public ResponseObject<String> discardCouponsProperty(@Valid @RequestBody IssueCouponsPropertyDTO issueCouponsPropertyDTO){
        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(issueCouponsPropertyDTO, couponsPropertyDTO);
        couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATUS.DISCARD.getCode());
        couponsService.updateCouponsProperty(couponsPropertyDTO);

        return ResponseObject.success();
    }

    @PostMapping("stopReceive")
    @ApiOperation(value = "停止领取优惠券", notes = "停止领取优惠券接口")
    public ResponseObject<String> stopReceiveCouponsProperty(@Valid @RequestBody IssueCouponsPropertyDTO issueCouponsPropertyDTO){
        CouponsPropertyEntity couponsPropertyEntity = couponsService.getCouponsProperty(issueCouponsPropertyDTO.getAppId(), issueCouponsPropertyDTO.getCouponsSeq());
        if(couponsPropertyEntity == null){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20004);
        }//状态不是可领取，不能停止
        else if(ENUM_COUPONS_PROPERTY_STATE.OK.getCode() != couponsPropertyEntity.getStatus()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATE.getTipsByCode(couponsPropertyEntity.getState()));
        }

        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(issueCouponsPropertyDTO, couponsPropertyDTO);
        couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.STOP.getCode());
        couponsService.updateCouponsProperty(couponsPropertyDTO);

        return ResponseObject.success();
    }

    @PostMapping("renewReceive")
    @ApiOperation(value = "恢复领取优惠券", notes = "恢复领取优惠券接口")
    public ResponseObject<String> renewReceiveCouponsProperty(@Valid @RequestBody IssueCouponsPropertyDTO issueCouponsPropertyDTO){
        CouponsPropertyEntity couponsPropertyEntity = couponsService.getCouponsProperty(issueCouponsPropertyDTO.getAppId(), issueCouponsPropertyDTO.getCouponsSeq());
        if(couponsPropertyEntity == null){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20004);
        }//状态不是停止，不能恢复
        else if(ENUM_COUPONS_PROPERTY_STATE.STOP.getCode() != couponsPropertyEntity.getStatus()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATE.getTipsByCode(couponsPropertyEntity.getState()));
        }

        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(issueCouponsPropertyDTO, couponsPropertyDTO);
        couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.OK.getCode());
        couponsService.updateCouponsProperty(couponsPropertyDTO);

        return ResponseObject.success();
    }

}
