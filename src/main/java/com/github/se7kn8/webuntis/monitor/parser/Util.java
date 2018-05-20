package com.github.se7kn8.webuntis.monitor.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
}
