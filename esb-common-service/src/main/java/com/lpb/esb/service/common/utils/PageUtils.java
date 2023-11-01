package com.lpb.esb.service.common.utils;

import com.lpb.esb.service.common.model.response.ListModel;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudv1 on 2021-11-16
 */
public class PageUtils {
    @SneakyThrows
    public static <T, V> ListModel<V> initPageModel(Page<T> page, int pageNumber, int pageSize, Class<V> cls) {
        List<T> list = page.getContent();
        List<V> listRes = copyBeans(list, cls);
        ListModel<V> listModel = new ListModel(listRes);
        listModel.setRecordStart((pageNumber - 1) * pageSize + 1);
        listModel.setRecordEnd(listModel.getRecordStart() + list.size() - 1);
        listModel.setTotalPages(page.getTotalPages());
        listModel.setTotalRecords(page.getTotalElements());

        return listModel;
    }

    @SneakyThrows
    public static <T, V> List<V> copyBeans(List<T> ori, Class<V> cls) {
        List<V> listRes = new ArrayList<>();
        for (T t : ori) {
            V v = cls.newInstance();
            BeanUtils.copyProperties(t, v);
            listRes.add(v);
        }
        return listRes;
    }
}
