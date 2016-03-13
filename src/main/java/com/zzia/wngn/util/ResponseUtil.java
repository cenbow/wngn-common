package com.zzia.wngn.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzia.wngn.domain.ResponseResult;
import com.zzia.wngn.domain.ResultResponse;
import com.zzia.wngn.domain.ReturnResponse;
import com.zzia.wngn.util.AliJsonUtil;

@SuppressWarnings("deprecation")
public class ResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 发送请求响应结果数据（旧版本使用（2016-3-10前））
     * 
     * @param response
     * @param returnResponse
     * @deprecated
     */
    public static void write(HttpServletResponse response, ReturnResponse returnResponse) {
        PrintWriter writer = null;
        try {
            String result = AliJsonUtil.bean2Json(returnResponse);
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            logger.error("发送响应数据失败！");
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /**
     * 发送请求响应结果数据
     * 
     * @param response
     * @param responseResult
     * @throws IOException
     */
    public static void write(HttpServletResponse response, ResponseResult responseResult) {
        PrintWriter writer = null;
        try {
            String result = AliJsonUtil.bean2Json(responseResult);
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            logger.error("发送响应数据失败！");
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /**
     * 发送请求响应结果数据，并打印结束日志（旧版本使用（2016-3-10前）
     * 
     * @param response
     * @param returnResponse
     * @param controllerName
     * @param controllerMethod
     * @deprecated
     * @throws IOException
     */
    public static void write(HttpServletResponse response, ReturnResponse returnResponse, String controllerName,
            String controllerMethod) throws IOException {
        String result = AliJsonUtil.bean2Json(returnResponse);
        PrintWriter writer;
        response.setCharacterEncoding("UTF-8");
        writer = response.getWriter();
        logger.debug(controllerName + " controller : " + controllerMethod + " , result = " + result);
        writer.write(result);
        writer.flush();
        writer.close();
    }

    /**
     * 发送请求响应结果数据，并打印结束日志（旧版本使用（2016-3-10前）
     * 
     * @param response
     * @param returnResponse
     * @param controllerClass
     * @param controllerMethod
     * @deprecated
     * @throws IOException
     */
    public static void write(HttpServletResponse response, ReturnResponse returnResponse, Class<?> controllerClass,
            String controllerMethod) throws IOException {
        String result = AliJsonUtil.bean2Json(returnResponse);
        PrintWriter writer;
        response.setCharacterEncoding("UTF-8");
        writer = response.getWriter();
        logger.debug(controllerClass.getName() + " controller : " + controllerMethod + " , result = " + result);
        writer.write(result);
        writer.flush();
        writer.close();
    }

    /**
     * jsonp形式返回json数据,解决ajax跨域请求
     * 
     * @param request
     * @param response
     * @param resultResponse
     * @param apiName
     * @deprecated
     */
    public static void write(HttpServletRequest request, HttpServletResponse response, ResultResponse resultResponse,
            String apiName) {
        try {
            String result = AliJsonUtil.bean2Json(resultResponse);
            logger.info("api-name : " + apiName + " , results : " + result);
            String callback = request.getParameter("callback");
            if (StringUtil.notBlank(callback)) {
                result = callback + "(" + result + ")";
            }
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getOutputStream().write(result.getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * jsonp形式返回json数据,解决ajax跨域请求
     * 
     * @param request
     * @param response
     * @param resultResponse
     * @param apiName
     */
    public static void write(HttpServletRequest request, HttpServletResponse response, String result, String apiName) {
        try {
            logger.info("api-name : " + apiName + " , results : " + result);
            String callback = request.getParameter("callback");
            if (StringUtil.notBlank(callback)) {
                result = callback + "(" + result + ")";
            }
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getOutputStream().write(result.getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送请求响应结果数据
     * 
     * @param response
     * @param result
     * @throws IOException
     */
    public static void write(HttpServletResponse response, String result) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            logger.error("发送响应数据失败！");
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
