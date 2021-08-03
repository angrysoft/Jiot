package ovh.angrysoft.jiot;

import ovh.angrysoft.jiot.exceptions.attributes.AttributeReadOnly;

public class DeviceAttribute<T> {
    private T value;
    private String name;
    private boolean readonly = false;

    
    public DeviceAttribute(String name, boolean readonly, T value) {
        this.name = name;
        this.readonly = readonly;
        this.value = value;
    }
    
    public DeviceAttribute(String name) {
        this.name = name;
    }
    
    public DeviceAttribute(String name, boolean readonly) {
       this.name = name;
       this.readonly = readonly;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setValue(T value) throws AttributeReadOnly {
        if (this.readonly && this.value != null) {
                throw new AttributeReadOnly("Read only parameter");
        }
        this.value = value;
    }
    
    public T getValue() {
        return this.value;
    }
}

