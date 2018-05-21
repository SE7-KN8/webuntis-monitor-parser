package com.github.se7kn8.webuntis.monitor.parser.parser;

import com.github.se7kn8.webuntis.monitor.parser.ListEntry;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class DefaultParser implements DataParser {

	private static final Logger log = Logger.getLogger(DefaultParser.class.getName());

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.GERMAN);
	private static final SimpleDateFormat endFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

	@Override
	public List<ListEntry> parseData(JsonNode node) {
		List<ListEntry> entries = new ArrayList<>();
		log.info("Parsing json data");
		try {
			JSONObject root = node.getObject().getJSONObject("payload");
			JSONArray rows = root.getJSONArray("rows");
			String date = String.valueOf(root.getInt("date"));
			for (Object element : rows) {
				if (element instanceof JSONObject) {
					JSONObject classEntry = (JSONObject) element;
					String className = classEntry.getString("group");
					JSONArray dataArray = classEntry.getJSONArray("data");
					String hour = dataArray.getString(0);
					String classes = dataArray.getString(1);
					String subject = dataArray.getString(2);
					String room = dataArray.getString(3);
					String teacher = dataArray.getString(4);
					String infoText = dataArray.getString(5);
					String text = dataArray.getString(6);

					entries.add(new ListEntry(className, hour, classes, subject, room, teacher, text, infoText, endFormat.format((format.parse(date)))));
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
