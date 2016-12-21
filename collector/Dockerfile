FROM python:3.5
MAINTAINER "Daniele Murroni" <daniele.murroni@gmail.com>

ENV UWSGI_PORT 5001

RUN pip install --upgrade pip==9.0.1

ADD ./requirements.txt /trade_collector/requirements.txt
WORKDIR /trade_collector
RUN pip3 install -r requirements.txt
ADD . /trade_collector

RUN rm config/local.py && cp config/docker.py config/local.py

EXPOSE $UWSGI_PORT
EXPOSE 1717

CMD ["sh", "./start.sh"]
