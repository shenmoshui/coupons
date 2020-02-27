package com.yrwl.api.service.impl;

import com.yrwl.api.dao.CouponsDao;
import com.yrwl.api.dto.*;
import com.yrwl.api.entity.CouponsEntity;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.entity.CouponsSeqTypeEntity;
import com.yrwl.api.service.CouponsService;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.constants.ENUM_COUPONS_PROPERTY_STATE;
import com.yrwl.constants.ENUM_COUPONS_PROPERTY_STATUS;
import com.yrwl.constants.ENUM_COUPONS_STATUS;
import com.yrwl.exception.BusinessException;
import com.yrwl.exception.ErrorCode;
import com.yrwl.utils.JodaTimeUtils;
import com.yrwl.utils.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author shentao
 * @date 2020-02-12
 */
@Service
@Transactional(rollbackFor = {BusinessException.class, Exception.class})
public class CouponsServiceImpl extends BaseServiceImpl implements CouponsService {

    @Autowired
    private CouponsDao couponsDao;

    @Override
    public List<CouponsPropertyEntity> listCouponsProperty(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsProperty(pageQueryModel.getQueryParams());
    }

    @Override
    public List<CouponsPropertyEntity> listCouponsPropertyByExpiry(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsPropertyByExpiry(pageQueryModel.getQueryParams());
    }

    @Override
    public List<CouponsPropertyEntity> listCouponsPropertyByReceive(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsPropertyByStop(pageQueryModel.getQueryParams());
    }

    @Override
    public List<CouponsPropertyEntity> listCouponsPropertyByStop(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsPropertyByStop(pageQueryModel.getQueryParams());
    }

    @Override
    public List<CouponsEntity> listCouponsByConsume(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsByConsume(pageQueryModel.getQueryParams());
    }

    @Override
    public List<CouponsEntity> listCouponsByExpiry(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsByExpiry(pageQueryModel.getQueryParams());
    }

    @Override
    public CouponsPropertyEntity getCouponsProperty(String appId, String couponsSeq) {

        return couponsDao.getCouponsProperty(appId, couponsSeq);
    }

    @Override
    public void addCouponsProperty(CouponsPropertyDTO couponsPropertyDTO) {
        if(couponsDao.addCouponsProperty(couponsPropertyDTO) <= 0){
            throw BusinessException.withErrorCode("优惠券属性添加异常");
        }
    }

    @Override
    public void updateCouponsProperty(CouponsPropertyDTO couponsPropertyDTO) {
        if(couponsDao.updateCouponsProperty(couponsPropertyDTO) <= 0){
            throw BusinessException.withErrorCode("优惠券属性更新异常");
        }
    }

    @Override
    public void updateCoupons(CouponsDTO couponsDTO) {
        if(couponsDao.updateCoupons(couponsDTO) <= 0){
            throw BusinessException.withErrorCode("优惠券更新异常");
        }
    }

    @Override
    public void issueCouponsProperty(IssueCouponsPropertyDTO issueCouponsPropertyDTO) {
        CouponsPropertyEntity couponsPropertyEntity = couponsDao.getCouponsProperty(issueCouponsPropertyDTO.getAppId(), issueCouponsPropertyDTO.getCouponsSeq());
        if(couponsPropertyEntity == null){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20004);
        }//状态不是待发布，不能发布
        else if(ENUM_COUPONS_PROPERTY_STATUS.WAIT.getCode() != couponsPropertyEntity.getStatus()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(couponsPropertyEntity.getStatus()));
        }

        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        BeanUtils.copyProperties(issueCouponsPropertyDTO, couponsPropertyDTO);
        couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATUS.OK.getCode());
        //优惠券达到可领取的时间，设置为可领取
        String nowDate = JodaTimeUtils.dateToStr(new Date(), JodaTimeUtils.STANDARD_DATE);
        if(ENUM_COUPONS_PROPERTY_STATE.NO.getCode() == couponsPropertyEntity.getState() &&
            nowDate.compareTo(couponsPropertyEntity.getReceiveBeginDate()) >= 0 &&
            nowDate.compareTo(couponsPropertyEntity.getReceiveEndDate()) <= 0){
            couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.OK.getCode());
        }

        updateCouponsProperty(couponsPropertyDTO);
    }

    @Override
    public String receiveCoupons(String appId, ReceiveCouponsDTO receiveCouponsDTO) {
        //优惠券已发布，并且优惠券仍可领
        String couponsSeq = receiveCouponsDTO.getCouponsSeq();
        CouponsPropertyEntity couponsPropertyEntity = couponsDao.getCouponsProperty(appId, couponsSeq);
        if(couponsPropertyEntity == null){
            throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20001);
        }
        String nowDate = JodaTimeUtils.dateToStr(new Date());
        CouponsPropertyDTO couponsPropertyDTO = new CouponsPropertyDTO();
        couponsPropertyDTO.setAppId(appId);
        couponsPropertyDTO.setCouponsSeq(couponsSeq);
        //判断有效期,过期则设置为过期并提示
        if(nowDate.compareTo(couponsPropertyEntity.getExpiryDate()) > 0
            && couponsPropertyEntity.getStatus() < ENUM_COUPONS_PROPERTY_STATUS.EXPIRY.getCode()){
            couponsPropertyDTO.setStatus(ENUM_COUPONS_PROPERTY_STATUS.EXPIRY.getCode());
            updateCouponsProperty(couponsPropertyDTO);

            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(ENUM_COUPONS_PROPERTY_STATUS.EXPIRY.getCode()));
        }
        //判断是否可以领取，若是达到条件，则重新设置一次条件
        if(couponsPropertyEntity.getStatus() < ENUM_COUPONS_PROPERTY_STATUS.STOP.getCode() &&
                couponsPropertyEntity.getState() == ENUM_COUPONS_PROPERTY_STATE.NO.getCode() &&
                nowDate.compareTo(couponsPropertyEntity.getReceiveBeginDate()) >= 0 &&
                nowDate.compareTo(couponsPropertyEntity.getReceiveEndDate()) <= 0){
            couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.OK.getCode());
            updateCouponsProperty(couponsPropertyDTO);
            //重新赋值，用于下面的判断
            couponsPropertyEntity.setState(ENUM_COUPONS_PROPERTY_STATE.OK.getCode());
        }
        else if(couponsPropertyEntity.getStatus() < ENUM_COUPONS_PROPERTY_STATUS.STOP.getCode() &&
                couponsPropertyEntity.getState() < ENUM_COUPONS_PROPERTY_STATE.STOP.getCode() &&
                nowDate.compareTo(couponsPropertyEntity.getReceiveEndDate()) > 0){
            couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.STOP.getCode());
            updateCouponsProperty(couponsPropertyDTO);

            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(ENUM_COUPONS_PROPERTY_STATE.STOP.getCode()));
        }
        //优惠券不是已发布状态
        if(ENUM_COUPONS_PROPERTY_STATUS.OK.getCode() != couponsPropertyEntity.getStatus()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(couponsPropertyEntity.getStatus()));
        }//优惠券不是可领取状态
        else if(ENUM_COUPONS_PROPERTY_STATE.OK.getCode() != couponsPropertyEntity.getState()){
            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATE.getTipsByCode(couponsPropertyEntity.getState()));
        }//优惠券被领取完，更新优惠券属性状态
        else if(couponsPropertyEntity.getNum() <= couponsDao.takeNum(couponsSeq)){
            couponsPropertyDTO.setState(ENUM_COUPONS_PROPERTY_STATE.OVER.getCode());
            updateCouponsProperty(couponsPropertyDTO);

            throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATE.OVER.getTips());
        }
        else{
            String couponsNo = UUID.UU64();
            CouponsDTO couponsDTO = new CouponsDTO();
            BeanUtils.copyProperties(receiveCouponsDTO, couponsDTO);
            couponsDTO.setCouponsNo(couponsNo);
            couponsDTO.setAppId(appId);
            //使用时间校验
            if(nowDate.compareTo(receiveCouponsDTO.getExpiryBeginDate()) < 0){
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.NO_USE.getCode());
            }
            else if(nowDate.compareTo(receiveCouponsDTO.getExpiryEndDate()) > 0){
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.EXPIRY.getCode());
            }
            else {
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.WAIT.getCode());
            }

            if(couponsDao.addCoupons(couponsDTO) <= 0){
                throw BusinessException.withErrorCode("领取优惠券异常");
            }

            return couponsNo;
        }
    }

    @Override
    public void consumeCoupons(String appId, List<ConsumeCouponsDTO> consumeCouponsDTOS, boolean checkFlag) {
        Map<String, Integer> map = new HashMap<>(10);
        Map<String, Integer> result = new HashMap<>(10);
        List<String> list = new ArrayList();
        consumeCouponsDTOS.forEach(consumeCouponsDTO -> list.add(consumeCouponsDTO.getCouponsSeq()));
        //校验是否可以叠加
        if(checkFlag){
            if(StringUtils.isBlank(couponsDao.getSuperimposition(list, list.size()))){
                throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20002);
            }
        }
        //筛选使用同个批次多张优惠券的数据
        list.forEach(l -> {
            if(map.get(l) != null){
                Integer num = map.get(l) + 1;
                map.put(l, num);
                result.put(l, num);
            }
            else{
                map.put(l, 1);
            }
        });
        String nowDate = JodaTimeUtils.dateToStr(new Date());
        consumeCouponsDTOS.forEach(consumeCouponsDTO -> {
            //优惠券已发布，并且优惠券仍可领
            CouponsPropertyEntity couponsPropertyEntity = couponsDao.getCouponsProperty(appId, consumeCouponsDTO.getCouponsSeq());
            CouponsEntity couponsEntity = couponsDao.getCoupons(consumeCouponsDTO.getCouponsSeq(), consumeCouponsDTO.getCouponsNo());
            if(couponsPropertyEntity == null || couponsEntity == null){
                throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20001);
            }//优惠券不是已发布状态
            else if(ENUM_COUPONS_PROPERTY_STATUS.OK.getCode() != couponsPropertyEntity.getStatus()){
                throw BusinessException.withErrorCode(ENUM_COUPONS_PROPERTY_STATUS.getTipsByCode(couponsPropertyEntity.getStatus()));
            }//优惠券不是待使用状态
            else if(ENUM_COUPONS_STATUS.WAIT.getCode() != couponsEntity.getStatus()
                && ENUM_COUPONS_STATUS.NO_USE.getCode() != couponsEntity.getStatus()){
                throw BusinessException.withErrorCode(ENUM_COUPONS_STATUS.getTipsByCode(couponsEntity.getStatus()));
            }//优惠券已过期
            else if(nowDate.compareTo(couponsEntity.getExpiryEndDate()) > 0){
                throw BusinessException.withErrorCode(ENUM_COUPONS_STATUS.getTipsByCode(ENUM_COUPONS_STATUS.EXPIRY.getCode()));
            }
            //是否优惠券批次数量超过设定的阀值
            else if(result.keySet().contains(consumeCouponsDTO.getCouponsSeq())
                    && result.get(consumeCouponsDTO.getCouponsSeq()).intValue() > couponsPropertyEntity.getSuperimpositionQuantity()){
                throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20003);
            }//达到使用条件
            else if((ENUM_COUPONS_STATUS.NO_USE.getCode() == couponsEntity.getStatus()
                && nowDate.compareTo(couponsEntity.getExpiryBeginDate()) >= 0
                && nowDate.compareTo(couponsEntity.getExpiryEndDate()) <= 0)
                || ENUM_COUPONS_STATUS.WAIT.getCode() == couponsEntity.getStatus()){

                CouponsDTO couponsDTO = new CouponsDTO();
                couponsDTO.setCouponsSeq(consumeCouponsDTO.getCouponsSeq());
                couponsDTO.setCouponsNo(consumeCouponsDTO.getCouponsNo());
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.OVER.getCode());
                updateCoupons(couponsDTO);
            }
        });
    }

    @Override
    public void discardCoupons(List<ConsumeCouponsDTO> consumeCouponsDTOS) {
        consumeCouponsDTOS.forEach(consumeCouponsDTO -> {
            CouponsEntity couponsEntity = couponsDao.getCoupons(consumeCouponsDTO.getCouponsSeq(), consumeCouponsDTO.getCouponsNo());
            if(couponsEntity != null &&
                (couponsEntity.getStatus() == ENUM_COUPONS_STATUS.WAIT.getCode() ||
                couponsEntity.getStatus() == ENUM_COUPONS_STATUS.NO_USE.getCode())){
                CouponsDTO couponsDTO = new CouponsDTO();
                BeanUtils.copyProperties(consumeCouponsDTO, couponsDTO);
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.DISCARD.getCode());
                updateCoupons(couponsDTO);
            }
            else{
                throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20005);
            }
        });
    }

    @Override
    public List<CouponsSeqTypeEntity> listCouponsSeqType(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return couponsDao.listCouponsSeqType(pageQueryModel.getQueryParams());
    }

    @Override
    public void addCouponsSeqType(List<CouponsSeqTypeDTO> couponsSeqTypeDTOS) {
        couponsSeqTypeDTOS.forEach(couponsSeqTypeDTO -> {
            if(couponsDao.addCouponsSeqType(couponsSeqTypeDTO) <= 0){
                throw BusinessException.withErrorCode("添加批次类型异常");
            }
        });
    }

    @Override
    public void delCouponsSeqType(List<CouponsSeqTypeDTO> couponsSeqTypeDTOS) {
        couponsSeqTypeDTOS.forEach(couponsSeqTypeDTO -> {
            if(couponsDao.delCouponsSeqType(couponsSeqTypeDTO) <= 0){
                throw BusinessException.withErrorCode("删除批次类型异常");
            }
        });
    }

    @Override
    public void resetCoupons(List<ConsumeCouponsDTO> consumeCouponsDTOS) {
        consumeCouponsDTOS.forEach(consumeCouponsDTO -> {
            CouponsEntity couponsEntity = couponsDao.getCoupons(consumeCouponsDTO.getCouponsSeq(), consumeCouponsDTO.getCouponsNo());
            if(couponsEntity != null &&
                    couponsEntity.getStatus() == ENUM_COUPONS_STATUS.OVER.getCode()){
                CouponsDTO couponsDTO = new CouponsDTO();
                BeanUtils.copyProperties(consumeCouponsDTO, couponsDTO);
                couponsDTO.setStatus(ENUM_COUPONS_STATUS.WAIT.getCode());
                updateCoupons(couponsDTO);
            }
            else{
                throw BusinessException.withErrorCode(ErrorCode.ERROR_CODE_20006);
            }
        });
    }

}
