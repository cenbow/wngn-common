package com.zzia.wngn.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzia.wngn.util.StringUtil;

@WebServlet(name = "actionServlet", urlPatterns = "/actions/*")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ActionServlet.class);

	protected static final String METHOD_POST = "post";
	protected static final String CONTENTTYPE_STREAM = "multipart/form-data";

	public ActionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// actions/menu.action!menu
		logger.debug("请求URI:{}", request.getRequestURI());
		String actionName = parseActionName(request.getRequestURI());
		String methodName = parseMethodName(request.getRequestURI());
		logger.debug("actionName:{}, methodName:{}", actionName, methodName);
		invoke(actionName, methodName, request, response);

	}

	@SuppressWarnings("rawtypes")
	private void invoke(String actionName, String methodName, HttpServletRequest request, HttpServletResponse response) {
		ActionClass action = ActionFactory.getAction(actionName);
		action.initAction(request, response);
		if (isMultipartPost(request)) {
			// 文件上次请求
			upload(request, action);
		} else {
			Enumeration pNames = request.getParameterNames();
			while (pNames.hasMoreElements()) {
				String name = (String) pNames.nextElement();
				if (action.containsAttribute(name)) {
					action.setAttribute(name, request.getParameter(name));
				}
			}
		}
		action.execute(methodName);
	}

	private void upload(HttpServletRequest request, ActionClass action) {
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}
			List<FileItem> list = upload.parseRequest(request);
			String filename = null;
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (action.containsAttribute(name)) {
						action.setAttribute(name, value);
					}
				} else {
					filename = item.getName();
					String name = item.getFieldName();
					if (!StringUtil.notBlank(filename)) {
						continue;
					}
					filename.replace("\\", "/");
					filename = filename.substring(filename.lastIndexOf("/") + 1);
					item.getInputStream();
					UploadFile fileUpload = new UploadFile();
					fileUpload.setFileName(filename);
					fileUpload.setName(name);
					fileUpload.setInputStream(item.getInputStream());
					action.getAction().setUploadFile(fileUpload);
					item.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isMultipartPost(HttpServletRequest request) {
		String contentType = request.getContentType();
		String method = request.getMethod();
		if (METHOD_POST.equalsIgnoreCase(method) && contentType.contains(CONTENTTYPE_STREAM)) {
			return true;
		}
		return false;

	}

	private String parseActionName(String url) {
		String action = StringUtil.substring(url, "/", true);
		action = StringUtil.substring(action, "!", false);
		String[] actions = action.split("\\.");
		return actions[0];
	}

	private String parseMethodName(String url) {
		String action = StringUtil.substring(url, "/", true);
		return StringUtil.substring(action, "!", true);
	}

}
