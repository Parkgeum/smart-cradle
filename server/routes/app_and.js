var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;
var http = require('http').Server(express);
var fs = require('fs');
var exec = require('child_process').exec;
var cmd='';

mongoose.connect(mongo, {useNewUrlParser: true});

//라즈베리에서 위험하다는 메시지를 받은 경우!!
//tset.py
router.post('/',function(req,res){
  var msg=req.body.msg;
  if (msg=='Warnnnnnnnnn!!')
    //res.send({success: true, data: Warn});
    console.log(msg);
});


//python MOTER.py 실행 명령어 전송
router.get('/ctl',function(req,res){
  res.send('python MOTER.py');
});

module.exports = router;
