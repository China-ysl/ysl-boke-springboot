package org.lingge.utils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * bean拷贝工具类
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {
    }
    // 使用泛型<V>V
    //单个对象拷贝方法copyBean
    public static  <V>V copyBean(Object s , Class<V>  clazz) {

        V v = null;
        try {
            //通过反射创建目标对象
            v = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(s, v);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static <V,T> List<T> copyList(List<V> List ,Class<T> clazz){
        return List.stream()
                .map(O -> copyBean(O, clazz))
                .collect(Collectors.toList());
    }
}
