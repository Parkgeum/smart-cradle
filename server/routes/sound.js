var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('./../con1');
var mongo=con.mongo;
var http = require('http').Server(express);
var fs = require('fs');
var exec = require('child_process').exec;
var cmd='';

mongoose.connect(mongo, {useNewUrlParser: true});

//python MOTER.py 실행 명령어 전송
router.get('/',function(req,res){
	var filename = 'star.mp3';
	var file = fs.createReadStream(filename,{fags:'r'});
	file.pipe(res);
  });

module.exports = router;