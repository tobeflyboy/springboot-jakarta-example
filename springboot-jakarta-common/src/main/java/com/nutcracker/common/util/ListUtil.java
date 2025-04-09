package com.nutcracker.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List util
 *
 * @author 胡桃夹子
 * @date 2020-05-14 12:38
 */
public class ListUtil {
    /**
     * 1> 按照份数---划分list
     *
     * @param source 集合数据
     * @param num    想要划分成多少份
     * @return 集合数据
     */
    public static <T> List<List<T>> splitListForNum(List<T> source, int num) {
        List<List<T>> result = new ArrayList<>();
        //(先计算出余数)
        int remaider = source.size() % num;
        //然后是商
        int number = source.size() / num;
        //偏移量
        int offset = 0;
        for (int i = 0; i < num; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 2> 根据目标容量 划分List
     *
     * @param source   集合数据
     * @param capacity 划分完成的单个List容量
     * @param <T>      泛型
     * @return 集合数据
     */
    public static <T> List<List<T>> splitListByCapacity(List<T> source, int capacity) {
        List<List<T>> result = new ArrayList<>();
        if (source != null) {
            int size = source.size();
            if (size > 0) {
                for (int i = 0; i < size; ) {
                    List<T> value = null;
                    int end = i + capacity;
                    if (end > size) {
                        end = size;
                    }
                    value = source.subList(i, end);
                    i = end;

                    result.add(value);
                }

            } else {
                result = null;
            }
        } else {
            result = null;
        }


        return result;
    }

}
