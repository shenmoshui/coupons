package com.yrwl.api.dao;

import com.yrwl.api.dto.CouponsDTO;
import com.yrwl.api.dto.CouponsPropertyDTO;
import com.yrwl.api.dto.CouponsSeqTypeDTO;
import com.yrwl.api.entity.CouponsEntity;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.entity.CouponsSeqTypeEntity;
import com.yrwl.common.model.PageQueryModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Mapper
public interface CouponsDao {

    /**
     * 优惠券属性类型列表
     * @param map
     * @return
     */
    List<CouponsPropertyEntity> listCouponsProperty(Map<String, String> map);

    /**
     * 优惠券属性类型已过期列表
     * @param map
     * @return
     */
    List<CouponsPropertyEntity> listCouponsPropertyByExpiry(Map<String, String> map);


    /**
     * 优惠券属性类型可设置为可领取列表
     * @param map
     * @return
     */
    List<CouponsPropertyEntity> listCouponsPropertyByReceive(Map<String, String> map);


    /**
     * 优惠券属性类型可设置为停止领取列表
     * @param map
     * @return
     */
    List<CouponsPropertyEntity> listCouponsPropertyByStop(Map<String, String> map);

    /**
     * 优惠券属性从未到使用期设置为待使用
     * @param map
     * @return
     */
    List<CouponsEntity> listCouponsByConsume(Map<String, String> map);

    /**
     * 优惠券属性设置为过期
     * @param map
     * @return
     */
    List<CouponsEntity> listCouponsByExpiry(Map<String, String> map);

    /**
     * 优惠券属性详情
     * @param appId
     * @param couponsSeq
     * @return
     */
    CouponsPropertyEntity getCouponsProperty(@Param("appId") String appId, @Param("couponsSeq") String couponsSeq);

    /**
     * 添加优惠券属性信息
     * @param couponsPropertyDTO
     * @return
     */
    int addCouponsProperty(CouponsPropertyDTO couponsPropertyDTO);

    /**
     * 更新优惠券属性信息
     * @param couponsPropertyDTO
     * @return
     */
    int updateCouponsProperty(CouponsPropertyDTO couponsPropertyDTO);

    /**
     * 已领取数量
     * @param couponsSeq
     * @return
     */
    @Select("select count(1) from coupons where coupons_seq = #{couponsSeq}")
    int takeNum(String couponsSeq);

    /**
     * 优惠券领取记录列表
     * @param map
     * @return
     */
    List<CouponsEntity> listCoupons(Map<String, String> map);

    /**
     * 优惠券领取记录详情
     * @param couponsSeq
     * @param couponsNo
     * @return
     */
    CouponsEntity getCoupons(@Param("couponsSeq") String couponsSeq, @Param("couponsNo") String couponsNo);

    /**
     * 添加领取记录
     * @param couponsDTO
     * @return
     */
    int addCoupons(CouponsDTO couponsDTO);

    /**
     * 更新领取记录
     * @param couponsDTO
     * @return
     */
    int updateCoupons(CouponsDTO couponsDTO);

    /**
     * 叠加优惠券列表
     * @param map
     * @return
     */
    List<CouponsSeqTypeEntity> listCouponsSeqType(Map<String, String> map);

    /**
     * 添加不同批次可叠加优惠券
     * @param couponsSeqTypeDTO
     * @return
     */
    int addCouponsSeqType(CouponsSeqTypeDTO couponsSeqTypeDTO);


    /**
     * 删除不同批次可叠加优惠券
     * @param couponsSeqTypeDTO
     * @return
     */
    int delCouponsSeqType(CouponsSeqTypeDTO couponsSeqTypeDTO);

    /**
     * 获取叠加批次类型，有值则表示可叠加
     * @param list
     * @param size
     * @return
     */
    String getSuperimposition(@Param("list") List<String> list, @Param("size") int size);
}
