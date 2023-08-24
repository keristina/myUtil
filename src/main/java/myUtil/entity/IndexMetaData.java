package myUtil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Accessors
@Data
public class IndexMetaData {
    private String name;//索引名

    private boolean nonUnique;//索引允许不唯一

    private List<FieldWithPosition> indexList;//索引字段集合


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IndexMetaData indexMetaData = (IndexMetaData) o;
        return nonUnique == indexMetaData.nonUnique && name.equalsIgnoreCase(indexMetaData.name) && Objects.equals(indexList, indexMetaData.indexList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void addIndexField(String fieldNme, int position){
        if (this.indexList==null){
            this.indexList=new ArrayList<>();
        }
        this.indexList.add(new FieldWithPosition(fieldNme,position));
    }
}