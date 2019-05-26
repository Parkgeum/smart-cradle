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
import os
import sys
import requests
import json


sleep = 0
sleep_start=0
sleep_end=0
sleep_time=0
ear=0

SERVER ='http://223.194.132.29:80'
CAMURL ='http://223.194.132.29:8090/?action=stream'
ControlURL = SERVER + '/ctl'
IMGURL = SERVER + '/uploadimage'
SleepURL = SERVER +'/boards/sleep'

command_headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
IMGheaders = {'Authorization':'Bearer {}',}

data = {'msg': SERVER}
local = requests.post(SERVER, data=json.dumps(data), headers=command_headers)

def eye_aspect_ratio(eye):
    
    # compute the euclidean distances between the two sets of
    # vertical eye landmarks (x, y)-coordinates
    A = dist.euclidean(eye[1], eye[5])
    B = dist.euclidean(eye[2], eye[4])
    
    
    # compute the euclidean distance between the horizontal
    # eye landmark (x, y)-coordinates
    C = dist.euclidean(eye[0], eye[3])
    
    # compute the eye aspect ratio
    ear = (A + B) / (2.0 * C)
 
    # return the eye aspect ratio
    return ear


def caculate_sleep(start,end):

    start_time = start
    end_time = end
    
    sleep_time = end_time-start_time
    sleep_time = round(sleep_time)

    return sleep_time

    


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
 



# initialize dlib's face detector (HOG-based) and then create
# the facial landmark predictor
print("[INFO] loading facial landmark predictor...")
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor(args["shape_predictor"])


# grab the indexes of the facial landmarks for the left and
# right eye, respectively
(lStart, lEnd) = face_utils.FACIAL_LANDMARKS_IDXS["left_eye"]
(rStart, rEnd) = face_utils.FACIAL_LANDMARKS_IDXS["right_eye"]



# start the video stream thread
print("[INFO] starting video stream thread...")
vs = VideoStream(usePiCamera=args["picamera"] > 0).start()
fileStream = False
time.sleep(1.0)
url_cry = SERVER+'/cry'
cry_data = {'msg': 'CRYON'}
requests.post(url_cry,data=json.dumps(cry_data), headers=command_headers)
time.sleep(1)

# loop over frames from the video stream
while True:
    # if this is a file video stream, then we need to check if
    # there any more frames left in the buffer to process
    
    if fileStream and not vs.more():
        break
 
    
    # grab the frame from the threaded video file stream, resize
    # it, and convert it to grayscale
    # channels)
    cam = cv2.VideoCapture(CAMURL)
    ret_val, frame = cam.read()  

    frame = imutils.resize(frame, width=450)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    
    # detect faces in the grayscale frame
    rects = detector(gray, 0)

    
    if len(rects) == 0:
        time.sleep(5)
        print('nono')
        cv2.imwrite('./Image/' + str(count) + '.png', frame)

        motor_on = {'msg': 'MOTOROFF'}
        requests.post(ControlURL, data=json.dumps(motor_on), headers=command_headers)

        files = {'file': open('./Image/' + str(count) + '.png', 'rb')}
        # upload img

        requests.post(IMGURL, files=files, headers=IMGheaders)
        time.sleep(2)
        count = count + 1
                

    
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

        
        if ear < EYE_AR_THRESH:
            sleep = 1

            if sleep_start == 0:
                sleep_start = time.time()
       
        if sleep == 1:
            if ear > EYE_AR_THRESH:
                sleep = 0
        # sleep=0
        else:
            
            if sleep_start != 0 and sleep_end == 0:
                sleep_end = time.time()
                sleep_time = caculate_sleep(sleep_start, sleep_end)

                
                if sleep_time > 5:
                    print("wake up %0.2f" % ((sleep_time)))
                    sleep_data = {'local': local, sleep: sleep_time}
                    requests.post(SleepURL, data=json.dumps(sleep_data), headers=command_headers)
                
                else:
                    sleep_start = 0
                    sleep_end = 0
            
            else:
                print("not sleep")
                motor_on = {'msg': 'MOTORON'}
                requests.post(ControlURL, data=json.dumps(motor_on), headers=command_headers)

                
                # draw the total number of blinks on the frame along with
                # the computed eye aspect ratio for the frame
                cv2.putText(frame, "Blinks: {}".format(EYE_AR_THRESH), (10, 30),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                cv2.putText(frame, "EAR: {:.2f}".format(ear), (300, 30),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
        
        # show the frame
    cv2.imshow("Frame", frame)
    key = cv2.waitKey(1) & 0xFF

    # if the `q` key was pressed, break from the loop
    if key == ord("q"):
        break
 
# do a bit of cleanup
cv2.destroyAllWindows()
vs.stop()

