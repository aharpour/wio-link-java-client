package nl.openweb.iot.wio.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroveBean {
    private String name;
    private String type;
    private boolean passive = true;

    public void update(GroveBean other) {
        if (!name.equals(other.getName()) && !type.equals(other.getType())) {
            throw new IllegalArgumentException();
        }
        passive = passive && other.isPassive();
    }
}
