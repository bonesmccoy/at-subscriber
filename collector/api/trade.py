import connexion
import datetime
from models import db, TradeStream
import logging

def collect():
    request_body = connexion.request.json

    logging.info(request_body)

    trade = TradeStream()

    trade.symbol = request_body['symbol']
    trade.hour = request_body['hour']
    trade.minute = request_body['minute']
    trade.seconds = request_body['seconds']
    trade.milliseconds = request_body['milliseconds']

    trade.price = request_body['price']
    trade.last_size = request_body['lastSize']

    now = datetime.datetime.now()
    timestamp = datetime.datetime(
        now.year,
        now.month,
        now.day,
        hour=trade.hour,
        minute=trade.minute,
        second=trade.seconds,
        microsecond=(trade.milliseconds*1000)
    )
    trade.timestamp = timestamp

    db.session.add(trade)

    try:
        db.session.commit()
    except Exception as e:
        return str(e), 400

    return "Success", 200


