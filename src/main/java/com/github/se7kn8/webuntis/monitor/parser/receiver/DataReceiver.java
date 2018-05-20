package com.github.se7kn8.webuntis.monitor.parser.receiver;

import com.github.se7kn8.webuntis.monitor.parser.ListEntry;

import java.io.Closeable;
import java.util.List;

public interface DataReceiver extends Closeable {
	void setup() throws Exception;
	void exportData(List<ListEntry> entries) throws Exception;
}
