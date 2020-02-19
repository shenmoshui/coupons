package com.yrwl.schedule;

import com.yrwl.api.dto.CouponsDTO;
import com.yrwl.api.dto.CouponsPropertyDTO;
import com.yrwl.api.entity.CouponsEntity;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.service.CouponsService;
import com.yrwl.api.service.impl.BaseServiceImpl;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.constants.ENUM_COUPONS_PROPERTY_STATE;
import com.yrwl.constants.ENUM_COUPONS_PROPERTY_STATUS;
import com.yrwl.constants.ENUM_COUPONS_STATUS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  shentao
 * @date    2020-02-15
 */
//@Component
@Slf4j
public class CouponsSchedule extends BaseServiceImpl {

    @Autowired
    private CouponsService couponsService;

    @Scheduled(cron = "0 0/10 0 * * *")
    @Async
    public void listCouponsPropertyByExpiry(){
        PageQueryModel pageQueryModel = new PageQueryModel(1, 100);
        List<CouponsPropertyEntity> list = couponsService.listCouponsPropertyByExpiry(pageQueryModel);
        if(CollectionUtils.isNotEmpty(list)){
            log.info("过期优惠券批次数:" + list.size());
            list.forEach(couponsPropertyEntity -> {
                CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
                couponsPropertyDTO.setAppId(couponsPropertyEntity.getAppId());
                couponsPropertyDTO.setCouponsSeq(couponsPropertyEntity.getCouponsSeq());
                couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATUS.EXPIRY.getCode());

                couponsService.updateCouponsProperty(couponsPropertyDTO);
            });
        }
    }

    @Scheduled(cron = "0 0/3 0 * * *")
    @Async
    public void listCouponsPropertyByReceive(){
        PageQueryModel pageQueryModel = new PageQueryModel(1, 100);
        List<CouponsPropertyEntity> list = couponsService.listCouponsPropertyByReceive(pageQueryModel);
        if(CollectionUtils.isNotEmpty(list)){
            log.info("可领取优惠券批次数:" + list.size());
            list.forEach(couponsPropertyEntity -> {
                CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
                couponsPropertyDTO.setAppId(couponsPropertyEntity.getAppId());
                couponsPropertyDTO.setCouponsSeq(couponsPropertyEntity.getCouponsSeq());
                couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.OK.getCode());

                couponsService.updateCouponsProperty(couponsPropertyDTO);
            });
        }
    }

    @Scheduled(cron = "0 0/5 0 * * *")
    @Async
    public void listCouponsPropertyByStop(){
        PageQueryModel pageQueryModel = new PageQueryModel(1, 100);
        List<CouponsPropertyEntity> list = couponsService.listCouponsPropertyByStop(pageQueryModel);
        if(CollectionUtils.isNotEmpty(list)){
            log.info("停止领取优惠券批次数:" + list.size());
            list.forEach(couponsPropertyEntity -> {
                CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
                couponsPropertyDTO.setAppId(couponsPropertyEntity.getAppId());
                couponsPropertyDTO.setCouponsSeq(couponsPropertyEntity.getCouponsSeq());
                couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATE.STOP.getCode());

                couponsService.updateCouponsProperty(couponsPropertyDTO);
            });
        }
    }

    @Scheduled(cron = "0 0/5 0 * * *")
    @Async
    public void listCouponsByConsume(){
        PageQueryModel pageQueryModel = new PageQueryModel(1, 100);
        List<CouponsEntity> list = couponsService.listCouponsByConsume(pageQueryModel);
        if(CollectionUtils.isNotEmpty(list)){
            log.info("到达使用时间设置为待使用优惠券批次数:" + list.size());
            list.forEach(couponsEntity -> {
                CouponsDTO couponsDTO = new CouponsDTO();
                couponsDTO.setCouponsNo(couponsEntity.getCouponsNo());
                couponsDTO.setCouponsSeq(couponsEntity.getCouponsSeq());
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.WAIT.getCode());

                couponsService.updateCoupons(couponsDTO);
            });
        }
    }

    @Scheduled(cron = "0 0/10 0 * * *")
    @Async
    public void listCouponsByExpiry(){
        PageQueryModel pageQueryModel = new PageQueryModel(1, 100);
        List<CouponsEntity> list = couponsService.listCouponsByExpiry(pageQueryModel);
        if(CollectionUtils.isNotEmpty(list)){
            log.info("设置过期优惠券数:" + list.size());
            list.forEach(couponsEntity -> {
                CouponsDTO couponsDTO = new CouponsDTO();
                couponsDTO.setCouponsNo(couponsEntity.getCouponsNo());
                couponsDTO.setCouponsSeq(couponsEntity.getCouponsSeq());
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.EXPIRY.getCode());

                couponsService.updateCoupons(couponsDTO);
            });
        }
    }
}

