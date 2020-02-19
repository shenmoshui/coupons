package com.yrwl.api.controller;

import com.yrwl.annotation.Auth;
import com.yrwl.api.dto.CouponsSeqTypeDTO;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.service.CouponsService;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.common.model.PageResultModel;
import com.yrwl.common.model.ResponseObject;
import com.yrwl.common.model.ValidList;
import com.yrwl.constants.CommonConst;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.utils.UUID;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Api(tags = "3、优惠券不同批次叠加")
@RestController
@RequestMapping(CommonConst.VERSION + "superimposition")
@Slf4j
@Auth
public class SuperimpositionController {

    @Autowired
    private CouponsService couponsService;

    @GetMapping("list")
    @ApiOperation(value = "优惠券叠加类型组合列表", notes = "优惠券叠加类型组合列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10", required = true),
            @ApiImplicitParam(name = "couponsSeq", value = "优惠券批次"),
            @ApiImplicitParam(name = "couponsType", value = "优惠券叠加类型"),
    })
    public ResponseObject<PageResultModel<CouponsPropertyEntity>> listUser(@RequestParam int current, @RequestParam int size, @RequestParam(required = false) String couponsSeq, @RequestParam(required = false) String couponsType){

        PageQueryModel pageQueryModel = new PageQueryModel(current, size);
        pageQueryModel.addParameter("couponsSeq", couponsSeq);
        pageQueryModel.addParameter("couponsType", couponsType);

        return ResponseObject.successForPage(couponsService.listCouponsSeqType(pageQueryModel));
    }

    @PostMapping("create")
    @ApiOperation(value = "优惠券叠加类型组合创建", notes = "不同类型的优惠券可叠加使用")
    public ResponseObject<String> createCouponsPropertyType(@RequestBody @ApiParam(value = "优惠券批次数组", required = true) List<String> list){
        if(CollectionUtils.isEmpty(list)){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_1);
        }
        String type = UUID.UU64();
        List<CouponsSeqTypeDTO> couponsSeqTypeDTOS = new ArrayList<>();
        list.forEach(s -> {
            CouponsSeqTypeDTO couponsSeqTypeDTO = new CouponsSeqTypeDTO();
            couponsSeqTypeDTO.setCouponsSeq(s);
            couponsSeqTypeDTO.setCouponsType(type);
            couponsSeqTypeDTOS.add(couponsSeqTypeDTO);
        });
        couponsService.addCouponsSeqType(couponsSeqTypeDTOS);

        return ResponseObject.success();
    }

    @PostMapping("add")
    @ApiOperation(value = "优惠券叠加类型组合新加", notes = "不同类型的优惠券可叠加使用")
    public ResponseObject<String> addCouponsPropertyType(@Valid @RequestBody ValidList<CouponsSeqTypeDTO> couponsSeqTypeDTOS){
        couponsService.addCouponsSeqType(couponsSeqTypeDTOS);

        return ResponseObject.success();
    }

    @PostMapping("del")
    @ApiOperation(value = "优惠券叠加类型组合删除", notes = "不同类型的优惠券可叠加使用")
    public ResponseObject<String> delCouponsPropertyType(@Valid @RequestBody ValidList<CouponsSeqTypeDTO> couponsSeqTypeDTOS){
        couponsService.delCouponsSeqType(couponsSeqTypeDTOS);

        return ResponseObject.success();
    }
}
