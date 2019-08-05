package com.liupeng.sell.utils;

import com.liupeng.sell.enums.CodeEnum;

/**
 * @author liufeifei
 */
public class EnumUtil {

    /**
     *  使用泛型限制该类的实参类型，
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
