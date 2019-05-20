var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;
//var io = require('socket.io').listen(1234);
var http = require('http').Server(express);
var fs = require('fs');
//var server = http.createServer();
var exec = require('child_process').exec;
var cmd='';

mongoose.connect(mongo, {useNewUrlParser: true});

//안드로이드에서 모터 제어
router.post('/',function(req,res, next){
  var msg=req.body.msg;
  if (msg=='MOTORON') {
    exec("python MOTERON.py", function(err, stdout, stderr){
      res.send({success: true, data: stdout})
    })
  } else if (msg=='MOTOROFF') { 
    exec("python MOTERFF.py", function(err, stdout, stderr){
      res.send({success: true, data: stdout})
    })
  }
    //res.send({success: true, data: 'Warn'});
    //console.log(msg);
});


//python MOTER.py 실행 명령어 전송>>>> 눈 떴을 때
router.get('/ctl',function(req,res,next){
  //res.send('python MOTERON.py');  //사용자 설정에 맞게 수정해야함
  
  var control = req.body.control;
  if (control=='motorON')
    res.send('python MOTERON.py');
  else if (control=='motorSTOP')
    res.send('MOTEROFF');
  else
    res.send(control);
});

module.exports = router;
