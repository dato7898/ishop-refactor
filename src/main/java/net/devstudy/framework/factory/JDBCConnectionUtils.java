package net.devstudy.framework.factory;

import java.sql.Connection;

import net.devstudy.framework.FrameworkSystemException;

final class JDBCConnectionUtils {
	private JDBCConnectionUtils() {}
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	static Connection getCurrentConnection() {
		Connection c = connections.get();
		if (c == null) {
			throw new FrameworkSystemException("Connection not found for current thread. Does your business "
					+ "service have @Transactional annotation?");
		}
		return c;
	}
	static void setCurrentConnection(Connection c) {
		connections.set(c);
	}
	static void removeCurrentConnection() {
		connections.remove();
	}
}
