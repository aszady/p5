import json
from datetime import datetime, timedelta

import icmparser.coords
import icmparser.current

from tornado.ioloop import IOLoop
import tornado.web


def mgram_to_crodis_item(mgram, at):
    return {
        'lat': mgram.lat,
        'lon': mgram.lon,
        'radius': 5,
        'conditions': {
            'temperature': mgram.get_temperature(at),
            'temperature_delta': (mgram.get_temperature(at + timedelta(hours=3)) - mgram.get_temperature(at)) / 3,
            'wind': mgram.get_wind(at)
        }
    }

def mgrams_to_crodis(mgrams, at=None):
    if at is None:
        at = datetime.utcnow()
    mgrams = list(mgrams)
    return {
        'source': 'icm:'+mgrams[0].fdate,
        'items': list(map(lambda mgram: mgram_to_crodis_item(mgram, at), mgrams))
    }


class RestHandler(tornado.web.RequestHandler):
    def set_default_headers(self):
        self.set_header('Content-Type', 'application/json')


class PointHandler(RestHandler):
    def get(self):
        lat = float(self.get_query_argument('latitude'))
        lon = float(self.get_query_argument('longitude'))
        mgram = icmparser.current.CurrentMeteogram(lat, lon)
        self.write(json.dumps(mgrams_to_crodis([mgram])))


class AreaHandler(RestHandler):
    def get(self):
        lat = float(self.get_query_argument('latitude'))
        lon = float(self.get_query_argument('longitude'))
        radius = float(self.get_query_argument('radius'))
        grid = list(icmparser.coords.latlonradius2grid(lat, lon, radius))
        mgrams = map(lambda latlon: icmparser.current.CurrentMeteogram(*latlon), grid)
        self.write(json.dumps(mgrams_to_crodis(mgrams)))


class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/point", PointHandler),
            (r"/area", AreaHandler)
        ]
        tornado.web.Application.__init__(self, handlers)


def main():
    app = Application()
    app.listen(4412)
    IOLoop.instance().start()

if __name__ == '__main__':
    main()