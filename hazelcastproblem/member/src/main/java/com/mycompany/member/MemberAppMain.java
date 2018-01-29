package com.mycompany.member;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class MemberAppMain {

    private static final Logger logger = LoggerFactory.getLogger(MemberAppMain.class);
    
    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(MemberAppMain.class, args);
        
        File hazelcastConfigFileInClassPath = new ClassPathResource(args[0]).getFile();
        

        if (hazelcastConfigFileInClassPath == null) {
            String error = "No resource found for hazelcastConfigFile";
            logger.error(error);
            throw new Exception(error);
        }

        String filePath = hazelcastConfigFileInClassPath.getPath();
        
        logger.info(String.format("Starting hazelcast instance for config file : %s", filePath));
        Config cfg = new XmlConfigBuilder(filePath).build();
        Hazelcast.newHazelcastInstance(cfg);
        logger.info("instance succefully started!!!");
        
        CountDownLatch closeLatch = context.getBean("closeLatch", CountDownLatch.class);
        closeLatch.await();

    }

}
