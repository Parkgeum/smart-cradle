var express = require('express');
var router = express.Router();
var multer = require('multer');
var uploads = multer({dest:'uploads/'})
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;
var http = require('http').Server(express);
var fs = require('fs');
var exec = require('child_process').exec;
var cmd='';

//uploads라는 파일 미리 만들어야한다
router.post('/',uploads.array('file'),function(req,res,next){

  //console.log('hi');
});
//저장 이름및 경로 설정
let storage = multer.diskStorage({
  destination: function(req, file ,callback){
    callback(null, "upload/")
  },
  filename: function(req, file, callback){
    callback(null, file.originalname + " - " + Date.now())
  }
})

let upload = multer({
  storage: storage
})


module.exports = router;
