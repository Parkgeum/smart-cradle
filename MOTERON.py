import RPi.GPIO as GPIO
from time import sleep

OP=21

GPIO.setmode(GPIO.BCM)
GPIO.setup(OP, GPIO.OUT)
GPIO.setwarnings(False)

GPIO.output(OP, 1)
print('TEST MOTER ON')

#GPIO.cleanup()
