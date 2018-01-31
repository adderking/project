package com.scisdata.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.scisdata.web.util.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 控制器支持类
 * @author fangshilei
 * @version 2015-3-23
 */
@SuppressWarnings("all")
public abstract class BaseController {
	
	public static final String REDIRECT="redirect:";
	protected HttpServletRequest request ;
	protected HttpServletResponse response;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
		JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	}
	/**
	 * 利用指定的Response对象向网页输出内容
	 * @param value
	 */

	public void outputResponse(String value){
		try {
			value=JSON.toJSONString(value,SerializerFeature.WriteDateUseDateFormat);
			response.setContentType("text/json;charset=utf-8");
			PrintWriter out= response.getWriter();
			out.write(value);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向页面输出内容
	 * @param encoding  编码
	 * @param contentType 格式
	 * @param value 内容
	 * @throws SystemException
	 */

	public void outputResponse(String encoding,String contentType,String value){

		try {
			//JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
			value=JSON.toJSONString(value,SerializerFeature.WriteDateUseDateFormat);
			response.setCharacterEncoding(encoding);
			response.setContentType(contentType);
			PrintWriter out= response.getWriter();
			out.write(value);
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


//	@InitBinder
//	public void myInitBinder(WebDataBinder webDataBinder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		dateFormat.setLenient( false);
//		webDataBinder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
//	}
	/**
	 * 验证Bean实例对象
	 */
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}

	/************
	 * 打印JSON
	 * @param o
	 * @param response
	 */
	public void writeJson(Object o, HttpServletResponse response) {
		//JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
		writeJson(JSON.toJSONString(o, SerializerFeature.WriteDateUseDateFormat), response);
	}
	/************
	 * 打印JSON
	 * @param o
	 * @param response
	 */
	public void writeJson(String o, HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/************
	 * 打印JSON
	 * @param o
	 * @param response
	 */
	  public static void writeJSON(Object json, HttpServletResponse response)
	  {
		try {
			response.setContentType("text/json;charset=utf-8");
			byte[] jsonBytes = json.toString().getBytes("utf-8");

			response.setContentLength(jsonBytes.length);
			response.getOutputStream().write(jsonBytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
		} finally {
			try {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (IOException e) {
			}
		}
	}
	
}
