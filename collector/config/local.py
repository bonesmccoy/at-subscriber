from .common import *


class Config(CommonConfig):
    DB_NAME = 'collector'
    DB_HOST = '127.0.0.1'
    DB_PORT = 6603
    DB_USERNAME = 'root'
    DB_PASSWORD = 'rootpass'
    


config = Config()