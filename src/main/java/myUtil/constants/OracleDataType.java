package myUtil.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class OracleDataType {
    public final static int NUMBER = 2;//无精度声明查询结果为（0，-127）

    public final static int VARCHAR2 = 12;

    public final static int FLOAT = 6;

    public final static int NVARCHAR2 = -9;

    public final static int TIMESTAMP1 = 93;

    public final static int TIMESTAMP2 = -101;

    public final static int TIMESTAMP3 = -102;

    public final static int DATE = 93;
    
    public final static int CHAR = 1;

    public final static int NCHAR = -15;

    public final static int BLOB = 2004;

    public final static int CLOB = 2005;

    public static final Map<Integer, String> DATA_MAP = ImmutableMap.<Integer, String>builder()
            .put(NUMBER, "NUMBER")
            .put(VARCHAR2, "VARCHAR2")
            .put(FLOAT, "FLOAT")
            .put(NVARCHAR2,"NVARCHAR2")
            .put(CHAR,"CHAR")
            .put(NCHAR,"NCHAR")
            .put(BLOB,"BLOB")
            .put(CLOB,"CLOB")
            .build();
}
