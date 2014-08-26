package com.allthelucky.examples.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegUtil {
	
	public static final String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><cupMobile application=\"UPCards\" version=\"1.01\"><transaction type=\"PurchaseAdvice.PARsp\"><submitTime>20140819151552</submitTime><merchant id=\"249EFAD79BF5A0395369400785220CD2\"/><terminal id=\"A76AB9FC3017DD78\"/><order id=\"43C414F5C5A496F1\"/><settleDate>20140819</settleDate><transAmount currency=\"1012E29101473767\">A0C1B492490553FDDE5DE3A3A02DC9FA</transAmount><billAmount currency=\"1012E29101473767\" convertRate=\"A0C1B492490553FD\">A0C1B492490553FDDE5DE3A3A02DC9FB</billAmount><accountNumber1>B80CC3E78E5DEECB2265D6BFDD5EFFEB</accountNumber1><responseCode>55</responseCode><transSerialNumber>605942</transSerialNumber></transaction><mac>267130DC</mac></cupMobile>";
	
	public static void main(String argv[]) { 
		System.out.println(RegUtil.getValueKey("<mac>(.*?)</mac>", xml));
		System.out.println(RegUtil.getValueKey("<transAmount currency=\"(.*?)\">", xml));
		System.out.println(RegUtil.getValueKey("convertRate=\"(.*?)\">", xml));
		System.out.println(RegUtil.getValueKey("<transAmount.*?\">(.*?)</transAmount>", xml));
		System.out.println(RegUtil.getValueKey("<billAmount.*?\">(.*?)</billAmount>", xml));
		System.out.println(RegUtil.getValueKey("<billAmount.*?\">(.*?)</billAmount>", xml));
	}

	public static List<String> getValueContainReg(String regInput,
			String content) {
		Pattern pattern = Pattern.compile(regInput);
		List<String> rows = new ArrayList<String>();
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			addValue(rows, matcher.group());
		}
		return rows;

	}

	public static List<String> getValueNotContainReg(String regInput,
			String content) {
		Pattern pattern = Pattern.compile(regInput);
		List<String> rows = new ArrayList<String>();
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			addValue(rows, matcher.group(1));
		}
		return rows;

	}

	public static List<String> getValueNotContainReg(String regInput,
			String regInput2, String content) {
		Pattern pattern = Pattern.compile(regInput);
		List<String> rows = new ArrayList<String>();
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			addValue(rows, getValueKey(regInput2, matcher.group()));
		}
		return rows;

	}

	public static String getValueKey(String regInput, String content) {
		if(content!=null){
			Pattern pattern = Pattern.compile(regInput);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				return matcher.group(1);
			}
		}
		return null;
	}
	public static String getValueKeyContainReg(String regInput, String content) {
		Pattern pattern = Pattern.compile(regInput);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	public static void addValue(List<String> list, String s){
		if(null != s && !"".equals(s.trim())){
			list.add(s);
		}
	}
}
