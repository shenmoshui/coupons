package com.yrwl.api.service.impl;

import com.yrwl.api.dao.CouponsDao;
import com.yrwl.api.entity.CouponsEntity;
import com.yrwl.api.entity.CouponsPropertyEntity;
import com.yrwl.api.entity.CouponsQuantityEntity;
import com.yrwl.api.entity.StatisticsEntity;
import com.yrwl.api.service.StatistiscService;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.constants.ENUM_COUPONS_STATUS;
import com.yrwl.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shentao
 * @date 2020-02-16
 */
@Service
@Transactional(rollbackFor = {BusinessException.class, Exception.class})
public class StatisticsServiceImpl implements StatistiscService {

    @Autowired
    private CouponsDao couponsDao;

    @Override
    public StatisticsEntity listStatistics(String appId) {

        StatisticsEntity statisticsEntity = new StatisticsEntity();
        PageQueryModel pageQueryModel = new PageQueryModel(1, 10000);
        pageQueryModel.addParameter("appId", appId);
        List<CouponsPropertyEntity> list = couponsDao.listCouponsProperty(pageQueryModel.getQueryParams());
        if(CollectionUtils.isNotEmpty(list)){
            List<CouponsQuantityEntity> couponsQuantityEntities = new ArrayList<>();
            //发放总数
            int allSendNum = 0;
            //领取总数
            int allReceiveNum = 0;
            //使用总数
            int allConsumeNum = 0;
            for(CouponsPropertyEntity couponsPropertyEntity : list){
                PageQueryModel temp = new PageQueryModel(1, 10000);
                temp.addParameter("appId", appId);
                //所有领取的优惠券
                temp.addParameter("couponsSeq", couponsPropertyEntity.getCouponsSeq());
                List<CouponsEntity> allCouponsEntities = couponsDao.listCoupons(temp.getQueryParams());
                //所有使用的优惠券
                temp.addParameter("status", ENUM_COUPONS_STATUS.OVER.getCode());
                List<CouponsEntity> couponsEntities = couponsDao.listCoupons(temp.getQueryParams());

                allSendNum += couponsPropertyEntity.getNum();
                allReceiveNum += allCouponsEntities.size();
                allConsumeNum += couponsEntities.size();

                //优惠券数量统计
                CouponsQuantityEntity couponsQuantityEntity = new CouponsQuantityEntity();
                couponsQuantityEntity.setReceiveNum(allCouponsEntities.size()+"");
                couponsQuantityEntity.setConsumeNum(couponsEntities.size()+"");
                couponsQuantityEntity.setSendNum(couponsPropertyEntity.getNum()+"");
                couponsQuantityEntity.setCouponsName(couponsPropertyEntity.getName());
                couponsQuantityEntity.setCouponsSeq(couponsPropertyEntity.getCouponsSeq());

                couponsQuantityEntities.add(couponsQuantityEntity);
            }

            statisticsEntity.setList(couponsQuantityEntities);
            statisticsEntity.setAllConsumeNum(allConsumeNum+"");
            statisticsEntity.setAllCouponsSeqNum(list.size()+"");
            statisticsEntity.setAllReceiveNum(allReceiveNum+"");
            statisticsEntity.setAllSendNum(allSendNum+"");
        }

        return statisticsEntity;
    }
}
