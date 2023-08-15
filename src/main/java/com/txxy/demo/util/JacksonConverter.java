package com.txxy.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

public class JacksonConverter {
    private static final Logger logger = LoggerFactory.getLogger(JacksonConverter.class);

    public final static ObjectMapper MAPPER = new ObjectMapper();

    public final static Charset DEFAULT_CHARSET = Charset.forName("utf-8");

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //序列化时只序列化非null属性
        MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * 将JSON字符串转换为简单的Java对象（非泛型）
     *
     * @param json
     * @param clazz
     * @return
     * @throws
     */
    public static <T> T read(String json, Class<T> clazz) {

        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将JSON字符串转换为简单的Java对象
     */
    public static <T> T read(String json, JavaType javaType) {

        try {
            return MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将JSON字符串转换为带泛型的Java对象
     * JacksonConverter.readGeneric(write, new TypeReference<ApiResponseDTO<RiskInfoDTO>>() {})
     *
     * @param json
     * @param type
     * @return
     * @throws
     */
    public static <T> T readGeneric(String json, TypeReference<T> type) {

        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将Java对象转换成JSON字符串
     *
     * @param obj
     * @return
     * @throws
     */
    public static String write(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将Java对象转换成JSON字符串(美化)
     *
     * @param obj
     * @return
     * @throws
     */
    public static String prettyWrite(Object obj) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将Java对象转换成JSON字符串,exclude fields
     *
     * @param obj
     * @return
     * @throws
     */
    public static String writeExceptFields(Object obj, String[] fieldNames) {

        try {
            FilterProvider provider = new SimpleFilterProvider()
                    .addFilter("exclude", SimpleBeanPropertyFilter.serializeAllExcept(fieldNames));
            return MAPPER.writer(provider).writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将JSON字符串转换为带泛型的List对象，这个和方法3一样，考虑到这种场景应该会比较多，所以单独提了一个方法。
     *
     * @param json
     * @param clazz
     * @return
     * @throws
     */
    public static <T> List<T> readList(String json, Class<T> clazz) {

        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static JsonNode readTree(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T merge(T t, String json) {
        try {
            return StringUtils.isBlank(json) ?
                    t : MAPPER.readerForUpdating(t).readValue(json);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
