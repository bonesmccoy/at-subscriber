from .common import *


class Config(CommonConfig):
    DB_NAME = 'collector'
    DB_HOST = 'database'
    DB_PORT = 3306
    DB_USERNAME = 'user'
    DB_PASSWORD = 'password'


config = Config()