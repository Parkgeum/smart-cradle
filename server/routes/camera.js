
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;

mongoose.connect(mongo, {useNewUrlParser: true});

var img_flag =0;

var cameraOptions = {
    width : 600,
    height : 420,
    mode : 'timelapse',
    awb : 'off',
    encoding : 'jpg',
    output : 'images/camera.jpg',
    q : 50,
    timeout : 10000,
    timelapse : 0,
    nopreview : true,
    th : '0:0:0'
  };

  var camera = new require('raspicam')(cameraOptions);
  camera.start();

  camera.on('exit', function(){
      camera.stop();
      console.log("Restart camera");
      Uint8ClampedArray.start();
  });

  camera.on('read', function(){
      img_flag=1;
  });

  router.get('/', function(req, res){
      res.send('cam', {root:_dirname});
  })

  router.get('/img', function (req, res) {
    console.log('get /img') ;
      if (img_flag == 1) {
        img_flag = 0 ;
        res.sendfile('images/camera.jpg') ;
      }
  }) ;
  