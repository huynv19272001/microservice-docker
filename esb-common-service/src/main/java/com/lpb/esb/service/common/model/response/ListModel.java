package com.lpb.esb.service.common.model.response;

import lombok.*;

import java.util.Collection;

/**
 * Created by tudv1 on 2021-07-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ListModel<T> {
    long size;
    long totalRecords;
    long totalPages;
    long recordStart;
    long recordEnd;
    Collection<T> list;

    public ListModel(Collection<T> list) {
        this.list = list;
        this.size = list.size();
    }
}
