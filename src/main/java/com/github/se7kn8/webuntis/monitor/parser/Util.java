package com.github.se7kn8.webuntis.monitor.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Util {

	public static String readResource(String resourceName) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(resourceName), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.GERMAN);
	private static final SimpleDateFormat endFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

	public static String parseWebUntisDate(int date) {
		try {
			return endFormat.format((format.parse(String.valueOf(date))));
		} catch (ParseException e) {
			throw new IllegalStateException("Can't parse webuntis date", e);
		}
	}

	public static int toWebuntisDate(Date now) {
		return Integer.valueOf(format.format(now));
	}

	public static <T> List<T> appendToList(List<T> list, List<T> rootList) {
		List<T> newList = new ArrayList<>();
		newList.addAll(rootList);
		newList.addAll(list);
		return newList;
	}

}
