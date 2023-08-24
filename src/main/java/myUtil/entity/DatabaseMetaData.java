package myUtil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Data
public class DatabaseMetaData {
    private Integer id;

    private String name;

    private Map<String, TableMetaData> tables;

    private String DBType;
}
