var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('../con');
var mongo=con.mongo;
var http = require('http').Server(express);
var fs = require('fs');
var exec = require('child_process').exec;
var cmd='';

mongoose.connect(mongo, {useNewUrlParser: true});

//안드로이드에서 모터 및 스피커 제어
router.post('/',function(req,res, next){
  var msg=req.body.msg;
  if (msg=='MOTORON') {
    exec("python MOTERON.py", function(err, stdout, stderr){
      res.send({success: true, data: stdout})
    })
  } else if (msg=='MOTOROFF') { 
    exec("python MOTEROFF.py", function(err, stdout, stderr){
      res.send({success: true, data: stdout})
    })
  } else if (msg=='SOUND') { 
    exec("python test.py", function(err, stdout, stderr){
      res.send({success: true, data: stdout})
  })
    }
});


//위험상황 알림
router.post('/warn',function(req,res, next){
  var msg=req.body.msg;
  if (msg=='warn') {
      res.send({success: true, data: 'Warn'})
  } 
});

module.exports = router;
