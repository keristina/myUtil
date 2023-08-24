package myUtil.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Data
public class FieldMetadata {
    private Integer id;

    private String name;

    private Integer dataType;//数据类型标识，取值参考java.sql.Types

    private String TypeName;//数据类型名称

    private Integer nullable;//是否允许为空

    private Integer isIncrease;//是否自增

    private String defaultValue;//默认值

    private Integer length;//字段长度

    private Integer decimalLength;//小数位长度

    private Integer ordinalPosition;//列序号



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldMetadata fieldMetadata = (FieldMetadata) o;
        return Objects.equals(dataType, fieldMetadata.dataType) && Objects.equals(TypeName, fieldMetadata.TypeName) && Objects.equals(nullable, fieldMetadata.nullable) && Objects.equals(isIncrease, fieldMetadata.isIncrease) && Objects.equals(length, fieldMetadata.length) && Objects.equals(decimalLength, fieldMetadata.decimalLength) && name.equalsIgnoreCase(fieldMetadata.name) && Objects.equals(defaultValue, fieldMetadata.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataType, TypeName, nullable, isIncrease, defaultValue, length, decimalLength);
    }
}
