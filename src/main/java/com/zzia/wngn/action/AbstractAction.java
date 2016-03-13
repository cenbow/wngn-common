package com.zzia.wngn.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzia.wngn.util.StringUtil;

public abstract class AbstractAction implements Serializable {

	private static final long serialVersionUID = -114964899191243702L;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, UploadFile> fileMap = new HashMap<String, UploadFile>();

	public UploadFile getUploadFile(String name) {
		return fileMap.get(name);
	}

	public void setUploadFile(UploadFile upload) {
		this.fileMap.put(upload.getName(), upload);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void initRequest(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void redirect(String url) {
		try {
			request("action", this);
			this.response.sendRedirect(parseUrl(url, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void forward(String url) {
		try {
			request("action", this);
			this.request.getRequestDispatcher(parseUrl(url, false)).forward(this.request, this.response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void output(String result) {
		try {
			this.response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stream(InputStream inputStream, Object... objects) {
		try {
			response.reset();
			response.setContentType("application/x-msdownload");
			if(objects!=null&&objects.length>0){
				response.addHeader("Content-Disposition", "attachment; filename=\"" + objects[0].toString() + "\"");
				if(objects.length>1){
					response.setContentLength((Integer)objects[1]);
				}
			}else{
				response.addHeader("Content-Disposition", "attachment; filename=\"" + System.currentTimeMillis() + "\"");
			}
			byte[] buf = new byte[4096];
			ServletOutputStream servletOS = response.getOutputStream();
			int readLength;
			while (((readLength = inputStream.read(buf)) != -1)) {
				servletOS.write(buf, 0, readLength);
			}
			inputStream.close();
			servletOS.flush();
			servletOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void request(String name, Object value) {
		this.request.setAttribute(name, value);
	}

	public void session(String name, Object value) {
		this.request.getSession().setAttribute(name, value);
	}

	private String parseUrl(String url, boolean redirect) {
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		if (url.startsWith("actions")) {
			String base = StringUtil.substring(url, "actions/", true);
			return base;
		} else {
			if (redirect) {
				String base = this.request.getRequestURI().substring(1);
				base = StringUtil.substring(base, "/", false);
				base = "/" + base + "/" + url;
				return base;
			}
			return "/" + url;
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			// 将流序列化成对象
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (oos != null) {
					oos.close();
				}
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return super.clone();
	}
}
