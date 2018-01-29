package com.mycompany.common;

import java.io.IOException;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;

public class MapValue implements Portable {

    private Long           data;

    public MapValue() {

    }

    public MapValue(Long data) {
        this.data = data;
    }
    

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MapValue [data=" + data + "]";
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        if (reader.readBoolean("_has__data")) {
            data = reader.readLong("data");
        }

    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {

        boolean hasData = (data != null);
        if (hasData) {
            writer.writeLong("data", data);
        }
        
        writer.writeBoolean("_has__data", hasData);

    }
}
