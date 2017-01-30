package nl.openweb.iot.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.openweb.iot.wio.db.GroveBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "grove")
public class JpaGroveBean {

    @Id
    @GeneratedValue
    private Long id;

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
