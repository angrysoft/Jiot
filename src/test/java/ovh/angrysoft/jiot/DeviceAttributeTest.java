package ovh.angrysoft.jiot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ovh.angrysoft.jiot.exceptions.attributes.AttributeReadOnly;


public class DeviceAttributeTest {

    @Test
    @DisplayName("Test string attribute")
    void testStringAttribute() {
        DeviceAttribute<String> attr = new DeviceAttribute<>("test", false, "StringValue");
        assertEquals("test", attr.getName());
        assertEquals("StringValue", attr.getValue()); 
    }
    
    @Test
    @DisplayName("Test change value")
    void testChangeValue() {
        DeviceAttribute<String> attr = new DeviceAttribute<>("test", false, "StringValue");
        try {
            attr.setValue("NewValue");
            
        } catch (AttributeReadOnly er){
        }
        assertEquals("NewValue", attr.getValue());
    }
    
    
    @Test
    void testReadOnlyException() {
        DeviceAttribute<String> attr = new DeviceAttribute<>("test", true, "StringValue");
        Throwable exception = assertThrows(AttributeReadOnly.class, () -> attr.setValue("NewValue"));
        assertEquals("Read only parameter", exception.getMessage());
}
    
}
