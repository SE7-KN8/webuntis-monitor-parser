package com.github.se7kn8.webuntis.monitor.parser;

import com.github.se7kn8.webuntis.monitor.parser.entry.ListEntry;
import com.github.se7kn8.webuntis.monitor.parser.parser.NewsParser;
import com.github.se7kn8.webuntis.monitor.parser.parser.SubstituteParser;
import com.github.se7kn8.webuntis.monitor.parser.receiver.DataReceiver;
import com.github.se7kn8.webuntis.monitor.parser.receiver.FileReceiver;
import com.github.se7kn8.webuntis.monitor.parser.receiver.SQLReceiver;
import com.mashape.unirest.http.JsonNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WebUntisMonitorParser {

	static {
		try {
			LogManager.getLogManager().readConfiguration(ClassLoader.getSystemResourceAsStream("logging.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final Logger log = Logger.getLogger(WebUntisMonitorParser.class.getName());

	public static void main(String[] args) throws Exception {
		if (args.length == 1) {
			Path configPath = Paths.get(args[0]);
			if (checkForConfig(configPath)) {
				ConfigHandler handler = new ConfigHandler(configPath);
				handler.saveConfig();

				List<ListEntry> substituteList = new ArrayList<>();
				List<ListEntry> newsList = new ArrayList<>();
				for (int i = 0; i < Integer.valueOf(handler.getProperties().getProperty(ConfigHandler.Constants.DAYS_TO_LOAD)); i++) {
					JsonNode node = new HTTPClient(handler).receiveData(i);
					List<ListEntry> substituteEntries = new SubstituteParser().parseData(node);
					List<ListEntry> newsEntries = new NewsParser().parseData(node);

					substituteList = Util.appendToList(substituteEntries, substituteList);
					newsList = Util.appendToList(newsEntries, newsList);
				}

				DataReceiver receiver;

				switch (handler.getProperties().getProperty(ConfigHandler.Constants.DATA_RECEIVER)) {
					case "file":
						receiver = new FileReceiver(handler);
						break;
					case "sql":
						receiver = new SQLReceiver(handler);
						break;
					default:
						log.severe("Data receiver not found. Please use 'file' or 'sql'");
						throw new IllegalArgumentException("Data receiver not found");
				}
				try {
					receiver.setup();
				} catch (Exception e) {
					throw new IllegalStateException("There was an error while opening the data receiver", e);
				}
				try {
					receiver.exportData(substituteList, ParseType.SUBSTITUTE);
					receiver.exportData(newsList, ParseType.NEWS);
				} catch (Exception e) {
					throw new IllegalStateException("There was an error while exporting the data", e);
				}
				try {
					receiver.close();
				} catch (Exception e) {
					throw new IllegalStateException("There was an error while closing the data receiver", e);
				}
			}
		} else if (args.length == 2) {
			Path configPath = Paths.get(args[0]);
			if (checkForConfig(configPath)) {
				if (args[1].equalsIgnoreCase("file") || args[1].equalsIgnoreCase("sql")) {
					ConfigHandler handler = new ConfigHandler(configPath, args[1]);
					handler.saveConfig();
					log.info("Default config was saved. You can now edit the config and restart the program without the intial data receiver parameter");
				} else {
					log.severe("Initial data receiver is neither 'file' nor 'sql'");
				}
			}
		} else {
			log.severe("Usage: <Config path: conf.properties|/home/conf/conf.properties> [Initial data receiver: file|sql]");
		}

	}

	private static boolean checkForConfig(Path configPath) {
		if (!Files.isDirectory(configPath)) {
			return true;
		} else {
			log.severe("Config path is not a file");
			return false;
		}
	}

}
