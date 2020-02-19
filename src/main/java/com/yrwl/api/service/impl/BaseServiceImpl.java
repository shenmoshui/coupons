package com.yrwl.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.yrwl.common.model.PageQueryModel;

/**
 * @author  shentao
 * @date    2019-12-24
 */
public class BaseServiceImpl {

    /**
     * 分页
     * @param pageQueryModel
     */
    protected void getList(PageQueryModel pageQueryModel){
        if(pageQueryModel == null){
            pageQueryModel = new PageQueryModel();
        }
        if(pageQueryModel.isQueryAll()){
            pageQueryModel.setPageSize(0);
        }
        PageHelper.startPage(pageQueryModel.getPageNum(), pageQueryModel.getPageSize(), pageQueryModel.isDoCount());
    }
}
