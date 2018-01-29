package com.mycompany.common;

import java.io.IOException;
import java.util.Map.Entry;

import com.hazelcast.core.Offloadable;
import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

public class MyEntryProcessor extends AbstractEntryProcessor<String, MapValue> implements Offloadable, Portable {

    private static final long   serialVersionUID = 1L;

    
    private MapValue mapValue;


    public MyEntryProcessor() {

    }

    public MyEntryProcessor(MapValue mapValue) {
        this.mapValue = mapValue;
    }

    @Override
    public Object process(Entry<String, MapValue> entry) {

        MapValue valueToSet = null;
        if (null == entry.getValue()) {
            valueToSet = mapValue;
        } else {
            MapValue currentValue = entry.getValue();
            currentValue.setData(mapValue.getData());
        }

        entry.setValue(valueToSet);
        
        return null;
    }

    @Override
    public String getExecutorName() {
        //return NO_OFFLOADING;
        return OFFLOADABLE_EXECUTOR;
    }

    @Override
    public int getClassId() {
        return 2;
    }

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        if (reader.readBoolean("_has__mapValue")) {
            ObjectDataInput in = reader.getRawDataInput();
            mapValue = in.readObject();
        }
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        boolean hasMapValue = (mapValue != null);
        writer.writeBoolean("_has__mapValue", hasMapValue);
        if (hasMapValue) {
            ObjectDataOutput out = writer.getRawDataOutput();
            out.writeObject(mapValue);
        }

    }


}
