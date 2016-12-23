At Trade Collector
==================

This is a simple Flask application which exposes a REST API to collect
data from the Subscriber.

Requirements:
You need a MySql database instance.

Initialize
----------
Create your local.py configuration file from config/local.py.dist adding
your own database credential.

_Optional_:
Configure your flask app and port
```
FLASK_DEBUG = True

FLASK_APP_PORT = 8080
FLASK_APP_HOST = '0.0.0.0'
```

Local Setup
-----------
The trade collector requires Python3, MySQL

To create and activate the virtualenv:

```bash
$ python3 -m virtualenv venv
$ source venv/bin/activate
```

#### Install dependencies

```bash
$ pip3 install -r requirements.txt
```

#### Initialize the database

Update the database schema

```bash
 $ python3 manage.py db upgrade
```

Run as a Flask application
--------------------------

#### Run the Flask Version with HTTP support
```
$ python3 app.py
```

#### Browse the api doc
-------

```
http://localhost:8080/1.0/ui/
```

Swagger file
```
http://localhost:8080/1.0/swagger.json
```

#### Payload Example
--------------
```json
   {
      "symbol": "AAPL",
      "year": 2016,
      "month": 12,
      "day": 22,
      "hour": 12,
      "minute": 0,
      "seconds": 0,
      "milliseconds": 0,
      "timestamp": "2016-12-22T12:00:00Z",
      "price": 123.4567,
      "price_precision": 2,
      "last_size": 2000
    }
```

Run as a docker container
-------------------------
Create the docker.py configuration file from config/docker.py.dist adding
your own database credential.

Build docker composizion
```bash
 $ docker-compose build
```

Run docker orchestration
```bash
 $ docker-compose up -d
```

#### Browse the api doc
-------

```
http://localhost:18080/1.0/ui/
```

Swagger file
```
http://localhost:18080/1.0/swagger.json
```


