package com.mycompany.client.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.mycompany.common.MapValue;
import com.mycompany.common.MyEntryProcessor;

@Service
public class MapRepo implements InitializingBean {
    
    private long startIndex;

    @Autowired
    private HazelcastInstance hazelcastClient;

    @Value("${elementCount}")
    private int               elementCount;

    public void putEntriesUsingEntryProcessor() {

        IMap<String, MapValue> hzlMap = getMap();
        
        for (int i = 0; i < elementCount; i++) {
            String key = "key"+i;
            MapValue value = new MapValue(startIndex + i);
            hzlMap.executeOnKey(key, new MyEntryProcessor(value));
        }

    }

    private IMap<String, MapValue> getMap() {
        return hazelcastClient.getMap("hazelcastProblemMap");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startIndex = System.currentTimeMillis();
        
    }

}
