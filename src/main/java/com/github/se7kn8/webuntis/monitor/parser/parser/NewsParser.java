package com.github.se7kn8.webuntis.monitor.parser.parser;

import com.github.se7kn8.webuntis.monitor.parser.Util;
import com.github.se7kn8.webuntis.monitor.parser.entry.ListEntry;
import com.github.se7kn8.webuntis.monitor.parser.entry.NewsEntry;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NewsParser implements DataParser {

	private static final Logger log = Logger.getLogger(SubstituteParser.class.getName());

	@Override
	public List<ListEntry> parseData(JsonNode node) {
		List<ListEntry> entries = new ArrayList<>();
		log.info("Parsing json data");
		try {
			JSONObject root = node.getObject().getJSONObject("payload");
			JSONObject messageData = root.getJSONObject("messageData");
			JSONArray messages = messageData.getJSONArray("messages");
			int date = root.getInt("date");
			for (Object o : messages) {
				if (o instanceof JSONObject) {
					JSONObject messagesObject = (JSONObject) o;
					entries.add(new NewsEntry(messagesObject.getString("subject"), messagesObject.getString("body"), Util.parseWebUntisDate(date)));
				}
			}

			log.info("Successfully parsed json data");
			return entries;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
