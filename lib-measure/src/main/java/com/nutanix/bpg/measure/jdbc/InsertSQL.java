package com.nutanix.bpg.measure.jdbc;

import com.nutanix.bpg.measure.model.DataMapping;
import com.nutanix.bpg.measure.model.MetricsDimension;
/**
 * an SQL statement for insert
 * 
 * @author pinaki.poddar
 *
 */
public class InsertSQL extends SQL {

	public InsertSQL() {
		super(SQL.VERB.INSERT);
	}
	
	public String toString() {
		if (tableAliases.isEmpty()) {
			throw new IllegalStateException("no tables in INSERT");
		}
		String sql = verb + SPACE;
		sql += "INTO" + SPACE + tableAliases.get(0).toString()
			+ " ("
			+ concatenate(COMMA, projections)
			+ ") VALUES ("
			+ concatenate(COMMA, binders) + ")";
			
		
		return sql;
	}
	
	/**
	 * set table name to insert
	 * @param m
	 * @return
	 */
	public SQL into(String m) {
		if (!tableAliases.isEmpty()) {
			throw new IllegalStateException();
		}
		tableAliases.add(new TableAlias(m, null));
		return this;
	}
	
	/**
	 * set values with given measurement
	 * @param m
	 * @return
	 */
	
	public SQL insert(MetricsDimension dim, Object value) {
		project(dim, null);
		bind(dim, value);
		return this;
	}
	
	/**
	 * Inserts a column of given name.
	 * 
	 * @param name name of a column that must be resolvable 
	 * @param value
	 * @return
	 */
	public SQL insert(String name, Object value) {
		MetricsDimension dim = DataMapping.getMappedDimension(name);
		return insert(dim, value);
	}


}
