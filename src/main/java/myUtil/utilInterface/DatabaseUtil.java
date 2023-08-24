package myUtil.utilInterface;

import myUtil.constants.MetaDataConstants;
import myUtil.entity.DatabaseMetaData;
import myUtil.entity.FieldMetadata;
import myUtil.entity.TableMetaData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseUtil {
    String getUserName() throws SQLException;

    String getScheme() throws SQLException;

    String getCatalog() throws SQLException;

    String getDatabaseName() throws SQLException;

    String getDatabaseType() throws SQLException;

    void setMetaDBbyConnection(DatabaseMetaData databaseMetaData) throws SQLException;

    TableMetaData getTableMetaDataByDatabaseMetaData(java.sql.DatabaseMetaData metaData, String dataBaseName, String tableName) throws SQLException;

    default DatabaseMetaData getDatabaseMetaData() throws SQLException {
        DatabaseMetaData databaseMetaData = new DatabaseMetaData();
        databaseMetaData.setDBType(getDatabaseType());
        setMetaDBbyConnection(databaseMetaData);
        return databaseMetaData;
    }
    default void FieldMetadataMapping(ResultSet columns, FieldMetadata fieldMetadata) throws SQLException {
        fieldMetadata.setName(columns.getString(MetaDataConstants.metaDataKey_columnName));  //列名
        fieldMetadata.setDataType(columns.getInt(MetaDataConstants.metaDataKey_dataType));     //对应的java.sql.Types的SQL类型(列类型ID)
        fieldMetadata.setTypeName(columns.getString(MetaDataConstants.metaDataKey_dataTypeName));  //java.sql.Types类型名称(列类型名称)
        fieldMetadata.setLength(columns.getInt(MetaDataConstants.metaDataKey_length));  //列大小
        fieldMetadata.setDecimalLength(columns.getInt(MetaDataConstants.metaDataKey_decimalLength));  //小数位数
        /**
         *  0 (columnNoNulls) - 该列不允许为空
         *  1 (columnNullable) - 该列允许为空
         *  2 (columnNullableUnknown) - 不确定该列是否为空
         */
        fieldMetadata.setNullable(columns.getInt(MetaDataConstants.metaDataKey_nullable));  //是否允许为null
        fieldMetadata.setDefaultValue(columns.getString(MetaDataConstants.metaDataKey_defaultValue));  //默认值
        fieldMetadata.setOrdinalPosition(columns.getInt("ORDINAL_POSITION"));//设置列序号
        fieldMetadata.setIsIncrease("YES".equals(columns.getString(MetaDataConstants.metaDataKey_isIncrease)) ? 1 : 0);   //是否自增
    }
}
