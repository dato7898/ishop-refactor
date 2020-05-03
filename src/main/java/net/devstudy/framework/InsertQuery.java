package net.devstudy.framework;

import java.lang.reflect.Field;
import java.util.List;

public class InsertQuery {
	Field idField;
	StringBuilder sql;
	List<Object> params;

	public InsertQuery(Field idField, StringBuilder sql, List<Object> params) {
		this.idField = idField;
		this.sql = sql;
		this.params = params;
	}

	public StringBuilder getSql() {
		return sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public Field getIdField() {
		return idField;
	}

}
