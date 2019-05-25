import RPi.GPIO as GPIO
from time import sleep

OP=21

GPIO.setmode(GPIO.BCM)
GPIO.setup(OP, GPIO.OUT)
GPIO.setwarnings(False)

GPIO.output(OP, 0)

GPIO.cleanup()
