package com.github.se7kn8.webuntis.monitor.parser;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.logging.Logger;

public class HTTPClient {

	private static final Logger log = Logger.getLogger(HTTPClient.class.getName());
	private ConfigHandler handler;

	public HTTPClient(ConfigHandler handler) {
		this.handler = handler;
	}

	public JsonNode receiveData(int index, int initialDate) {
		try {
			log.info("Loading data for day with offset " + index);
			return Unirest.post(handler.getProperties().getProperty(ConfigHandler.Constants.WEBUNTIS_MONITOR_URL))
					.header("Content-type", "application/json")
					.body(Util.readResource("post-data/webuntis-post-data.json").replace("\"%1\"", String.valueOf(index)).replace("\"%2\"", String.valueOf(initialDate)))
					.asJson().getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonNode("");
	}
}
