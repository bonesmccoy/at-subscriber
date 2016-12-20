package co.alphai.atsubscriber;



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

    private Map configuration;

    public Configuration(String confContent)
    {
        Yaml ymlReader = new Yaml();
        configuration = (Map) ymlReader.load(confContent);
        populateAccount();
        populateConnection();
        populateCollector();
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
        collectorUrl = (String) collectorNode.get("url");
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

    public String toString()
    {
       return configuration.toString();
    }
}
