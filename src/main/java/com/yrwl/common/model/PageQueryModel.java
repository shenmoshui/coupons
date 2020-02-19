package com.yrwl.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  shentao
 * @date    2019-12-24
 */
@Getter
@Setter
public class PageQueryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static String PAGENUM = "pageNum";
    private final static String PAGESIZE = "pageSize";

    /**
     * 第几页
     */
    private int pageNum = 1;

    /**
     * 每页记录数
     */
    private int pageSize = 10;

    /**
     * 是否查询总记录数
     */
    private boolean doCount = true;

    /**
     * 是否查询所有
     */
    private boolean queryAll = false;

    /**
     * 参数
     */
    private Map queryParams = new HashMap();

    public PageQueryModel(){}

    public PageQueryModel(int pageNum, int pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 一个静态构造方法
     * @param params
     * @return
     */
    public static PageQueryModel buildQueryModel(Map<String, Object> params) {
        PageQueryModel pageModel = new PageQueryModel();
        if(params.containsKey(PAGENUM)) {
            pageModel.setPageNum(Integer.valueOf(String.valueOf(params.get("pageNum"))));
        }
        if(params.containsKey(PAGESIZE)) {
            pageModel.setPageSize(Integer.valueOf(String.valueOf(params.get("pageSize"))));
        }
        pageModel.setQueryParams(params);

        return pageModel;
    }

    public String getQueryParam(String key) {
        Object value = queryParams.get(key);
        if(value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    public void addParameter(Object key, Object value) {
        this.queryParams.put(key, value);
    }
}
