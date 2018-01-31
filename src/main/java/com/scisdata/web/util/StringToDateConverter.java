package com.scisdata.web.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 将字符串转换为日期对象转换器
 *
 */
public class StringToDateConverter implements Converter<String, Date> {
	/** log4j对象 */
	private static final Logger log = Logger.getLogger(StringToDateConverter.class);

	@Override
	public Date convert(String value) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		try {
			if (value.matches("^\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z$")) {
				String fmt = null;
				if (value.matches("^.+\\.\\d{3}Z$")) {
					fmt = "yyyyMMddHHmmssSSS";
				} else {
					fmt = "yyyyMMddHHmmss";
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new SimpleDateFormat(fmt).parse(value.replaceAll("\\D", "")));
				calendar.add(Calendar.MILLISECOND, Calendar.getInstance().getTimeZone().getRawOffset());
				return calendar.getTime();
			}
			if(value.matches("^\\d+$")) {
				return new Date(Long.valueOf(value));
			}
			if(JsonUtil.isJsonObjectString(value)) {
				return JsonUtil.convert(value, Date.class);
			}
			return DateUtils.parseDate(value, new String[] { "yyyy-MM", "yyyy-MM-dd",
					"yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss" });
		} catch (Exception e) {
			log.warn(e);
			return null;
		}
	}

}
