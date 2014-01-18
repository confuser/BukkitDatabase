package me.confuser.database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.plugin.java.JavaPlugin;

public class SQLQueue implements Runnable {
	private JavaPlugin plugin;
	private ConcurrentLinkedQueue<StoredStatement> storedStatements = new ConcurrentLinkedQueue<StoredStatement>();

	public SQLQueue(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void add(StoredStatement statement) {
		storedStatements.add(statement);
	}

	public StoredStatement next() {
		return storedStatements.remove();
	}

	public boolean hasNext() {
		return storedStatements.size() > 0;
	}

	public void run() {
		while (hasNext()) {
			StoredStatement statement = next();

			try {
				statement.execute();
			} catch (SQLException e) {
				plugin.getLogger().severe("Query Failed: " + statement.getSQL());
				e.printStackTrace();
			}
		}
	}
}
