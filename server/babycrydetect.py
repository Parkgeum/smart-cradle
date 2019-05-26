from matplotlib.mlab import find
import pyaudio
import numpy as np

import pygame
import time

import math
import matplotlib.pyplot as plt
from detect_peaks import detect_peaks
from threading import Thread

#
import RPi.GPIO as GPIO
OP = 21
GPIO.setmode(GPIO.BCM)
GPIO.setup(OP,GPIO.OUT)

MinDetect = 50
chunk = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100
RECORD_SECONDS = 3

p = pyaudio.PyAudio()
window = 150
babyDidCry = False
endFrame = 0

ffs=[0]
amp=[0]

pygame.mixer.init()
pygame.mixer.music.load('record.mp3') 

def Pitch(signal):
	signal = np.fromstring(signal, 'Int16')
	maXabs = sum(abs(i) for i in signal)/float(len(signal))
	if maXabs >MinDetect:
		crossing = [math.copysign(1.0, s) for s in signal]
		index = find(np.diff(crossing));
		f0=round(len(index) *RATE /(2*np.prod(len(signal))))
		return f0, maXabs;
	else:
		return 0,0


def detecting():
	global babyDidCry

	stream = p.open(format = FORMAT,
	channels = CHANNELS,
	rate = RATE,
	input = True,
	output = True,
	frames_per_buffer = chunk)

	average = []
	for i in range(0, 5000):
		data = stream.read(chunk, exception_on_overflow = False)
		Frequency, amplitude = Pitch(data)

		ffs.append(Frequency)
		amp.append(amplitude)

		if (i+1)% 100 != 0:
			avgPeak = []

			if np.count_nonzero(ffs[i-99:]!=0):
                                peaks = detect_peaks(ffs[i-99:], mph=1500, mpd=3,show = False)
                                averageAmp = np.average(amp[i-99:])
				if len(peaks) >0:
					for j in peaks:
						avgPeak.append(ffs[i-99])
					avgPeakResult = np.average(avgPeak)
					stdPeak = np.std(avgPeak)
				else:
					avgPeakResult = 0
					stdPeak = 0
				print ( avgPeakResult, stdPeak,averageAmp, len(peaks),np.count_nonzero(ffs[i-99:])/float(len(ffs[i-99:])))
				if np.average(avgPeak) <= 2500 and np.std(avgPeak) <200 and averageAmp >20 and len(peaks)>=3 and len(peaks) <14:
				  #  if babyDidCry == False:
				  #      babyDidCry = True
					endFrame = i + 1500
					print ("baby crying")
					GPIO.output(OP,1)#motor start
					pygame.mixer.music.play()
					time.sleep(5)
				else:
					print ("noise")
					pygame.mixer.music.stop()
					GPIO.output(OP,0)#motor stop

			else:
				print ("noise")
				pygame.mixer.music.stop()
				GPIO.output(OP,0)
detecting()
