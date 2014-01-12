package me.confuserr.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoredStatement {
	final String sql;
	final Object[] params;
	final Database db;
	private PreparedStatement statement = null;

	public StoredStatement(Database db, String sql) {
		this.db = db;
		this.sql = sql;
		this.params = null;
	}

	public StoredStatement(Database db, String sql, Object... params) {
		this.db = db;
		this.sql = sql;
		this.params = params;
	}

	public PreparedStatement getPreparedStatement() throws SQLException {
		if (statement != null)
			return statement;
	
		statement = db.prepare(sql);

		if (params == null)
			return statement;

		int i = 1;

		for (Object param : params) {
			statement.setObject(i, param);
			i++;
		}

		return statement;
	}

	public void execute() throws SQLException {
		if (statement == null)
			getPreparedStatement();
		
		statement.executeUpdate();
		statement.close();
	}
}
