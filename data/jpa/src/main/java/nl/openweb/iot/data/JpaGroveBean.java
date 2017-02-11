package nl.openweb.iot.data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.openweb.iot.wio.db.GroveBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "node")
@Entity(name = "grove")
public class JpaGroveBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private JpaNodeBean node;

    private String name;
    private String type;
    private boolean passive = true;

    public void update(nl.openweb.iot.wio.db.GroveBean other) {
        if (!name.equals(other.getName()) && !type.equals(other.getType())) {
            throw new IllegalArgumentException();
        }
        passive = passive && other.isPassive();
    }

    public JpaGroveBean(GroveBean bean) {
        name = bean.getName();
        type = bean.getType();
        passive = bean.isPassive();
    }

    public GroveBean toGroveBean() {
        return new GroveBean(name, type, passive);
    }

}
