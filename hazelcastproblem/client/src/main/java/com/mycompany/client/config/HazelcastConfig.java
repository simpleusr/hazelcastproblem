package com.mycompany.client.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfig {

    private static final Logger logger = LoggerFactory.getLogger(HazelcastConfig.class);
    
    @Bean
    public HazelcastInstance hazelcastInstance() throws Exception {
        File hazelcastConfigFileInClassPath = new ClassPathResource("hazelcast-client.xml").getFile();
        

        if (hazelcastConfigFileInClassPath == null) {
            String error = "No resource found for hazelcastConfigFile";
            logger.error(error);
            throw new Exception(error);
        }

        String hazelcastClientXmlFile = hazelcastConfigFileInClassPath.getPath();
        
        logger.info(String.format("Starting hazelcast instance for config file : %s", hazelcastClientXmlFile));
        ClientConfig cfg = new XmlClientConfigBuilder(hazelcastClientXmlFile).build();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(cfg);

        return client;
    }

}
