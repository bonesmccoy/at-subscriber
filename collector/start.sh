#!/usr/bin/env bash

echo "upgrading the database .."
python3 manage.py db upgrade

uwsgi --http :$UWSGI_PORT --wsgi-file app.py --callable connexion_application --stats :1717 --stats-http
