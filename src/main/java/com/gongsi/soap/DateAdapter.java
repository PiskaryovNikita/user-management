package com.gongsi.soap;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;
//convert from obj to string, and from string to obj
//schema has few primitiv types
public class DateAdapter extends XmlAdapter<String, Date> {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String marshal(Date v) throws Exception {
		return dateFormat.format(v);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		java.util.Date date = dateFormat.parse(v);
		return new Date(date.getTime());
	}
}