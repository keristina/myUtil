package myUtil.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class MySQLDataType {
    public final static int INT = 4;

    public final static int CHAR = 1;

    public final static int VARCHAR = 12;

    public final static int TINYINT = -6;

    public final static int BIT = -7;

    public final static int DATETIME = 93;

    public final static int TIME = 93;

    public final static int YEAR = 91;

    public final static int FLOAT = 7;//默认小数位为零

    public final static int DOUBLE = 8;//默认小数位为零

    public final static int DECIMAL = 3;

    public final static int DATE = 91;

    public final static int SMALLINT = 5;

    public final static int TEXT = -1;

    public final static int BIGINT = -5;

    public final static int BLOB = -4;


    public static final Map<Integer, String> DATA_MAP = ImmutableMap.<Integer, String>builder()
            .put(INT, "INT")
            .put(TINYINT, "TINYINT")
            .put(BIT, "BIT")
            .put(BIGINT, "BIGINT")
            .put(SMALLINT,"SMALLINT")
            .put(DOUBLE,"DOUBLE")
            .put(FLOAT,"FLOAT")
            .put(DECIMAL,"DECIMAL")
            .put(CHAR,"CHAR")
            .put(TEXT,"TEXT")
            .put(BLOB,"BLOB")
            .put(VARCHAR,"VARCHAR")
            .build();

}
