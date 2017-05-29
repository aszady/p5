import json
from datetime import datetime

import icmparser.current

from tornado.ioloop import IOLoop
import tornado.web

def mgram_to_crodis(mgram, at=None):
    if at is None:
        at = datetime.utcnow()
    return {
        'source': 'icm',
        'items': [
            {
                'lat': 44,
                'lon': 44,
                'radius': 1e9+44,
                'conditions': {
                    'temperature': mgram.get_temperature(at)
                }
            }
        ]
    }

class GetareaHandler(tornado.web.RequestHandler):
    def set_default_headers(self):
        self.set_header('Content-Type', 'application/json')

    def get(self):
        mgram = icmparser.current.CurrentMeteogram(466, 232)
        self.write(json.dumps(mgram_to_crodis(mgram)))


class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/get_area", GetareaHandler)
        ]
        tornado.web.Application.__init__(self, handlers)


def main():
    app = Application()
    app.listen(4412)
    IOLoop.instance().start()

if __name__ == '__main__':
    main()