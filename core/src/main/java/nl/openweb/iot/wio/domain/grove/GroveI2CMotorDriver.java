package nl.openweb.iot.wio.domain.grove;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.Grove;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.rest.NodeResource;

public class GroveI2CMotorDriver extends Grove {

    public GroveI2CMotorDriver(GroveBean groveBean, Node parent, NodeResource nodeResource) {
        super(groveBean, parent, nodeResource);
    }

    public boolean setI2CAddress(int address) throws WioException {
        return writeProperty("i2c_address", Integer.toString(address));
    }

    public boolean enableStepperMode(int direction, int speed) throws WioException{
        return writeProperty("enable_stepper_mode", Integer.toString(direction), Integer.toString(speed));
    }

    public boolean disableStepperMode() throws WioException{
        return writeProperty("disable_stepper_mode");
    }

    public boolean steps(int steps) throws WioException{
        return writeProperty("stepper_steps", Integer.toString(steps));
    }

    public boolean methodName(int arg) throws WioException{
        return writeProperty("", Integer.toString(arg));
    }

    public boolean setDcMotorSpeed(int m1Speed, int m2Speed) throws WioException {
        return writeProperty("dcmotor_speed", Integer.toString(m1Speed), Integer.toString(m2Speed));
    }

    public boolean dcMotor1ChangeDirection() throws WioException{
        return writeProperty("dcmotor1_change_direction");
    }

    public boolean dcMotor1Resume() throws WioException{
        return writeProperty("dcmotor1_resume");
    }

    public boolean dcMotor1Break() throws WioException{
        return writeProperty("dcmotor1_break");
    }

    public boolean dcMotor2ChangeDirection() throws WioException{
        return writeProperty("dcmotor2_change_direction");
    }

    public boolean dcMotor2Resume() throws WioException{
        return writeProperty("dcmotor2_resume");
    }

    public boolean dcMotor2Break() throws WioException{
        return writeProperty("dcmotor2_break");
    }

}
