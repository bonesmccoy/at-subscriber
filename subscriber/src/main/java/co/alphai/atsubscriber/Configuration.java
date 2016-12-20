package co.alphai.atsubscriber;



import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Configuration
{
    protected String username;
    protected String password;
    protected String apiKey;

    protected String hostName;
    protected Integer port;

    private Map configuration;

    public Configuration(String confContent)
    {
        Yaml ymlReader = new Yaml();
        configuration = (Map) ymlReader.load(confContent);
        populateAccount();
        populateConnection();
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
        hostName = (String) connectionNode.get("hostname");
        port = new Integer(connectionNode.get("port").toString());
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

    public String getHostName() {
        return hostName;
    }

    public Integer getPort() {
        return port;
    }

    public String toString()
    {
       return configuration.toString();
    }
}
