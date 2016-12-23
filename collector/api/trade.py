import connexion
import datetime
from models import db, TradeStream
import logging

def collect():
    request_body = connexion.request.json

    logging.info(request_body)

    trade = TradeStream()

    trade.symbol = request_body['symbol']
    trade.year = request_body['year']
    trade.month = request_body['hour']
    trade.day = request_body['hour']
    trade.hour = request_body['hour']
    trade.minute = request_body['minute']
    trade.seconds = request_body['seconds']
    trade.milliseconds = request_body['milliseconds']
    trade.timestamp = datetime.datetime.strptime(request_body['timestamp'], '%Y-%m-%dT%H:%M:%SZ')

    trade.price = request_body['price']
    trade.price_precision = request_body['price_precision']
    trade.last_size = request_body['last_size']

    db.session.add(trade)

    try:
        db.session.commit()
    except Exception as e:
        return str(e), 400

    return "Success", 200


