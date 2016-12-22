from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()


def init_db(app):
    db.init_app(app)
    return app


class TradeStream(db.Model):
    __tablename__ = 'stream_trade'

    id = db.Column(db.Integer, primary_key=True)
    symbol = db.Column(db.String(20), nullable=False)
    year = db.Column(db.Integer, nullable=False)
    month = db.Column(db.Integer, nullable=False)
    day = db.Column(db.Integer, nullable=False)
    hour = db.Column(db.Integer, nullable=False)
    minute = db.Column(db.Integer, nullable=False)
    seconds = db.Column(db.Integer, nullable=False)
    milliseconds = db.Column(db.Integer, nullable=False)
    timestamp = db.Column(db.DateTime, nullable=False)
    price = db.Column(db.Float, nullable=False)
    price_precision = db.Column(db.Integer, nullable=False)
    last_size = db.Column(db.Integer, nullable=False)
