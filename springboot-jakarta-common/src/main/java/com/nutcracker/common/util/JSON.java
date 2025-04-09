package com.nutcracker.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nutcracker.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * jack json util
 *
 * @author 胡桃夹子
 * @since 2023/12/28 14:02
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Slf4j
public class JSON {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JSON() {

    }

    static {
        // 配置 Jackson，忽略未知属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 注册 JavaTimeModule 来处理 Java 8 的时间类型
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * 将 Java 对象序列化为 JSON 字符串
     *
     * @param object 要序列化的对象
     * @return JSON 字符串
     */
    public static String toJSONString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJsonString fail", e);
            throw new BusinessException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为 Java 对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标对象的类
     * @param <T>   目标对象的类型
     * @return 反序列化得到的 Java 对象
     */
    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("parse fail", e);
            throw new BusinessException(e.getCause());
        }
    }

    /**
     * 将 JSON 字符串反序列化为 List 对象
     *
     * @param json  JSON 字符串
     * @param clazz List 中元素的类
     * @param <T>   List 中元素的类型
     * @return 反序列化得到的 List 对象
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        CollectionType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return OBJECT_MAPPER.readValue(json, listType);
        } catch (JsonProcessingException e) {
            log.error("parseList fail", e);
            throw new BusinessException(e);
        }
    }
}
