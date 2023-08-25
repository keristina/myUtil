package myUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import myUtil.constants.MetaDataConstants;
import myUtil.constants.OracleDataType;
import myUtil.entity.DatabaseMetaData;
import myUtil.entity.FieldMetadata;
import myUtil.entity.TableMetaData;
import myUtil.utilInterface.DatabaseMetaDataUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
@Accessors
@Data
public class OracleMetaDataUtil implements DatabaseMetaDataUtil {

    private Connection connection;

    OracleMetaDataUtil(Connection connection) {
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
        return getScheme();
    }

    @Override
    public String getDatabaseType() throws SQLException {
        return connection.getMetaData().getDatabaseProductName();
    }

    @Override
    public void setMetaDBbyConnection(DatabaseMetaData databaseMetaData) throws SQLException {
        databaseMetaData.setName(getUserName());
        java.sql.DatabaseMetaData metaData = connection.getMetaData();
        String[] types = {MetaDataConstants.tableType_Table};//仅提取表结构
        ResultSet tables = metaData.getTables(connection.getCatalog(), databaseMetaData.getName(), "%", types);
        List<String> tableName = new ArrayList<>();
        while (tables.next()) {
            tableName.add(tables.getString(MetaDataConstants.metaDataKey_tableName));  //表名
        }
        TreeMap<String, TableMetaData> tableMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String s : tableName) {
            tableMap.put(s, getTableMetaDataByDatabaseMetaData(metaData, getDatabaseName(), s));
        }
        databaseMetaData.setTables(tableMap);
    }

    @Override
    public TableMetaData getTableMetaDataByDatabaseMetaData(java.sql.DatabaseMetaData metaData, String dataBaseName, String tableName) throws SQLException {
        TableMetaData metaTable = new TableMetaData();
        metaTable.setName(tableName);
        ResultSet columns = metaData.getColumns(null, dataBaseName, tableName, null);
        Map<String, FieldMetadata> columnMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        TreeMap<Short, FieldMetadata> keyMap = new TreeMap<>();
        while (columns.next()) {
            FieldMetadata fieldMetadata = new FieldMetadata();
            FieldMetadataMapping(columns, fieldMetadata);
            if (fieldMetadata.getDataType() == OracleDataType.NUMBER && fieldMetadata.getLength() == 0 && fieldMetadata.getDecimalLength() == -127) {
                fieldMetadata.setLength(38);
                fieldMetadata.setDecimalLength(6);
            }//若长度精度为0，-127则表示NUMBER未声明长度与精度，设为默认值
            columnMap.put(fieldMetadata.getName(), fieldMetadata);
        }
        metaTable.setColumns(columnMap);
        ResultSet keys = metaData.getPrimaryKeys(null, dataBaseName, tableName);
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