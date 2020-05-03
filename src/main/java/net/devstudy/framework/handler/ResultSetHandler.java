package net.devstudy.framework.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
	
	T handle(ResultSet rs) throws SQLException;
	
}
