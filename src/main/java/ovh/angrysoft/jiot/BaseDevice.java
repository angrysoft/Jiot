package ovh.angrysoft.jiot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDevice {
    protected DeviceStatus status;

    protected BaseDevice() {
        this.status = new DeviceStatus();
    }
    
    public List<String> getTraits() {
        List<String> ret = new ArrayList<>();
        for (Class<?> cls: this.getClass().getInterfaces()) {
            String className = cls.getName();
            int lastDot = className.lastIndexOf(".");
            ret.add(className.substring(++lastDot));
        }
        return ret;
    }

    public <T> T query(String attrName) {
        return this.status.get(attrName);
    }

    public Map<String, String> getDeviceStatus() {
        HashMap<String, String> ret = new HashMap<>();
        ret.putAll(this.status.getAll());
        ret.put("traits", this.getTraits().toString());
        return ret;

    }
}
