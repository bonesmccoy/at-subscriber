FROM python:3.5
MAINTAINER "Daniele Murroni" <daniele.murroni@gmail.com>

ENV UWSGI_PORT 5001

RUN pip install --upgrade pip==9.0.1

WORKDIR /trade_collector
ADD . /trade_collector
RUN rm config/local.py && cp config/docker.py config/local.py

RUN pip3 install -r requirements.txt

EXPOSE $UWSGI_PORT

CMD ["sh", "./start.sh"]