package com.example.blob.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:openhr.properties")
public class HrConfig {

    @Value("${session.language}")
    private String language;

    @Value("${session.process_list}")
    private String processList;

    @Value("${openhr_server.server}")
    private String serverIp;

    @Value("${normal_message_sender.port}")
    private int normalMessagePort;

    @Value("${sensitive_message_sender.port}")
    private int sensitiveMessagePort;

    @Value("${privilegied_message_sender.port}")
    private int privilegedMessagePort;

    // Getters for the properties
    public String getLanguage() {
        return language;
    }

    public String getProcessList() {
        return processList;
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getNormalMessagePort() {
        return normalMessagePort;
    }

    public int getSensitiveMessagePort() {
        return sensitiveMessagePort;
    }

    public int getPrivilegedMessagePort() {
        return privilegedMessagePort;
    }
}