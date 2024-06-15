package com.nhnacademy.minidooraydgateway.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    public static String createSortParam(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            return order.getProperty() + "," + order.getDirection().name().toLowerCase();
        }
        return null;
    }
}
