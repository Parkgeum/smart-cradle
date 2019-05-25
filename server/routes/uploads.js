var express = require('express');
var router = express.Router();
var multer = require('multer');
var upload = multer({dest:'uploads/'})
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;
var http = require('http').Server(express);
var fs = require('fs');
var exec = require('child_process').exec;
var cmd='';


router.post('/uploads',upload.array('file'),function(req,res,next){

  //console.log('hi');
});


module.exports = router;
