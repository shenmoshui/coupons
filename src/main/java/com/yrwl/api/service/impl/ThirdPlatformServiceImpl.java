package com.yrwl.api.service.impl;

import com.yrwl.api.dao.ThirdPlatformDao;
import com.yrwl.api.dto.ThirdPlatformDTO;
import com.yrwl.api.entity.ThirdPlatformEntity;
import com.yrwl.api.service.ThirdPlatformService;
import com.yrwl.common.model.PageQueryModel;
import com.yrwl.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shentao
 * @date 2020-02-11
 */
@Service
@Transactional(rollbackFor = {BusinessException.class, Exception.class})
public class ThirdPlatformServiceImpl extends BaseServiceImpl implements ThirdPlatformService {

    @Autowired
    private ThirdPlatformDao thirdPlatformDao;

    @Override
    public List<ThirdPlatformEntity> listThirdPlatform(PageQueryModel pageQueryModel) {
        super.getList(pageQueryModel);

        return thirdPlatformDao.listThirdPlatform(pageQueryModel.getQueryParams());
    }

    @Override
    public ThirdPlatformEntity getThirdPlatform(String appId) {

        return thirdPlatformDao.getThirdPlatform(appId);
    }

    @Override
    public void addThirdPlatform(ThirdPlatformDTO thirdPlatformDTO) {
        thirdPlatformDao.addThirdPlatform(thirdPlatformDTO);
    }

    @Override
    public void updateThirdPlatform(ThirdPlatformDTO thirdPlatformDTO) {
        thirdPlatformDao.updateThirdPlatform(thirdPlatformDTO);
    }

    @Override
    public boolean checkSecret(String appId, String appSecret) {

        if(thirdPlatformDao.checkSecret(appId, appSecret) > 0){

            return true;
        }

        return false;
    }

}
