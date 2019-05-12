import os
import sys

import requests
import json

#위험상황 발생시 server에 메시지 전송
testurl = 'http://223.194.128.82:80/test/'  #서버 주소 수정
data = {'msg': 'Warnnnnnnnnn!!'}
headers = {'Content-type':'application/json', 'Accept':'text/plain'}
r = requests.post(testurl, data=json.dumps(data), headers=headers)

#서버에서 받은 명령어 실행
ctlurl = 'http://223.194.128.82:80/test/ctl/' #서버 주소 
data = requests.get(ctlurl)
os.system(data.text)
