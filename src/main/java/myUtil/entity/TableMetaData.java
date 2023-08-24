package myUtil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Data
public class TableMetaData {
    private Integer id;

    private String name;

    private Map<String, FieldMetadata> columns;

    private TreeMap<Short, FieldMetadata> key;//主键

    private TreeMap<Short, FieldMetadata> removeKey;//待删除主键

    private Map<String, IndexMetaData> index;//索引

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableMetaData metaTable = (TableMetaData) o;
        return name.equalsIgnoreCase(metaTable.name)&& Objects.equals(columns, metaTable.columns) && Objects.equals(key, metaTable.key) && Objects.equals(index, metaTable.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
