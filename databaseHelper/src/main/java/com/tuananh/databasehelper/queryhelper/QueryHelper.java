package com.tuananh.databasehelper.queryhelper;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tuananh.databasehelper.ConstantDatabase.AND;
import static com.tuananh.databasehelper.ConstantDatabase.ASC;
import static com.tuananh.databasehelper.ConstantDatabase.DESC;
import static com.tuananh.databasehelper.ConstantDatabase.FROM;
import static com.tuananh.databasehelper.ConstantDatabase.GROUP_BY;
import static com.tuananh.databasehelper.ConstantDatabase.HAVING;
import static com.tuananh.databasehelper.ConstantDatabase.JOIN;
import static com.tuananh.databasehelper.ConstantDatabase.LIKE;
import static com.tuananh.databasehelper.ConstantDatabase.LIMIT;
import static com.tuananh.databasehelper.ConstantDatabase.OFFSET;
import static com.tuananh.databasehelper.ConstantDatabase.ON;
import static com.tuananh.databasehelper.ConstantDatabase.ORDER_BY;
import static com.tuananh.databasehelper.ConstantDatabase.SELECT;
import static com.tuananh.databasehelper.ConstantDatabase.WHERE;

/**
 * Created by Tuan Anh on 2/2/2017.
 */
public class QueryHelper {
    private String mJoinTable = "";
    private String mWhereClause = "";
    private String mGroupByClause = "";
    private String mHavingClause = "";
    private String mOrderByClause = "";
    private String mLimitClause = "";
    private String mOffsetClause = "";
    private String mTableName = "";
    private List<String> mColumnList = new ArrayList<>();
    private Map<String, Object> mSelectionArgs = new HashMap<>();

    /**
     * use if select in 1 table
     *
     * @param tableName
     * @return QueryHelper
     */
    public QueryHelper setTableName(String tableName) {
        mTableName = tableName;
        return this;
    }

    /**
     * use if select with multiple table (join two table)
     *
     * @param masterTableName
     * @param valueTableName
     * @param columnMasterTableName
     * @param columnValueTableName
     * @return QueryHelper
     */
    public QueryHelper setJoinTable(String masterTableName,
                                    String valueTableName,
                                    String columnMasterTableName,
                                    String columnValueTableName) {
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(mJoinTable)) {
            builder.append(masterTableName);
        }
        builder.append(JOIN)
                .append(valueTableName)
                .append(ON)
                .append(columnMasterTableName)
                .append(" = ")
                .append(columnValueTableName);
        mJoinTable += builder.toString();
        return this;
    }

    /**
     * @param joinTable query join multiple table
     * @return QueryHelper
     */
    public QueryHelper setJoinTable(String joinTable) {
        mJoinTable = joinTable;
        return this;
    }

    /**
     * @param columnName
     * @return QueryHelper
     */
    public QueryHelper addColumn(String columnName) {
        mColumnList.add(columnName);
        return this;
    }

    /**
     * @param columnList
     * @return QueryHelper
     */
    public QueryHelper addAllColumns(List<String> columnList) {
        mColumnList.addAll(columnList);
        return this;
    }

    /**
     * @param columnList
     * @return QueryHelper
     */
    public QueryHelper setColumns(List<String> columnList) {
        mColumnList.clear();
        addAllColumns(columnList);
        return this;
    }

    /**
     * @return String select all ( * ) or select column
     */
    private String getSelectColumn() {
        StringBuilder selectColumn = new StringBuilder();
        if (mColumnList.size() == 0) selectColumn.append(" * ");
        else {
            for (String column : mColumnList) {
                if (!TextUtils.isEmpty(selectColumn)) {
                    selectColumn.append(", ");
                }
                selectColumn.append(column);
            }
        }
        return selectColumn.toString();
    }

    /**
     * add condition search extend
     *
     * @param columnName
     * @param val
     * @param queryLike
     * @return
     */
    public QueryHelper addCondition(String columnName, Object val, boolean queryLike) {
        String newWhere = "";
        if (val != null && !TextUtils.isEmpty(val.toString())) {
            if (queryLike && val instanceof String) {
                newWhere += columnName + LIKE + " ? ";
                mSelectionArgs.put(columnName, "%" + val + "%");
            } else {
                newWhere += columnName + " = ? ";
                mSelectionArgs.put(columnName, val);
            }
        }
        concatWhereClause(newWhere);
        return this;
    }

    /**
     * add condition search default
     *
     * @param columnName
     * @param val
     * @return
     */
    public QueryHelper addCondition(String columnName, Object val) {
        return addCondition(columnName, val, false);
    }

    /**
     * check mWhereClause add `AND`
     *
     * @param newWhere
     * @return
     */
    public QueryHelper concatWhereClause(String newWhere) {
        if (!TextUtils.isEmpty(newWhere)) {
            if (!TextUtils.isEmpty(mWhereClause)) {
                mWhereClause += AND;
            }
            mWhereClause += newWhere;
        }
        return this;
    }

    /**
     * choose sort
     *
     * @param columnName
     * @param isDesc
     * @return
     */
    public QueryHelper addOrderBy(String columnName, boolean isDesc) {
        if (TextUtils.isEmpty(columnName)) {
            return this;
        }
        String newOrderBy = columnName;
        newOrderBy += isDesc ? DESC : ASC;
        concatOrderByClause(newOrderBy);
        return this;
    }

    /**
     * add order by default
     *
     * @param columnName
     * @return
     */
    public QueryHelper addOrderBy(String columnName) {
        return addOrderBy(columnName, false);
    }

    /**
     * add ", " for sql query
     *
     * @param newOrderBy
     * @return
     */
    public QueryHelper concatOrderByClause(String newOrderBy) {
        if (!TextUtils.isEmpty(newOrderBy)) {
            if (!TextUtils.isEmpty(mOrderByClause)) {
                mOrderByClause += ", ";
            }
            mOrderByClause += newOrderBy;
        }
        return this;
    }

    public QueryHelper concatGroupByClause(String newGroupBy) {
        if (!TextUtils.isEmpty(newGroupBy)) {
            if (!TextUtils.isEmpty(mGroupByClause)) {
                mGroupByClause += ", ";
            }
            mGroupByClause += newGroupBy;
        }
        return this;
    }

    public QueryHelper addGroupBy(String columnName) {
        if (TextUtils.isEmpty(columnName)) {
            return this;
        }
        String newGroupBy = columnName;
        concatGroupByClause(newGroupBy);
        return this;
    }

    public QueryHelper addHaving(String having) {
        mHavingClause = having;
        return this;
    }

    public QueryHelper addLimit(String limit) {
        mLimitClause = limit;
        return this;
    }

    public QueryHelper addOffset(String offset) {
        mOffsetClause = offset;
        return this;
    }

    /**
     * @return String query sql from all
     */
    public String getSqlQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(SELECT)
                .append(getSelectColumn())
                .append(FROM)
                .append(!TextUtils.isEmpty(mJoinTable) ? mJoinTable : mTableName)
                .append(!TextUtils.isEmpty(mWhereClause) ? WHERE + mWhereClause : "")
                .append(!TextUtils.isEmpty(mGroupByClause) ? GROUP_BY + mGroupByClause : "")
                .append(!TextUtils.isEmpty(mGroupByClause) && !TextUtils.isEmpty(mHavingClause)
                        ? HAVING + mHavingClause : "")
                .append(!TextUtils.isEmpty(mOrderByClause) ? ORDER_BY + mOrderByClause : "")
                .append(!TextUtils.isEmpty(mLimitClause) ? LIMIT + mLimitClause : "")
                .append(!TextUtils.isEmpty(mLimitClause) && !TextUtils.isEmpty(mOffsetClause)
                        ? OFFSET + mOffsetClause : "");
        return sqlQuery.toString();
    }

    /**
     * @return String[] selectionArgs
     */
    public String[] getSelectionArgs() {
        Object[] values = mSelectionArgs.values().toArray();
        int length = values.length;
        String[] selectionArgs = new String[length];
        for (int i = 0; i < length; i++) {
            selectionArgs[i] = values[i].toString();
        }
        return selectionArgs;
    }
}

