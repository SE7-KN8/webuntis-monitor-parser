package com.github.se7kn8.webuntis.monitor.parser.receiver;

import com.github.se7kn8.webuntis.monitor.parser.ConfigHandler;
import com.github.se7kn8.webuntis.monitor.parser.ListEntry;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class SQLReceiver implements DataReceiver {

	private ConfigHandler handler;
	private Connection connection;

	private static final Logger log = Logger.getLogger(SQLReceiver.class.getName());

	public SQLReceiver(ConfigHandler handler) {
		this.handler = handler;
	}

	@Override
	public void setup() throws Exception {
		connectToMySql(handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_IP) + ":" + handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_PORT),
				handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_SCHEMA),
				handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_USERNAME),
				handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_PASSWORD));

		Statement checkTableStatement = connection.createStatement();
		ResultSet set = checkTableStatement.executeQuery("show tables like 'student_substitute';");
		if (!(set.first() && set.getString(1).equals("student_substitute"))) {
			Statement createTableStatement = connection.createStatement();
			createTableStatement.execute("CREATE TABLE `" + handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_SCHEMA) + "`.`student_substitute` (\n" +
					"  `id` INT AUTO_INCREMENT,\n" +
					"  `date` TEXT,\n" +
					"  `className` TEXT,\n" +
					"  `hour` TEXT,\n" +
					"  `classes` TEXT,\n" +
					"  `subject` TEXT,\n" +
					"  `room` TEXT,\n" +
					"  `teacher` TEXT,\n" +
					"  `info_text` TEXT,\n" +
					"  `text` TEXT,\n" +
					"  PRIMARY KEY (`id`));");
			log.info("Table created!");
		}
	}

	@Override
	public void exportData(List<ListEntry> entries) throws Exception {

		Statement updateDataStatement = connection.createStatement();

		StringBuilder queries = new StringBuilder();

		boolean first = true;

		for (ListEntry entry : entries) {
			String query = "('" +
					entry.getDate() + "', '" +
					entry.getClassName() + "', '" +
					entry.getHours() + "', '" +
					entry.getClasses() + "', '" +
					entry.getSubject() + "', '" +
					entry.getRoom() + "', '" +
					entry.getTeacher() + "', '" +
					entry.getInfoText() + "', '" +
					entry.getText() + "')";
			if (first) {
				queries.append(query);
				first = false;
			} else {
				queries.append(",");
				queries.append(query);
			}
		}
		updateDataStatement.execute("truncate student_substitute;");
		log.info("Old table cleared");
		updateDataStatement.execute("INSERT INTO `" + handler.getProperties().getProperty(ConfigHandler.Constants.SQL.SQL_SCHEMA) + "`.`student_substitute` (`date`, `className`, `hour`, `classes`, `subject`, `room`, `teacher`, `info_text`, `text`) VALUES " + queries.toString() + ";\n");
		log.info("New data inserted");
	}

	@Override
	public void close() throws IOException {
		try {
			connection.close();
			log.info("Connection closed");
		} catch (SQLException e) {
			throw new IOException("Can't close sql connection: ", e);
		}
	}

	private void connectToMySql(String host, String schema, String user, String password) throws IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String connectionCommand = "jdbc:mysql://" + host + "/" + URLEncoder.encode(schema, "UTF-8") + "?user=" + URLEncoder.encode(user, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			connection = DriverManager.getConnection(connectionCommand);
			log.info("Connected to database!");
		} catch (Exception e) {
			throw new IOException("Can't connect to " + host, e);
		}
	}

}
