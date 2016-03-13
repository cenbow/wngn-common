package com.zzia.wngn.util;

import org.apache.http.HttpRequest;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http接口调用，解析工具类
 * 
 * @author v_wanggang
 * @date 2016年1月14日 下午6:37:23
 */
public class HttpServiceUtil {

    /** 超时重试次数 */
    public static final int RETRY_COUNT = 5;
    /** 连接池的最大连接数 */
    public static final int MAX_TOTAL_CONNECTIONS = 100000;
    /** 路由的默认最大连接 */
    public static final int MAX_ROUTE_CONNECTIONS = 20000;
    /** 超时数 */
    public static final int TIME_OUT = 3000;
    /** 默认编码字符集 */
    public static final String CHARACTER_ENCODING = "UTF-8";

    private static CloseableHttpClient httpClient = null;

    private static PoolingHttpClientConnectionManager connManager = null;

    private static RequestConfig requestConfig = null;

    private static Logger logger = LoggerFactory.getLogger(HttpServiceUtil.class);

    static {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= RETRY_COUNT) {
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                if (exception instanceof SSLException) {
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    return true;
                }
                return false;
            }
        };
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT).setSocketTimeout(TIME_OUT)
                .setConnectTimeout(TIME_OUT).build();
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(myRetryHandler).build();
    }

    /**
     * 发送post请求，参数为json字符串
     *
     * @param url
     * @param str
     * @return
     * @throws IOException
     */
    public static String sendPost(String url, String str) {
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        StringEntity se = new StringEntity(str, ContentType.create("text/plain", "UTF-8"));
        try {
            logger.info("request-url : " + url + " json : " + str);
            post.setEntity(se);
            response = httpClient.execute(post);
            logger.info("response :" + response);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = handlerResponse(response);
            } else {
                logger.info("exception of call service . " + status);
            }
            logger.info("result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (post != null) {
                post.releaseConnection();
            }
        }
        return result;
    }

    /**
     * 发送post请求，参数为json字符串，设置请求头信息
     *
     * @param url
     * @param str
     * @param headers
     * @return
     * @throws IOException
     */
    public static String sendPost(String url, String str, HashMap<String, String> headers) {
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        for (String key : headers.keySet()) {
            post.setHeader(key, headers.get(key));
        }
        StringEntity se = new StringEntity(str, ContentType.create("text/plain", "UTF-8"));
        try {
            logger.info("request-url : " + url + " json : " + str);
            post.setEntity(se);
            response = httpClient.execute(post);
            logger.info("response :" + response);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = handlerResponse(response);
            } else {
                logger.info("exception of call service . " + status);
            }
            logger.info("result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (post != null) {
                post.releaseConnection();
            }
        }
        return result;
    }

    /**
     * 发送post请求，参数为json字符串，设置多项请求头信息
     *
     * @param url
     * @param str
     * @param postHeaders
     * @return
     * @throws IOException
     */
    public static String sendPost(String url, String str, List<Map<String, String>> postHeaders) {
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        for (Map<String, String> postHeader : postHeaders) {
            post.addHeader(postHeader.get("name"), postHeader.get("value"));
        }
        StringEntity se = new StringEntity(str, ContentType.create("text/plain", "UTF-8"));
        try {
            logger.info("request-url : " + url + " json : " + str);
            post.setEntity(se);
            response = httpClient.execute(post);
            logger.info("response : " + response);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = handlerResponse(response);
            } else {
                logger.info("exception of call service . " + status);
            }
            logger.info("result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (post != null) {
                post.releaseConnection();
            }
        }
        return result;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param idType
     * @param idValues
     * @return
     */
    public static String sendPost(String url, String idType, String[] idValues) {
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("idType", idType));
        for (String idValue : idValues) {
            valuePairs.add(new BasicNameValuePair("idValues[]", idValue));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        try {
            logger.info("request-url : " + url + " json : " + valuePairs);
            post.setEntity(entity);
            response = httpClient.execute(post);
            logger.info("response : " + response);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = handlerResponse(response);
            } else {
                logger.info("exception of call service . " + status);
            }
            logger.info("result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (post != null) {
                post.releaseConnection();
            }
        }
        return result;
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendGet(String url) {
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        String result = null;
        try {
            logger.info("request-url : " + url);
            response = httpClient.execute(httpGet);
            logger.info("response : " + response);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = handlerResponse(response);
            } else {
                logger.info("exception of call service . " + status);
            }
            logger.info("result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return result;
    }

    /**
     * 发送get请求，设置头部信息
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendGet(String url, HashMap<String, String> headers) {
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        for (String key : headers.keySet()) {
            httpGet.setHeader(key, headers.get(key));
        }
        String result = null;
        try {
            logger.info("request-url : " + url);
            response = httpClient.execute(httpGet);
            logger.info("response : " + response);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = handlerResponse(response);
            } else {
                logger.info("exception of call service . " + status);
            }
            logger.info("results : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return result;
    }

    /**
     * 处理返回的请求
     *
     * @param response
     * @return
     * @throws UnsupportedOperationException
     * @throws IOException
     */
    private static String handlerResponse(HttpResponse response) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                is = responseEntity.getContent();
                isr = new InputStreamReader(is, CHARACTER_ENCODING);
                br = new BufferedReader(isr);
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return sb.toString();
    }

}
