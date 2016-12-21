import os
import logging

root = os.path.join(os.path.dirname(__file__), '../', '../')


class CommonConfig:
    ROOT = root

    DB_NAME = 'collector'
    DB_HOST = 'database'
    DB_PORT = 3306
    DB_USERNAME = 'user'
    DB_PASSWORD = 'password'
    DB_PROTOCOL = 'mysql+pymysql'

    LOG_LEVEL = logging.INFO

    FLASK_DEBUG = True

    FLASK_APP_PORT = 8080
    FLASK_APP_HOST = '0.0.0.0'

    DEFAULT_MAX_QUERY_RESULT = 100

    def db_connection_string(self):
        return '{}://{}:{}@{}:{}/{}'.format(
            self.DB_PROTOCOL,
            self.DB_USERNAME,
            self.DB_PASSWORD,
            self.DB_HOST,
            self.DB_PORT,
            self.DB_NAME
        )


config = CommonConfig()
