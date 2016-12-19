package co.alphai.atsubscriber;



import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Configuration
{
    protected String username;
    protected String password;
    protected String apiKey;

    private Map configuration;

    public Configuration(String confContent)
    {
        Yaml ymlReader = new Yaml();
        configuration = (Map) ymlReader.load(confContent);
        populateAccount();
    }

    private void populateAccount()
    {
        Map accountNode = (Map) this.configuration.get("account");
        username = (String) accountNode.get("username");
        password = (String) accountNode.get("password");
        apiKey = (String) accountNode.get("apiKey");
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

    public String toString()
    {
       return configuration.toString();
    }
}
