package ovh.angrysoft.jiot;

import java.util.HashMap;
import java.util.Map;

import ovh.angrysoft.jiot.exceptions.attributes.AttributeAlreadyExist;
import ovh.angrysoft.jiot.exceptions.attributes.AttributeNotFound;
import ovh.angrysoft.jiot.exceptions.attributes.AttributeReadOnly;


public class DeviceStatus {
    private HashMap<String, DeviceAttribute<?>> attributes;

    public DeviceStatus() {
        this.attributes = new HashMap<>();
    }

    public void registerAttribute(DeviceAttribute<?> attr) throws AttributeAlreadyExist {
        if ( this.attributes.containsKey(attr.getName()) ) {
            throw new AttributeAlreadyExist("Attribute" + attr.getName() + "already registered");
        }
        this.attributes.put(attr.getName(), attr);
    }

    public void unregisterAttribute(String attrName) {
        if ( this.attributes.containsKey(attrName) ) {
            this.attributes.remove(attrName);
        }
    }

    public void addAlias(String aliasName, String attrName) throws AttributeNotFound {
        if ( this.attributes.containsKey(attrName)) {
            this.attributes.put(aliasName, this.attributes.get(attrName));
        } else {
            throw new AttributeNotFound("Attribute" + attrName + "not found");
        }
    }

    public <T> T get(String attrName) {
        if (this.attributes.containsKey(attrName)) {
            return (T) this.attributes.get(attrName).getValue();
        }
        return null;
    }

    public Map<String, String> getAll() {
        HashMap<String, String> ret = new HashMap<>();
        for (Map.Entry<String, DeviceAttribute<?>> attr : attributes.entrySet()) {
            String key = attr.getKey();
            DeviceAttribute<?> value = attr.getValue();
            ret.put(key, value.getValue().toString());
        }
        return ret;
    }

    public <T> void set(String attrName, T value) throws AttributeReadOnly{
        if (this.attributes.containsKey(attrName)) {
            DeviceAttribute<T> attr = (DeviceAttribute<T>) this.attributes.get(attrName);
            attr.setValue(value);
        }
    }

    public void update(Map<String, ?> status) throws AttributeReadOnly {
        for (String attrName : status.keySet()) {
            this.set(attrName, status.get(attrName));
        }
    }
     
}

