package com.github.se7kn8.webuntis.monitor.parser.entry;

public class NewsEntry implements ListEntry {

	private final String subject;
	private final String body;
	private final String date;

	public NewsEntry(String subject, String body, String date) {
		this.subject = subject;
		this.body = body;
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getDate() {
		return date;
	}
}
