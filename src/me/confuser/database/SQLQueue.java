package me.confuser.database;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SQLQueue {
	private ConcurrentLinkedQueue<StoredStatement> storedStatements = new ConcurrentLinkedQueue<StoredStatement>();

	public void add(StoredStatement statement) {
		storedStatements.add(statement);
	}

	public StoredStatement next() {
		return storedStatements.remove();
	}
	
	public boolean hasNext() {
		return storedStatements.size() > 0;
	}
}
