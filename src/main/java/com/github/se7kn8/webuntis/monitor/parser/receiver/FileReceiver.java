package com.github.se7kn8.webuntis.monitor.parser.receiver;

import com.github.se7kn8.webuntis.monitor.parser.ConfigHandler;
import com.github.se7kn8.webuntis.monitor.parser.ParseType;
import com.github.se7kn8.webuntis.monitor.parser.entry.ListEntry;
import com.github.se7kn8.webuntis.monitor.parser.entry.NewsEntry;
import com.github.se7kn8.webuntis.monitor.parser.entry.SubstituteListEntry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

import static com.github.se7kn8.webuntis.monitor.parser.ParseType.NEWS;

public class FileReceiver implements DataReceiver {

	private ConfigHandler handler;
	private BufferedWriter substituteWriter;
	private BufferedWriter newsWriter;
	private static final Logger log = Logger.getLogger(FileReceiver.class.getName());

	public FileReceiver(ConfigHandler handler) {
		this.handler = handler;
	}

	@Override
	public void setup() throws Exception {
		Path outputPath = Paths.get(handler.getProperties().getProperty(ConfigHandler.Constants.File.FILE_OUTPUT_DIR), "data.txt").toAbsolutePath();
		Files.createDirectories(outputPath.getParent());
		substituteWriter = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE);
		newsWriter = Files.newBufferedWriter(outputPath.getParent().resolve("news.txt"));
		log.info("FileReceiver created");
	}

	@Override
	public void exportData(List<ListEntry> entries, ParseType type) throws Exception {
		for (ListEntry entry : entries) {
			if (type == ParseType.SUBSTITUTE) {
				SubstituteListEntry listEntry = (SubstituteListEntry) entry;
				substituteWriter.write(listEntry.getDate() + "$" + listEntry.getClassName() + "$" + listEntry.getHours() + "$" + listEntry.getClasses() + "$" + listEntry.getSubject() + "$" + listEntry.getRoom() + "$" + listEntry.getTeacher() + "$" + listEntry.getInfoText() + "$" + listEntry.getText() + "\n");
			} else if (type == NEWS) {
				NewsEntry newsEntry = (NewsEntry) entry;
				newsWriter.write(newsEntry.getDate() + "$" + newsEntry.getSubject() + "$" + newsEntry.getBody() +"\n");
			}
		}
		log.info("File was written");
	}

	@Override
	public void close() throws IOException {
		substituteWriter.close();
		newsWriter.close();
		log.info("Closing file");
	}
}
