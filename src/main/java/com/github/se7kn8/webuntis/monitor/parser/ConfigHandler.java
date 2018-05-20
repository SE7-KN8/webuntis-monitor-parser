package com.github.se7kn8.webuntis.monitor.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigHandler {

	public class Constants {
		public static final String WEBUNTIS_MONITOR_URL = "webuntis-monitor-url";
		public static final String DAYS_TO_LOAD = "days-to-load";
		public static final String DATA_RECEIVER = "data-receiver";

		public class File {
			public static final String FILE_OUTPUT_DIR = "file.output-dir";
		}

		public class SQL {
			public static final String SQL_USERNAME = "sql.username";
			public static final String SQL_PASSWORD = "sql.password";
			public static final String SQL_IP = "sql.ip";
			public static final String SQL_PORT = "sql.port";
			public static final String SQL_SCHEMA = "sql.schema";
		}

	}

	private Properties properties;
	private Path propertiesPath;

	public ConfigHandler(Path propertiesPath) throws IOException {
		this.propertiesPath = propertiesPath;
		properties = new Properties();
		if (!Files.exists(propertiesPath)) {
			saveConfig();
		}
		properties.load(Files.newBufferedReader(propertiesPath));
		//Should be already created
		setupDefaultValues("file");
	}

	public ConfigHandler(Path propertiesPath, String initialHandler) throws IOException {
		this.propertiesPath = propertiesPath;
		properties = new Properties();
		if (!Files.exists(propertiesPath)) {
			saveConfig();
		}
		properties.load(Files.newBufferedReader(propertiesPath));
		setupDefaultValues(initialHandler);
	}

	public Properties getProperties() {
		return properties;
	}


	private void setupDefaultValues(String initialHandler) {
		setupDefaultProperty(Constants.WEBUNTIS_MONITOR_URL, "https://demo.webuntis.com/WebUntis/monitor?school=demo_inf&monitorType=subst&format=KlassenMonitor");
		setupDefaultProperty(Constants.DAYS_TO_LOAD, "1");
		setupDefaultProperty(Constants.DATA_RECEIVER, initialHandler);

		switch (properties.getProperty(Constants.DATA_RECEIVER)) {
			case "file":
				setupDefaultProperty(Constants.File.FILE_OUTPUT_DIR, "webuntis-data");
				break;
			case "sql":
				setupDefaultProperty(Constants.SQL.SQL_USERNAME, "");
				setupDefaultProperty(Constants.SQL.SQL_PASSWORD, "");
				setupDefaultProperty(Constants.SQL.SQL_IP, "localhost");
				setupDefaultProperty(Constants.SQL.SQL_PORT, "3306");
				setupDefaultProperty(Constants.SQL.SQL_SCHEMA, "db1");
				break;
		}

	}

	private void setupDefaultProperty(String key, String defaultValue) {
		if (properties.getProperty(key) == null) {
			properties.setProperty(key, defaultValue);
		}
	}

	public void saveConfig() throws IOException {
		properties.store(Files.newBufferedWriter(propertiesPath), "");
	}

}
