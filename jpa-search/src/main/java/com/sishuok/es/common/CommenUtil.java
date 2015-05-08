package com.sishuok.es.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommenUtil {
	//日志对象
	private static final Logger LOG = LoggerFactory.getLogger(CommenUtil.class);

	/**
	 * URL编码
	 * @param str
	 * @return
	 */
	public static final String urlEncode(String str){
		if(str!=null){
			try {
				return URLEncoder.encode(str,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				LOG.warn("URL编码["+str+"]失败",e);
				return str;
			} 
		}
		return null;
	}
}
