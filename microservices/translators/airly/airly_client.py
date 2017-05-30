import json
import urllib
import urllib.request


class AirlyClient(object):
    def __init__(self, token):
        self.token = token

    def _fetch_url(self, url):
        headers = {
            'Accept': 'application/json',
            'apikey': self.token
        }
        req = urllib.request.Request(url, headers=headers)
        print(url)
        with urllib.request.urlopen(req) as response:
            return json.loads(response.read().decode('utf-8'))

    def get_area(self, lat1, lat2, lon1, lon2):
        assert(lat1 < lat2)
        assert(lon1 < lon2)
        req = {
            'southwestLat': lat1,
            'northeastLat': lat2,
            'southwestLong': lon1,
            'northeastLong': lon2
        }
        url = 'https://airapi.airly.eu/v1/sensors/current'
        full_url = url + '?' + urllib.parse.urlencode(req)
        return self._fetch_url(full_url)