package ovh.angrysoft.jiot.traits;

public interface OnOff extends Trait {
    void on();
    void off();
    boolean isOn();
    boolean isOff();
    
}
