Active Tick Subscriber
======================

This app gives you an example on how to connect to an ActiveTick stream update.
The code is based on the active tick java example.

Requirements to build this project:
add the jar inside `api_sdk/` as project modules
Maven

Run
The application need to be configured. Copy the configuration.yml.dist into
configuration.yml and provide the required data:

```
account:
  username: "your_at_username"
  password: "your_at_password"
  apiKey: "your_at_api_ley"

connection:
  hostname: "activetick1.activetick.com"
  port: 443

collector:
  url: "http://<collector_ip>:<collector_port>/1.0/trade"
```


Run the app
-----------
```bash
java --jar subscriber.jar configuration.yml
````

Available commands
------------------

To subscribe to TRADE UPDATES
```bash
subscribe <CSV of SYMBOLS>
```

To unsubscribe to TRADE UPDATES
```bash
unsubscribe <CSV of SYMBOLS>
```
To have help:
```
?
```

To quit the app
```bash
quit
```

Known issue building the jar with intellij

https://youtrack.jetbrains.com/issue/IDEA-99596