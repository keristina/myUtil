package myUtil;

import com.alibaba.druid.util.StringUtils;
import myUtil.constants.MetaDataConstants;
import myUtil.constants.MySQLDataType;
import myUtil.entity.DatabaseMetaData;
import myUtil.entity.FieldMetadata;
import myUtil.entity.IndexMetaData;
import myUtil.entity.TableMetaData;
import myUtil.utilInterface.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MysqlUtil implements DatabaseUtil {

    private Connection connection;
    MysqlUtil(Connection connection){
        this.connection = connection;
    }
    @Override
    public String getUserName() throws SQLException {
        return connection.getMetaData().getUserName();
    }

    @Override
    public String getScheme() throws SQLException {
        return connection.getSchema();
    }

    @Override
    public String getCatalog() throws SQLException {
        return connection.getCatalog();
    }

    @Override
    public String getDatabaseName() throws SQLException {
        return getCatalog();
    }

    @Override
    public String getDatabaseType() throws SQLException {
        return connection.getMetaData().getDatabaseProductName();
    }

    @Override
    public void setMetaDBbyConnection(DatabaseMetaData databaseMetaData) throws SQLException {
        databaseMetaData.setName(connection.getCatalog());
        java.sql.DatabaseMetaData metaData = connection.getMetaData();
        String[] types = {MetaDataConstants.tableType_Table};//仅提取表结构
        ResultSet tables = metaData.getTables(connection.getCatalog(), null, "%", types);
        List<String> tableName = new ArrayList<>();
        while (tables.next()) {
            tableName.add(tables.getString(MetaDataConstants.metaDataKey_tableName));  //表名
        }
        TreeMap<String, TableMetaData> tableMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String s : tableName) {
            tableMap.put(s, getTableMetaDataByDatabaseMetaData(metaData, connection.getCatalog(), s));
        }
        databaseMetaData.setTables(tableMap);
    }

    @Override
    public TableMetaData getTableMetaDataByDatabaseMetaData(java.sql.DatabaseMetaData metaData, String dataBaseName, String tableName) throws SQLException {
        TableMetaData metaTable = new TableMetaData();
        metaTable.setName(tableName);
        ResultSet columns = metaData.getColumns(dataBaseName, null, tableName, null);
        Map<String, FieldMetadata> columnMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        TreeMap<Short, FieldMetadata> keyMap = new TreeMap<>();
        while (columns.next()) {
            FieldMetadata fieldMetadata = new FieldMetadata();
            FieldMetadataMapping(columns, fieldMetadata);
            if ((fieldMetadata.getDataType() == MySQLDataType.DOUBLE || fieldMetadata.getDataType() == MySQLDataType.FLOAT) && fieldMetadata.getDecimalLength() == 0) {
                fieldMetadata.setDecimalLength(6);
            }
            columnMap.put(fieldMetadata.getName(), fieldMetadata);
        }
        metaTable.setColumns(columnMap);
        ResultSet keys = metaData.getPrimaryKeys(dataBaseName, null, tableName);
        while (keys.next()) {
            String columnName = keys.getString(MetaDataConstants.metaDataKey_columnName);//列名
            short keySeq = keys.getShort(MetaDataConstants.metaDataKey_keySeq);//列序号
            keyMap.put(keySeq, columnMap.get(columnName));
        }
        metaTable.setKey(keyMap);
        return metaTable;
    }

    @Override
    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        DatabaseMetaData databaseMetaData = new DatabaseMetaData();
        setMetaDBbyConnection(databaseMetaData);
        return databaseMetaData;
    }
}
