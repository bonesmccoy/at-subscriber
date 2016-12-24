package org.bones.alphai.subscriber;



import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Configuration
{
    protected String username;
    protected String password;
    protected String apiKey;

    protected String atHostName;
    protected Integer atPort;

    protected String collectorUrl;

    protected String logFilePath;
    protected String logLevel;

    private Map configuration;

    public Configuration(String confContent)
    {
        Yaml ymlReader = new Yaml();
        configuration = (Map) ymlReader.load(confContent);
        populateAccount();
        populateConnection();
        populateCollector();
        populateLogging();
    }

    private void populateAccount()
    {
        Map accountNode = (Map) this.configuration.get("account");
        username = (String) accountNode.get("username");
        password = (String) accountNode.get("password");
        apiKey = (String) accountNode.get("apiKey");
    }

    private void populateConnection()
    {
        Map connectionNode = (Map) this.configuration.get("connection");
        atHostName = (String) connectionNode.get("hostname");
        atPort = new Integer(connectionNode.get("port").toString());
    }

    private void populateCollector()
    {
        Map collectorNode = (Map) this.configuration.get("collector");
        if (collectorNode != null) {
            collectorUrl = (String) collectorNode.get("url");
        }
    }

    private void populateLogging()
    {
        Map loggingNode = (Map) this.configuration.get("logging");
        if (loggingNode != null) {
            logFilePath = (String) loggingNode.get("file");
            logLevel = (String) loggingNode.get("level");
        }
    }

    public boolean hasCollector()
    {
        return collectorUrl != null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAtHostName() {
        return atHostName;
    }

    public Integer getAtPort() {
        return atPort;
    }

    public String getCollectorUrl() {
        return collectorUrl;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public String toString()
    {
       return configuration.toString();
    }
}
