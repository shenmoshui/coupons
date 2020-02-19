package com.yrwl.api.controller;

import com.yrwl.annotation.Auth;
import com.yrwl.api.entity.StatisticsEntity;
import com.yrwl.api.service.StatistiscService;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.constants.CommonConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Api(tags = "4、优惠券统计")
@RestController
@RequestMapping(CommonConst.VERSION + "statistics")
@Slf4j
@Auth
public class StatisticsController  {

    @Autowired
    private StatistiscService statistiscService;

    @GetMapping()
    @ApiOperation(value = "统计信息", notes = "统计信息接口")
    public ResponseObject<StatisticsEntity> listUser(@RequestHeader String appId){

        return ResponseObject.success(statistiscService.listStatistics(appId));
    }
}
