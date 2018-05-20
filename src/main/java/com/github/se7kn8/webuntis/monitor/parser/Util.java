package com.github.se7kn8.webuntis.monitor.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Util {

	public static String readResource(String resourceName) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(resourceName)));
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return sb.toString();
	}
}
