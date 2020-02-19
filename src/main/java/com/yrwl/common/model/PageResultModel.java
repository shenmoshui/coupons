package com.yrwl.common.model;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author  shentao
 * @date    2019-12-24
 */
@Getter
@Setter
@ApiModel(value = "PageResultModel", description = "分页返回实体类")
public class PageResultModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private long total;
    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集")
    private List<T> list;
    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页")
    private int pageNum;
    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数")
    private int pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private int pages;
    /**
     * 当前页的数量 <= pageSize，该属性来自ArrayList的size属性
     */
    @ApiModelProperty(value = "当前页的数据数量")
    private int size;

    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     * @param list          page结果
     */
    public PageResultModel(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
        } else {
            this.list = list;
        }
    }
}
