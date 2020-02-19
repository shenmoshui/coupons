package com.yrwl.api.controller;

import com.yrwl.annotation.NoAuth;
import com.yrwl.api.dto.AddThirdPlatformDTO;
import com.yrwl.api.dto.ThirdPlatformDTO;
import com.yrwl.api.dto.UpdateThirdPlatformDTO;
import com.yrwl.api.entity.ThirdPlatformEntity;
import com.yrwl.api.service.ThirdPlatformService;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.common.model.PageResultModel;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.constants.CommonConst;
import com.yrwl.constants.ENUM_THIRD_PLATFORM_STATUS;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.utils.JodaTimeUtils;
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
import java.util.Date;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Api(tags = "2、第三方平台")
@RestController
@RequestMapping(CommonConst.VERSION + "thirdPlatform")
@Slf4j
@NoAuth
public class ThirdPlatformController {

    @Autowired
    private ThirdPlatformService thirdPlatformService;

    @GetMapping("list")
    @ApiOperation(value = "获取第三方平台列表", notes = "第三方平台列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10", required = true)
    })
    public ResponseObject<PageResultModel<ThirdPlatformEntity>> listUser(@RequestParam int current, @RequestParam int size){

        PageQueryModel pageQueryModel = new PageQueryModel(current, size);

        return ResponseObject.successForPage(thirdPlatformService.listThirdPlatform(pageQueryModel));
    }

    @GetMapping()
    @ApiOperation(value = "获取获取第三方平台信息", notes = "获取第三方平台信息接口")
    @ApiImplicitParam(name = "appId", value = "第三方平台唯一标识", required = true)
    public ResponseObject<ThirdPlatformEntity> getThirdPlatform(@RequestParam String appId){

        return ResponseObject.success(thirdPlatformService.getThirdPlatform(appId));
    }

    @PostMapping("add")
    @ApiOperation(value = "添加第三方平台", notes = "添加第三方平台接口")
    public ResponseObject<String> addThirdPlatform(@Valid @RequestBody AddThirdPlatformDTO addThirdPlatformDTO){
        ThirdPlatformDTO thirdPlatformDTO = new ThirdPlatformDTO();
        BeanUtils.copyProperties(addThirdPlatformDTO, thirdPlatformDTO);
        thirdPlatformDTO.setAppId(UUID.UU64());
        thirdPlatformDTO.setAppSecret(UUID.UU64());
        thirdPlatformService.addThirdPlatform(thirdPlatformDTO);

        return ResponseObject.success();
    }

    @PostMapping("update")
    @ApiOperation(value = "更新第三方平台信息", notes = "更新第三方平台信息接口")
    public ResponseObject<String> updateThirdPlatform(@Valid @RequestBody UpdateThirdPlatformDTO updateThirdPlatformDTO){
        ThirdPlatformDTO thirdPlatformDTO = new ThirdPlatformDTO();
        BeanUtils.copyProperties(updateThirdPlatformDTO, thirdPlatformDTO);
        thirdPlatformService.updateThirdPlatform(thirdPlatformDTO);

        return ResponseObject.success();
    }

    @GetMapping("stop")
    @ApiOperation(value = "暂停第三方平台合作", notes = "暂停第三方平台合作接口")
    @ApiImplicitParam(name = "appId", value = "第三方平台唯一标识", required = true)
    public ResponseObject<String> stopThirdPlatform(@RequestParam String appId){
        ThirdPlatformDTO thirdPlatformDTO = new ThirdPlatformDTO();
        thirdPlatformDTO.setAppId(appId);
        thirdPlatformDTO.setStatus(ENUM_THIRD_PLATFORM_STATUS.STOP.getCode());
        thirdPlatformService.updateThirdPlatform(thirdPlatformDTO);

        return ResponseObject.success();
    }

    @GetMapping("start")
    @ApiOperation(value = "重启第三方平台合作", notes = "重启第三方平台合作接口")
    @ApiImplicitParam(name = "appId", value = "第三方平台唯一标识", required = true)
    public ResponseObject<String> startThirdPlatform(@RequestParam String appId){
        ThirdPlatformDTO thirdPlatformDTO = new ThirdPlatformDTO();
        thirdPlatformDTO.setAppId(appId);
        thirdPlatformDTO.setStatus(ENUM_THIRD_PLATFORM_STATUS.START.getCode());
        thirdPlatformService.updateThirdPlatform(thirdPlatformDTO);

        return ResponseObject.success();
    }

    @GetMapping("expiry")
    @ApiOperation(value = "延长第三方平台合作有效期", notes = "延长第三方平台合作有效期接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "第三方平台唯一标识", required = true),
            @ApiImplicitParam(name = "expiryDate", value = "合作有效期", required = true)
    })
    public ResponseObject<String> expiryThirdPlatform(@RequestParam String appId, @RequestParam String expiryDate){
        if (JodaTimeUtils.strToDate(expiryDate, JodaTimeUtils.STANDARD_DATE).before(new Date())){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_1);
        }
        ThirdPlatformDTO thirdPlatformDTO = new ThirdPlatformDTO();
        thirdPlatformDTO.setAppId(appId);
        thirdPlatformDTO.setExpiryDate(expiryDate);
        thirdPlatformDTO.setStatus(ENUM_THIRD_PLATFORM_STATUS.START.getCode());
        thirdPlatformService.updateThirdPlatform(thirdPlatformDTO);

        return ResponseObject.success();
    }

}
