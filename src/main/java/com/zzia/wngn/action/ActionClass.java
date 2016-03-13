package com.zzia.wngn.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionClass {

	private String name;
	private AbstractAction actionTemplate;
	private AbstractAction action;
	private Method defaultMethod;

	private Map<String, Method> setMethods = new HashMap<String, Method>();
	private Map<String, Method> busMethods = new HashMap<String, Method>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbstractAction getAction() {
		try {
			if(this.action==null){
				this.action = (AbstractAction) this.actionTemplate.clone();
			}
			return this.action;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setActionTemplate(AbstractAction actionTemplate) {
		this.actionTemplate = actionTemplate;
	}

	public Method getDefaultMethod() {
		return defaultMethod;
	}

	public void setDefaultMethod(Method defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

	public Method getSetMethod(String methodName) {
		return this.setMethods.get(methodName);
	}

	public void setSetMethods(Method method) {
		String key = method.getName().substring("set".length());
		key = key.substring(0, 1).toLowerCase() + key.substring(1);
		this.setMethods.put(key, method);
	}

	public Set<String> getAttributes() {
		return this.setMethods.keySet();
	}

	public boolean containsAttribute(String attributeName) {
		return this.setMethods.containsKey(attributeName);
	}

	public Method getBusMethod(String methodName) {
		return this.busMethods.get(methodName);
	}

	public void setBusMethods(Method method) {
		this.busMethods.put(method.getName(), method);
	}

	public void setAttribute(String name, String value) {
		try {
			this.getSetMethod(name).invoke(this.action, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute(String name) {
		try {
			this.getBusMethod(name).invoke(this.action);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initAction(HttpServletRequest request,HttpServletResponse response) {
		this.getAction().initRequest(request, response);
	}

}
