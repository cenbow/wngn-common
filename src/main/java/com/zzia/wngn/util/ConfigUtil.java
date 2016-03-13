package com.zzia.wngn.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 配置文件工具类:config.properties
 * <p>
 * 存在：configuration/config.properties 覆盖 classes/config.properties
 * 
 * @author v_wanggang
 * @date 2016年1月18日 上午11:05:32
 */
public class ConfigUtil {

    private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    /** 存储属性键值对 */
    private static Map<String, String> CONFIG_MAPS = null;

    private static final String CONFIG_SUFFIX = ".properties";
    private static final String CONFIG_PATH = "/";

    /**
     * 加载配置文件
     * 
     * @param configFileName
     */
    private static void loadConfigure(String configFileName) {
        CONFIG_MAPS = new HashMap<String, String>();
        Map<String, String> configureMap = getConfigureMap(configFileName);
        if (configureMap != null) {
            CONFIG_MAPS.putAll(configureMap);
        }
    }

    /**
     * 读取配置文件，jar外的覆盖jar内的配置文件
     * 
     * @param configFileName
     * @return
     */
    private static Map<String, String> getConfigureMap(String configFileName) {
        Map<String, String> propertyConfig = getJarInnerConfigureMap(configFileName);
        if (propertyConfig != null) {
            CONFIG_MAPS.putAll(propertyConfig);
        }
        propertyConfig = getJarOuterConfigureMap(configFileName);
        if (propertyConfig != null) {
            CONFIG_MAPS.putAll(propertyConfig);
        }
        return CONFIG_MAPS;
    }

    /**
     * 从jar包内读取配置文件classes/config.properties
     * 
     * @param configFileName
     * @return
     */
    private static Map<String, String> getJarInnerConfigureMap(String configFileName) {
        InputStream configInputStream = null;
        try {
            configInputStream = ConfigUtil.class.getResourceAsStream(CONFIG_PATH + configFileName + CONFIG_SUFFIX);
            if (configInputStream != null) {
                Map<String, String> propertyConfig = parserPropertyConfig(configInputStream);
                return propertyConfig;
            }
            logger.warn("there is no configuration files [config.properties] in jar package.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (configInputStream != null) {
                try {
                    configInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 从jar外读取配置文件：configuration/config.properties
     * 
     * @param configFileName
     * @return
     */
    private static Map<String, String> getJarOuterConfigureMap(String configFileName) {
        InputStream configInputStream = null;
        try {
            String path = ConfigUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            if (path.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"
                // 截取路径中的jar包名
                path = path.substring(0, path.lastIndexOf("/"));
                path = path.substring(0, path.lastIndexOf("/"));
                logger.info("jar_path=" + path);
                if (path.endsWith("/configuration")) {
                    path = path + CONFIG_PATH + configFileName + CONFIG_SUFFIX;
                    configInputStream = new FileInputStream(path);
                    if (configInputStream != null) {
                        Map<String, String> propertyConfig = parserPropertyConfig(configInputStream);
                        return propertyConfig;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (configInputStream != null) {
                try {
                    configInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.warn("there is no configuration files [config.properties] in configuration directory.");
        return null;
    }

    private static Map<String, String> parserPropertyConfig(InputStream is) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        try {
            Properties properties = new Properties();
            properties.load(is);
            Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Object, Object> entry = it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                propertyMap.put(key.toString(), value.toString());
            }
            return propertyMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void loadProperyConfigure(String filename) {
        loadConfigure(filename);
    }

    /**
     * 获得配置项值
     * 
     * @param configName
     *            配置名称
     * @return 配置项值
     */
    private static String getConfigValue(String configName) {
        if (CONFIG_MAPS == null) {
            loadProperyConfigure("config");
        }
        return CONFIG_MAPS.get(configName);
    }

    public static String getStringValue(String configName) {
        return getConfigValue(configName);
    }

    public static Integer getIntegerValue(String configName) {
        try {
            return Integer.parseInt(getConfigValue(configName));
        } catch (NumberFormatException e) {
            throw new RuntimeException("获取配置属性[" + configName + "]异常", e);
        }
    }

    public static Boolean getBooleanValue(String configName) {
        String value = getConfigValue(configName);
        if (value != null && value.length() > 1) {
            if ("true".equals(value.toLowerCase()) || "yes".equals(value.toLowerCase()) || "1".equals(value)) {
                return true;
            }
            if ("false".equals(value.toLowerCase()) || "no".equals(value.toLowerCase()) || "0".equals(value)) {
                return false;
            }
            logger.warn("property of " + configName
                    + " value is not legal, the correct value should be true, false, yes , no,1.or 0");
            return Boolean.FALSE;
        }
        return null;
    }

    public static String getValue(String configName) {
        return getConfigValue(configName);
    }

}
