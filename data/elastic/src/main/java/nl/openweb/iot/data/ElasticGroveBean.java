package nl.openweb.iot.data;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.db.GroveBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticGroveBean {

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String type;
    private boolean passive = true;

    public void update(nl.openweb.iot.wio.db.GroveBean other) {
        if (!name.equals(other.getName()) && !type.equals(other.getType())) {
            throw new IllegalArgumentException();
        }
        passive = passive && other.isPassive();
    }

    public ElasticGroveBean(GroveBean bean) {
        name = bean.getName();
        type = bean.getType();
        passive = bean.isPassive();
    }

    public GroveBean toGroveBean() {
        return new GroveBean(name, type, passive);
    }

}
