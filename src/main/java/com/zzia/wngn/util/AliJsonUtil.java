package com.zzia.wngn.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * json转换工具
 * @author heshibo
 *
 */
public class AliJsonUtil {

    private static final String SPLIT_PATTERN = ".";
    private static final String ARRAY_PATTERN = "[]";

    /**
     * 实现将bean转换为JSON字符串
     *
     * @param bean
     * @return
     */
    public static String toJsonString(Object bean) {
        return toJsonString(bean, null);
    }

    /**
     * 实现将bean转换为JSON字符串
     * SerializerFeature 具体定义见 http://code.alibabatech.com/wiki/display/FastJSON/Serial+Features
     *
     * @param bean
     * @return
     * @throws com.alibaba.fastjson.JSONException
     *
     */
    public static String toJsonString(Object bean, SerializerFeature[] features) {
        if (null == bean) {
            throw new IllegalArgumentException("target bean is null!");
        }
        try {
            if (null == features)
                return JSON.toJSONString(bean);
            else
                return JSON.toJSONString(bean, features);
        } catch (Exception ex) {
            throw new JSONException("Met error when converting bean" +
                    " to json!Error:" + ex.getMessage());
        }
    }

    /**
     * 将json字符串转化为JSON
     * 注意：
     * 转换时仅对bean与JSON中对应的key进行赋值，其他无法映射的值均为空
     *
     * @param reqmsg
     * @param targertClass
     * @return
     * @throws com.alibaba.fastjson.JSONException
     *
     */
    public static <T> T toObject(Object reqmsg, Class<T> targertClass) {
        if (null == reqmsg || reqmsg.toString().length() == 0) {
            throw new IllegalArgumentException("input json string is blank!");
        }
        try {
            return JSON.parseObject(reqmsg.toString(), targertClass);
        } catch (Exception ex) {
            throw new JSONException("Met error when converting json string" +
                    " to bean!Error:" + ex.getMessage());
        }
    }

    /**
     * 将json字符串转化为JSON
     * 注意：
     * 转换时仅对bean与JSON中对应的key进行赋值，其他无法映射的值均为空
     *
     * @param json
     * @param targertClass
     * @param features
     * @return
     * @throws com.alibaba.fastjson.JSONException
     *
     */
    public static <T> T toObject(String json, Class<T> targertClass, Feature... features) {
        if (null == json || json.length() == 0) {
            throw new IllegalArgumentException("input json string is blank!");
        }
        try {
            return JSON.parseObject(json, targertClass, features);
        } catch (Exception ex) {
            throw new JSONException("Met error when converting json string" +
                    " to bean!Error:" + ex.getMessage());
        }
    }

    /**
     * 将json转换为JSONObject
     *
     * @param json
     * @return
     * @throws com.alibaba.fastjson.JSONException
     *
     */
    public static JSONObject toJsonObject(String json) {
        if (null == json || json.length() == 0) {
            throw new IllegalArgumentException("input json string is blank!");
        }
        try {
            return JSON.parseObject(json);
        } catch (Exception ex) {
            throw new JSONException("Met error when converting json string" +
                    " to bean!Error:" + ex.getMessage());
        }
    }

    /**
     * 将json转换为JSONObject
     *
     * @param json
     * @return
     * @throws com.alibaba.fastjson.JSONException
     *
     */
    public static JSONArray toJsonArray(String json) {
        if (null == json || json.length() == 0) {
            throw new IllegalArgumentException("input json string is blank!");
        }
        try {
            return JSON.parseArray(json);
        } catch (Exception ex) {
            throw new JSONException("Met error when converting json string" +
                    " to bean!Error:" + ex.getMessage());
        }
    }

    public static <T> List<T> toList(String json, Class<T> targertClass) {
        if (null == json || json.length() == 0) {
            throw new IllegalArgumentException("input json string is blank!");
        }
        try {
            return JSONArray.parseArray(json, targertClass);
        } catch (Exception ex) {
            throw new JSONException("Met error when converting json string" +
                    " to bean!Error:" + ex.getMessage());
        }
    }

    /**
     * 从json字符串里获取属性值，多层嵌套的属性用"."分隔
     *
     * @param json         JSON字符串
     * @param propertyPath 属性串，格式：A.B[].C，相当于json.get("A").get("B")[0].get("C"),<br/>
     *                     后缀[]表示为列表，只支持取第一个值
     * @return 包含最后属性值的对象
     */
    public static Object getProperty(String json, String propertyPath) {
        StringUtil.notEmpty(json, "json");
        StringUtil.notEmpty(propertyPath, "propertyPath");
        try {
            JSONObject jsonObject = toJsonObject(json);

            if (propertyPath.contains(SPLIT_PATTERN)) {
                return getPropertyFromElement(jsonObject, propertyPath);
            }
            if (propertyPath.endsWith(ARRAY_PATTERN)) {
                getJsonFromArray(jsonObject, propertyPath);
            }

            return jsonObject.get(propertyPath);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error when try to get specified property:" + e.getMessage(), e);
        }
    }

    private static Object getPropertyFromElement(JSONObject jsonObject, String propertyPath) {
        int indexOfSeparator = propertyPath.indexOf(SPLIT_PATTERN);
        String subPropertyKey = propertyPath.substring(0, indexOfSeparator);
        String subPropertyPath = propertyPath.substring(indexOfSeparator + 1);

        if (subPropertyKey.endsWith(ARRAY_PATTERN)) {
            return getProperty(getJsonFromArray(jsonObject, subPropertyKey), subPropertyPath);
        }
        return getProperty(jsonObject.getString(subPropertyKey), subPropertyPath);
    }

    private static String getJsonFromArray(JSONObject jsonObject, String subPropertyKey) {
        subPropertyKey = subPropertyKey.substring(0, subPropertyKey.length() - ARRAY_PATTERN.length());
        return toJsonString(toJsonArray(jsonObject.getString(subPropertyKey)).get(0));
    }
    
    /**
     * 实现将bean转换为JSON字符串
     * @param bean
     * @return
     */
    public static String bean2Json(Object bean){
        return bean2Json(bean, null);
    }

    /**
     * 实现将bean转换为JSON字符串
     *  SerializerFeature 具体定义见 http://code.alibabatech.com/wiki/display/FastJSON/Serial+Features
     * @param bean
     * @return
     * @throws   com.alibaba.fastjson.JSONException
     */
    public static String bean2Json(Object bean, SerializerFeature[] features ){
        if(null == bean){
            throw new IllegalArgumentException("target bean is null!");
        }
        try {
            String json = null;
            if(null == features)
               json = JSON.toJSONString(bean);
            else
               json = JSON.toJSONString(bean,features);
            return json;
        } catch (Exception ex){
            throw new JSONException("Met error in converting bean" +
                    " to json!Error:"+ex.getMessage());
        }
    }

    /**
     * 将json字符串转化为JSON
     * 注意：
     *     转换时仅对bean与JSON中对应的key进行赋值，其他无法映射的值均为空
     * @param json
     * @param targertClass
     * @return
     */
    public static <T>T json2Object(String json, Class<T> targertClass) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        T bean = null;
        try {
        	bean = (T) JSON.parseObject(json, targertClass);
        } catch (Exception e){
        	throw new JSONException("Met error in converting json string " +
        			"to bean!Error:" + e.getMessage());
        }
        return bean;
    }

    /**
     * 将json字符串转化为JSON
     * 注意：
     *     转换时仅对bean与JSON中对应的key进行赋值，其他无法映射的值均为空
     * @param json
     * @param targertClass
     * @param features
     * @return
     * @throws   com.alibaba.fastjson.JSONException
     */
    public static Object json2Object(String json,Class<?> targertClass,Feature... features){
        if(null == json || json.length() == 0){
            throw new IllegalArgumentException("input json string is blank!");
        }
        try{
            Object bean = JSON.parseObject(json, targertClass,features);
            return bean;
        }catch (Exception ex){
            throw new JSONException("Met error in converting json string" +
                    " to bean!Error:"+ex.getMessage());
        }
    }


}
