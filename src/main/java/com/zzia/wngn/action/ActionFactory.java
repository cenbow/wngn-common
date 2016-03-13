package com.zzia.wngn.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzia.wngn.util.ClassUtils;
import com.zzia.wngn.util.ConfigUtil;
import com.zzia.wngn.util.StringUtil;

public class ActionFactory {

	private static final Logger logger = LoggerFactory.getLogger(ActionFactory.class);
	
	private static Map<String,ActionClass> actionMap = null;
	
	private static void initAction(){
		actionMap = new HashMap<String,ActionClass>();
		String packageName = ConfigUtil.getStringValue("action.package");
		if(packageName==null){
			packageName = "";//根路径
		}
		List<String> classNames = ClassUtils.getClassName(packageName, true);
		if (classNames != null) {
			for (String className : classNames) {
				try {
					Class<?> cls = Class.forName(className);
					Action annotation = cls.getAnnotation(Action.class);
					if(annotation==null||!className.endsWith("Action")||!AbstractAction.class.isAssignableFrom(cls)){
						continue;
					}
					logger.debug("解析Action[{}]",className);
					String actionName = className.substring(className.lastIndexOf(".")+1);
					actionName = StringUtil.substringSuffix(actionName, "Action");
					actionName = StringUtil.toFirstLowerCase(actionName);
					ActionClass actionClass = new ActionClass();
					AbstractAction action = (AbstractAction) cls.newInstance();
					actionClass.setName(actionName);
					actionClass.setActionTemplate(action);
					Method[] methods =  cls.getMethods();
					for (Method method : methods) {
						if(method.getName().startsWith("set")){
							actionClass.setSetMethods(method);
						}else{
							actionClass.setBusMethods(method);
						}
					}
					actionMap.put(actionClass.getName(), actionClass);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ActionClass getAction(String actionName){
		if(actionMap==null){
			initAction();
		}
		return (ActionClass) actionMap.get(actionName);
	}
	
}
