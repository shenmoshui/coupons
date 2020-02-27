package com.yrwl.api.service;

import com.yrwl.api.dto.*;
import com.yrwl.api.entity.CouponsEntity;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.entity.CouponsSeqTypeEntity;
import com.yrwl.common.model.PageQueryModel;

import java.util.List;

/**
 * @author shentao
 * @date 2020-02-12
 */
public interface CouponsService {

    /**
     * 优惠券属性类型列表
     * @param pageQueryModel
     * @return
     */
    List<CouponsPropertyEntity> listCouponsProperty(PageQueryModel pageQueryModel);

    /**
     * 优惠券属性类型已过期列表
     * @param pageQueryModel
     * @return
     */
    List<CouponsPropertyEntity> listCouponsPropertyByExpiry(PageQueryModel pageQueryModel);


    /**
     * 优惠券属性类型可设置为可领取列表
     * @param pageQueryModel
     * @return
     */
    List<CouponsPropertyEntity> listCouponsPropertyByReceive(PageQueryModel pageQueryModel);


    /**
     * 优惠券属性类型可设置为停止领取列表
     * @param pageQueryModel
     * @return
     */
    List<CouponsPropertyEntity> listCouponsPropertyByStop(PageQueryModel pageQueryModel);


    /**
     * 优惠券属性从未到使用期设置为待使用
     * @param pageQueryModel
     * @return
     */
    List<CouponsEntity> listCouponsByConsume(PageQueryModel pageQueryModel);

    /**
     * 优惠券属性设置为过期
     * @param pageQueryModel
     * @return
     */
    List<CouponsEntity> listCouponsByExpiry(PageQueryModel pageQueryModel);
    /**
     * 优惠券属性详情
     * @param appId
     * @param couponsSeq
     * @return
     */
    CouponsPropertyEntity getCouponsProperty(String appId, String couponsSeq);

    /**
     * 添加优惠券属性信息
     * @param couponsPropertyDTO
     * @return
     */
    void addCouponsProperty(CouponsPropertyDTO couponsPropertyDTO);

    /**
     * 更新优惠券属性信息
     * @param couponsPropertyDTO
     * @return
     */
    void updateCouponsProperty(CouponsPropertyDTO couponsPropertyDTO);

    /**
     * 更新优惠券信息
     * @param couponsDTO
     */
    void updateCoupons(CouponsDTO couponsDTO);

    /**
     * 发布优惠券
     * @param issueCouponsPropertyDTO
     */
    void issueCouponsProperty(IssueCouponsPropertyDTO issueCouponsPropertyDTO);

    /**
     * 领取优惠券
     * @param appId
     * @param receiveCouponsDTO
     * @return
     */
    String receiveCoupons(String appId, ReceiveCouponsDTO receiveCouponsDTO);

    /**
     * 使用优惠券
     * @param appId
     * @param consumeCouponsDTOS
     * @param checkFlag
     */
    void consumeCoupons(String appId, List<ConsumeCouponsDTO> consumeCouponsDTOS, boolean checkFlag);

    /**
     *
     * @param consumeCouponsDTOS
     */
    void discardCoupons(List<ConsumeCouponsDTO> consumeCouponsDTOS);

    /**
     * 叠加优惠券列表
     * @param pageQueryModel
     * @return
     */
    List<CouponsSeqTypeEntity> listCouponsSeqType(PageQueryModel pageQueryModel);

    /**
     * 添加不同批次可叠加优惠券
     * @param couponsSeqTypeDTOS
     * @return
     */
    void addCouponsSeqType(List<CouponsSeqTypeDTO> couponsSeqTypeDTOS);

    /**
     * 删除不同批次可叠加优惠券
     * @param couponsSeqTypeDTOS
     * @return
     */
    void delCouponsSeqType(List<CouponsSeqTypeDTO> couponsSeqTypeDTOS);

    /**
     * 将已使用的优惠券重置为待使用状态（退款的情况）
     * @param consumeCouponsDTOS
     */
    void resetCoupons(List<ConsumeCouponsDTO> consumeCouponsDTOS);
}
