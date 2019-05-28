# 2019 종합설계프로젝트


Raspberry Pi
------------

+ ### 라즈베리파이 서버 실행(/server)
```
sudo node app.js
```
> #### /srever에 con.js 생성
>```
>module.exports = {
>    server:'http://서버IP:포트'.,
>    mongo:'mongodb://localhost:27017/Smart'
>}
>```
> #### /server에 MOTORON.py와 MOTOROFF.py sound.py가 위치해야한다.

+ ### 라즈베리파이 스트리밍 실행
```
mjpg 스트리밍 코드(라즈베리파이 /Desktop/copty.txt)에 있음
```


금정 노트북
-----------
+ ### openCV 실행 (anaconda prompt에서)
```
python GJforvideo.py --shape-predictor shape_predictor_68_face_landmarks.dat
```
> #### GJforvideo.py에서 수정
> * SERVER=라즈베리파이 서버 ip
> * cam = VideoCapture('http://라즈베리파이서버ip:8090/?action=stream')


이미지 파일 접근
-----------
### 이미지 저장할 폴더 미리 생성해야함(uploads)
http://url/uploads/이미지이름

### imageupload.js 깔아야하는 모듈

> npm install moment
> * npm install moment-timezone
> * npm install multer or npm install --save multer
