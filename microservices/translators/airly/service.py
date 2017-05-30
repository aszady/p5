import json

import math
from tornado.ioloop import IOLoop
import tornado.web

from airly_client import AirlyClient


def airly_to_crodis_item(airly_item):
    return {
        'lat': airly_item['location']['latitude'],
        'lon': airly_item['location']['longitude'],
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


def main():
    airly_client = AirlyClient('fd6c7a87271c402ea38bd5dea38291aa')  # How nasty is that.
    app = Application(airly_client)
    app.listen(4411)
    IOLoop.instance().start()

if __name__ == '__main__':
    main()