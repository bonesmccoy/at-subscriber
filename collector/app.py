import connexion
from config import config
from models import *

connexion_application = connexion.App(__name__, specification_dir='swagger/')

flask_app = connexion_application.app
flask_app.config.from_object(config)

flask_app.config['SQLALCHEMY_DATABASE_URI'] = config.db_connection_string()

init_db(flask_app)

connexion_application.add_api('api.yml')

if __name__ == '__main__':
    flask_app.run(config.FLASK_APP_HOST, config.FLASK_APP_PORT, config.FLASK_DEBUG)