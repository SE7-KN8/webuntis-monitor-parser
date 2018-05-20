package com.github.se7kn8.webuntis.monitor.parser.parser;

import com.github.se7kn8.webuntis.monitor.parser.ListEntry;
import com.mashape.unirest.http.JsonNode;

import java.util.List;

public interface DataParser {
	List<ListEntry> parseData(JsonNode node);
}
