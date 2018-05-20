package com.github.se7kn8.webuntis.monitor.parser.receiver;

import com.github.se7kn8.webuntis.monitor.parser.ConfigHandler;
import com.github.se7kn8.webuntis.monitor.parser.ListEntry;
import com.github.se7kn8.webuntis.monitor.parser.receiver.DataReceiver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

public class FileReceiver implements DataReceiver {

	private ConfigHandler handler;
	private BufferedWriter writer;
	private static final Logger log = Logger.getLogger(FileReceiver.class.getName());

	public FileReceiver(ConfigHandler handler) {
		this.handler = handler;
	}

	@Override
	public void setup() throws Exception {
		Path outputPath = Paths.get(handler.getProperties().getProperty(ConfigHandler.Constants.File.FILE_OUTPUT_DIR), "data.txt").toAbsolutePath();
		Files.createDirectories(outputPath.getParent());
		writer = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE);
		log.info("FileReceiver created");
	}

	@Override
	public void exportData(List<ListEntry> entries) throws Exception {
		for (ListEntry entry : entries) {
			writer.write(entry.getDate() + "$" + entry.getClassName() + "$" + entry.getHours() + "$" + entry.getClasses() + "$" + entry.getSubject() + "$" + entry.getRoom() + "$" + entry.getTeacher() + "$" + entry.getInfoText() + "$" + entry.getText() + "\n");
		}
		log.info("File was written");
	}

	@Override
	public void close() throws IOException {
		writer.close();
		log.info("Closing file");
	}
}
