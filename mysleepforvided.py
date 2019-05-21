# import the necessary packages
from scipy.spatial import distance as dist
from imutils.video import FileVideoStream
from imutils.video import VideoStream
from imutils import face_utils
import numpy as np
import argparse
import imutils
import time
import dlib
import cv2
import datetime
import threading


sleep = 0
sleep_start=0
sleep_end=0
sleep_time=0
#지금 시간(now)
#now = datetime.datetime.now().strftime('%d_%H-%M-%S')
#post image
url = 'http://223.194.134.64:80/uploads/uploads'
headers = {'Authorization':'Bearer {}',}
# 눈 비율 계산 
def eye_aspect_ratio(eye):
	# 눈 수직길이 계산(A,B)
	# compute the euclidean distances between the two sets of
	# vertical eye landmarks (x, y)-coordinates
	A = dist.euclidean(eye[1], eye[5])
	B = dist.euclidean(eye[2], eye[4])
	
 	# 눈 수평거리 계산(C)
	# compute the euclidean distance between the horizontal
	# eye landmark (x, y)-coordinates
	C = dist.euclidean(eye[0], eye[3])
 	#눈 수직/수평 거리의 비율(ear)
	# compute the eye aspect ratio
	ear = (A + B) / (2.0 * C)
 
	# return the eye aspect ratio
	return ear

# 수면시간 측정
def caculate_sleep(start,end):

    start_time = start
    end_time = end
    sleep_time = end_time-start_time
    

    return sleep_time

    

#파씽해주는 부분
#코드내 사용자의 요구에 따라 적절히 파씽하여 오류를 줄인다

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-p", "--shape-predictor", required=True)
ap.add_argument("-v", "--picamera", type=int, default=-1,
	help="path to input video file")
args = vars(ap.parse_args())

# define two constants, one for the eye aspect ratio to indicate
# blink and then a second constant for the number of consecutive
# frames the eye must be below the threshold
EYE_AR_THRESH = 0.3
EYE_AR_CONSEC_FRAMES = 3
count=1
 


#dlib 와 facial...dat 이용해서 얼굴 인식
# initialize dlib's face detector (HOG-based) and then create
# the facial landmark predictor
print("[INFO] loading facial landmark predictor...")
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor(args["shape_predictor"])

#facial..dat의 눈 부분 변수에 저장
# grab the indexes of the facial landmarks for the left and
# right eye, respectively
(lStart, lEnd) = face_utils.FACIAL_LANDMARKS_IDXS["left_eye"]
(rStart, rEnd) = face_utils.FACIAL_LANDMARKS_IDXS["right_eye"]


#스트리밍 시작
# start the video stream thread
print("[INFO] starting video stream thread...")
vs = VideoStream(usePiCamera=args["picamera"] > 0).start()
fileStream = False
time.sleep(1.0)

#스트리밍 화면에서 얼굴 인식및 화면 표시 무한 루프
# loop over frames from the video stream
while True:
	# if this is a file video stream, then we need to check if
	# there any more frames left in the buffer to process
	#동영상에서만 필요한 부분같아서 나중에 확인 후 삭제
	if fileStream and not vs.more():
		break
 
	#스트리밍 화면 읽어 온 후 프레임 크기 조정
	# grab the frame from the threaded video file stream, resize
	# it, and convert it to grayscale
	# channels)
	frame = vs.read()
	frame = imutils.resize(frame, width=450)
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
 
	#인식된 얼굴의 데이터를 rects에 저장
	# detect faces in the grayscale frame
	rects = detector(gray, 0)

	
        #인식된 얼굴이 5초동안 없으면 화면 캡쳐
        if len(rects)==0:
                time.sleep(5)
                print('nono')
                cv2.imwrite('./Image/'+str(count)+'.png',frame)
		files = {'file':open('/home/pi/Image/'+str(count)+'.png','rb')}
                #upload img
		
		r = requests.post(url,files=files, headers=headers)
                count=count+1


                

	#인식 상황을 화면에 표시
	# loop over the face detections
	for rect in rects:
		# determine the facial landmarks for the face region, then
		# convert the facial landmark (x, y)-coordinates to a NumPy
		# array
		shape = predictor(gray, rect)
		shape = face_utils.shape_to_np(shape)
 
		# extract the left and right eye coordinates, then use the
		# coordinates to compute the eye aspect ratio for both eyes
		leftEye = shape[lStart:lEnd]
		rightEye = shape[rStart:rEnd]
		leftEAR = eye_aspect_ratio(leftEye)
		rightEAR = eye_aspect_ratio(rightEye)
 
		# average the eye aspect ratio together for both eyes
		ear = (leftEAR + rightEAR) / 2.0

		# compute the convex hull for the left and right eye, then
		# visualize each of the eyes
		leftEyeHull = cv2.convexHull(leftEye)
		rightEyeHull = cv2.convexHull(rightEye)
		cv2.drawContours(frame, [leftEyeHull], -1, (0, 255, 0), 1)
		cv2.drawContours(frame, [rightEyeHull], -1, (0, 255, 0), 1)

		# check to see if the eye aspect ratio is below the blink
		# threshold, and if so, increment the blink frame counter

		#수면 여부 확인
		#눈을 감고 있을때 수면상태 (sleep =1)로 상태 저장, 수면 시작 함수에 시간 저장
		if ear < EYE_AR_THRESH:  
                    sleep=1
                    if sleep_start==0:
                            sleep_start=time.time()
                # 수면 상태일때 (sleep=1)  눈을 뜨면 비수면상태로 저장(sleep=0) 
		if sleep==1:
                    if ear>EYE_AR_THRESH:
                        sleep=0
               #비수면 상태일때(sleep=0)      
                else:
			# sleep_start값이 있고 sleep_end가 값이 없으면 sleep_end에 시간 저장 후 수면시간 측정 함수를 불러옴
                    if sleep_start!=0 and sleep_end==0:
                        sleep_end=time.time()
                        sleep_time = caculate_sleep(sleep_start,sleep_end)

                        #수면시간이 5초 이상이라면 수면했다고 판단 수면시간을 측정하여 print함
			if sleep_time>5:
                                print("wake up %0.2f"%((sleep_time)))
                        #수면시간이 5초 이하라면 수면 시작,끝 변수를 0으로 초기화 한다.
			else:
                                sleep_start=0
                                sleep_end=0
                    #sleep_start함수가 없으면 잠을 자지 않았다고 판단 print한다   
                    else:
                        print("not sleep")
                        


               
                       
                 

                #눈과 귀의 위치 비율을 화면에 나타내는 부분
		#확인 후 삭제
		# draw the total number of blinks on the frame along with
                # the computed eye aspect ratio for the frame
                cv2.putText(frame, "Blinks: {}".format(EYE_AR_THRESH), (10, 30),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                cv2.putText(frame, "EAR: {:.2f}".format(ear), (300, 30),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2) 
	#프레임에 나타냄
	# show the frame
	cv2.imshow("Frame", frame)
	key = cv2.waitKey(1) & 0xFF

	
 
	# if the `q` key was pressed, break from the loop
	if key == ord("q"):
		break
 
# do a bit of cleanup
cv2.destroyAllWindows()
vs.stop()
