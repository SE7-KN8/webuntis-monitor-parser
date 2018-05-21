package com.github.se7kn8.webuntis.monitor.parser;

public class ListEntry {

	private final String className;
	private final String hours;
	private final String classes;
	private final String subject;
	private final String room;
	private final String teacher;
	private final String infoText;
	private final String text;
	private final String date;

	public ListEntry(String className, String hours, String classes, String subject, String room, String teacher, String text, String infoText, String date) {
		this.className = className;
		this.hours = hours;
		this.classes = classes;
		this.subject = subject;
		this.room = room;
		this.teacher = teacher;
		this.text = text;
		this.infoText = infoText;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public String getClassName() {
		return className;
	}

	public String getHours() {
		return hours;
	}

	public String getClasses() {
		return classes;
	}

	public String getSubject() {
		return subject;
	}

	public String getRoom() {
		return room;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getText() {
		return text;
	}

	public String getInfoText() {
		return infoText;
	}
}
