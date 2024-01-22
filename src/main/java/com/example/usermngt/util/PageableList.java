package com.example.usermngt.util;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
public class PageableList<T> {
    private long total;
    private List<T> list;

    public static<T> Map<String, Object> map(Long total, List<T> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);

        return map;
    }
}
