package me.yoyo.common;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

public class CommonUtils {
	/**
	 * 返回一个不重复的字符串
	 * @return
	 */
	public static String uuid()
	{
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T toBean(Map map,Class<T> clazz)
	{
		T bean;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean,map);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
