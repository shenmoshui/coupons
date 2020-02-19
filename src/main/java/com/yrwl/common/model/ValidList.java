package com.yrwl.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shentao
 * @date 2020-02-14
 */
@Getter
@Setter
public class ValidList<E> implements List<E> {

    @Delegate
    @Valid
    private List<E> list;

    public ValidList() {
        this.list = new ArrayList<>();
    }

    public ValidList(List<E> list) {
        this.list = list;
    }
}
