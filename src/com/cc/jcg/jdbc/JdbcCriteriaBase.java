package com.cc.jcg.jdbc;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class JdbcCriteriaBase
	implements JdbcCriteria {

    private String tableName;
    private int maxResults;
    protected final Collection<JdbcColumn<?>> columns = Collections.synchronizedSet(new TreeSet<>());
    private String whereLogic = "AND";

    public JdbcCriteriaBase() {
	super();
	maxResults = UNLIMITED_RESULTS;
	addColumns();
    }

    public JdbcCriteriaBase(String tableName) {
	this(tableName, UNLIMITED_RESULTS);
	addColumns();
    }

    public JdbcCriteriaBase(String tableName, int maxResults) {
	super();
	this.tableName = tableName;
	this.maxResults = maxResults;
	addColumns();
    }

    protected abstract void addColumns();

    @Override
    public synchronized String getTableName() {
	return tableName;
    }

    @Override
    public synchronized void setTableName(String tableName) {
	this.tableName = tableName;
    }

    @Override
    public synchronized int getMaxResults() {
	return maxResults;
    }

    @Override
    public synchronized void setMaxResults(int maxResults) {
	this.maxResults = maxResults;
    }

    @Override
    public Collection<JdbcColumn<?>> getAllColumns() {
	return columns;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(this.getClass().getSimpleName() + " [tableName=");
	builder.append(getTableName());
	builder.append("]");
	return builder.toString();
    }

    public static final String labelize(String s) {
	return camelize(s.toLowerCase(), " ");
    }

    public static final String camelize(String s) {
	return camelize(s, "");
    }

    private static String camelize(String s, String underscoreReplacement) {
	if (s != null && s.length() > 0) {
	    final StringBuffer r = new StringBuffer();
	    boolean nextToUpperCase = true;
	    for (int i = 0; i < s.length(); i++) {
		final String c = s.substring(i, i + 1);
		if (!c.equals("_")) {
		    if (nextToUpperCase) {
			r.append(c.toUpperCase());
			nextToUpperCase = false;
		    } else {
			r.append(c);
		    }
		} else {
		    r.append(underscoreReplacement);
		    nextToUpperCase = true;
		}
	    }
	    return r.toString();
	}
	return s;
    }

    @Override
    public final synchronized String getWhereLogic() {
	if (whereLogic == null || whereLogic.trim().isEmpty()) {
	    whereLogic = "AND";
	}
	return whereLogic;
    }

    @Override
    public final synchronized void setWhereLogic(String logic) {
	whereLogic = logic;
    }

    public <T> JdbcColumnValue<T> addJdbcColumnValue(String columnName, Class<T> valueType, Supplier<T> getter, Consumer<T> setter) {
	JdbcColumnValue<T> column = new JdbcColumnValue<T>() {

	    private Integer sortOrder = null;
	    private String label = labelize(columnName);
	    private boolean enabled = true;
	    private String operator = JdbcColumnValue.super.getOperator();
	    private final T defaultValue = getValue();
	    private JdbcOrderBy orderBy = JdbcOrderBy.NONE;

	    @Override
	    public final Class<T> getValueType() {
		return valueType;
	    }

	    @Override
	    public String getTableName() {
		return JdbcCriteriaBase.this.getTableName();
	    }

	    @Override
	    public String getColumnName() {
		return columnName;
	    }

	    @Override
	    public synchronized Integer getSortOrder() {
		return sortOrder;
	    }

	    @Override
	    public synchronized void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	    }

	    @Override
	    public synchronized String getLabel() {
		return label;
	    }

	    @Override
	    public synchronized void setLabel(String label) {
		this.label = label;
	    }

	    @Override
	    public T getValue() {
		return getter.get();
	    }

	    @Override
	    public void setValue(T value) {
		setter.accept(value);
	    }

	    @Override
	    public synchronized boolean isEnabled() {
		return enabled;
	    }

	    @Override
	    public synchronized void setEnabled(boolean enabled) {
		this.enabled = enabled;
	    }

	    @Override
	    public synchronized void setOperator(String operator) {
		this.operator = operator;
	    }

	    @Override
	    public synchronized String getOperator() {
		return operator;
	    }

	    @Override
	    public synchronized void resetValue() {
		setValue(defaultValue);
		orderBy = JdbcOrderBy.NONE;
	    }

	    @Override
	    public synchronized JdbcOrderBy getOrderBy() {
		return orderBy;
	    }

	    @Override
	    public synchronized void setOrderBy(JdbcOrderBy orderBy) {
		this.orderBy = orderBy;
	    }
	};
	columns.add(column);
	column.setSortOrder(columns.size() - 1);
	return column;
    }

    public <T> JdbcColumnValues<T> addJdbcColumnValues(String columnName, Class<T> valueType, Supplier<List<T>> getter, Consumer<List<T>> setter) {
	JdbcColumnValues<T> column = new JdbcColumnValues<T>() {

	    private Integer sortOrder = null;
	    private String label = labelize(columnName);
	    private boolean enabled = true;
	    private String operator = JdbcColumnValues.super.getOperator();
	    private final List<T> defaultValues = getValues();
	    private JdbcOrderBy orderBy = JdbcOrderBy.NONE;

	    @Override
	    public final Class<T> getValueType() {
		return valueType;
	    }

	    @Override
	    public String getTableName() {
		return JdbcCriteriaBase.this.getTableName();
	    }

	    @Override
	    public String getColumnName() {
		return columnName;
	    }

	    @Override
	    public synchronized Integer getSortOrder() {
		return sortOrder;
	    }

	    @Override
	    public synchronized void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	    }

	    @Override
	    public synchronized String getLabel() {
		return label;
	    }

	    @Override
	    public synchronized void setLabel(String label) {
		this.label = label;
	    }

	    @Override
	    public List<T> getValues() {
		return getter.get();
	    }

	    @Override
	    public void setValue(List<T> values) {
		setter.accept(values);
	    }

	    @Override
	    public synchronized boolean isEnabled() {
		return enabled;
	    }

	    @Override
	    public synchronized void setEnabled(boolean enabled) {
		this.enabled = enabled;
	    }

	    @Override
	    public synchronized void setOperator(String operator) {
		this.operator = operator;
	    }

	    @Override
	    public synchronized String getOperator() {
		return operator;
	    }

	    @Override
	    public synchronized void resetValue() {
		setValue(defaultValues);
		orderBy = JdbcOrderBy.NONE;
	    }

	    @Override
	    public synchronized JdbcOrderBy getOrderBy() {
		return orderBy;
	    }

	    @Override
	    public synchronized void setOrderBy(JdbcOrderBy orderBy) {
		this.orderBy = orderBy;
	    }
	};
	columns.add(column);
	column.setSortOrder(columns.size() - 1);
	return column;
    }
}
