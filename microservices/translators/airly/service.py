import argparse
import json

import math
from tornado.ioloop import IOLoop
import tornado.web
from eureka.client import EurekaClient

from airly_client import AirlyClient


def airly_to_crodis_item(airly_item):
    return {
        'latitude': airly_item['location']['latitude'],
        'longitude': airly_item['location']['longitude'],
        'radius': 1,
        'conditions': {
            'air_caql': airly_item['pollutionLevel']
        }
    }

def airly_to_crodis(items):
    return {
        'source': 'airly',
        'items': list(map(airly_to_crodis_item, filter(lambda item: item['pollutionLevel']>0, items)))
    }

def bounding_box(lat, lon, radius):
    # https://stackoverflow.com/questions/1253499/simple-calculations-for-working-with-lat-lon-km-distance
    dlat = radius / 111
    dlon = radius / 111 / math.cos(lat * math.pi / 180)
    return lat-dlat, lat+dlat, lon-dlon, lon+dlon


class RestHandler(tornado.web.RequestHandler):
    def set_default_headers(self):
        self.set_header('Content-Type', 'application/json')


class AreaHandler(RestHandler):
    def initialize(self, airly_client):
        self.airly_client = airly_client

    def get(self):
        lat = float(self.get_query_argument('latitude'))
        lon = float(self.get_query_argument('longitude'))
        radius = float(self.get_query_argument('radius'))

        airly_data = self.airly_client.get_area(*bounding_box(lat, lon, radius))
        self.write(json.dumps(airly_to_crodis(airly_data)))


class Application(tornado.web.Application):
    def __init__(self, airly_client):
        handlers = [
            (r"/area", AreaHandler, {'airly_client': airly_client})
        ]
        tornado.web.Application.__init__(self, handlers)

def register_eureka(args):
    ec = EurekaClient(
        "airly",
        eureka_url='http://{}:5042/eureka/'.format(args.eureka_host),
        host_name='airly',
        port=args.port,
        secure_port=443,
        home_page_url='http://{}:{}/'.format(args.host, args.port)
    )

    ec.register("UP")
    ec.heartbeat()


def parse_args():
    parser = argparse.ArgumentParser(description="Airly service")
    parser.add_argument('--host', required=True)
    parser.add_argument('--port', default=4411, type=int)
    parser.add_argument('--eureka_host', default='localhost')
    parser.add_argument('--airly_token', required=True)
    return parser.parse_args()

def main():
    args = parse_args()

    register_eureka(args)
    airly_client = AirlyClient(args.airly_token)
    app = Application(airly_client)
    app.listen(args.port, args.host)
    IOLoop.instance().start()

if __name__ == '__main__':
    main()
