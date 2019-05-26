var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('../con');
var mongo=con.mongo;
var http = require('http').Server(express);
var exec = require('child_process').exec;
var User_info = require('./../models/User_info.js');

mongoose.connect(mongo, {useNewUrlParser: true});

//사용자 정보
router.post('/',function(req,res, next){
  var msg=req.body.msg;

  User_info.findOne({server: msg}, function(err, user){
    if (err) {
      res.send(err);
    } 
    else { 
      res.send(user.id);
    }
  })
});



module.exports = router;
