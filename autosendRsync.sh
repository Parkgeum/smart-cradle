#! /bin/sh
USER=pi	
SERVER_IP=223.194.132.147
PW=1111

SEND_DIR=/home/pi/send/
SAVE_DIR=/home/pi/receive

expect<<EOF
 set timeout 5
 spawn rsync -avzh $SEND_DIR $USER@$SERVER_IP:$SAVE_DIR
 expect "password:"
 send "$PW\r"
 expect eof
EOF
