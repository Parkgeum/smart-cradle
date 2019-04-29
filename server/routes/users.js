var express = require('express');
var router = express.Router();
var User_info = require('./../models/User_info.js');
var mongoose = require('mongoose');
//mongoose.connect('mongodb://localhost:27017/Smart', {useNewUrlParser: true});
var con = require('./../con');
var mongo=con.mongo;

mongoose.connect(mongo, {useNewUrlParser: true});

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

//회원가입
router.post('/join', function(req,res){
  var join = new User_info();
  join.id = req.body.id;
  join.password = req.body.password;
  join.baby = req.body.baby;
  join.gender = req.body.gender;
  join.Byear = req.body.Byear;
  join.Bmonth = req.body.Bmonth;
  join.Bday = req.body.Bday;

  User_info.findOne({'id': req.body.id}, function(err, user){
    if (err) {
      console.err(err);
      throw err;
    } 
    else if (user===null) User_info.create(join, function() {res.send({success: true, type: "join us"})});
    else res.send({success: false, type: "ID"});
  })
})

//로그인
router.post('/login', function(req, res) {
  var login_id = req.body.id;
  var login_password = req.body.password;
  
  var findLocalUser = {
    id: login_id,
    password: login_password
  }

  User_info.findOne(findLocalUser, function(err, user){
    if (err) {
      console.err(err);
      throw err;
    } 
    else if (user===null) res.send({success: false, data: "error"});
    else res.send({success: true, data: user});
  })
});


//회원정보 수정
router.post('/userinfo', function(req, res){
  var NewInfo;
  NewInfo = req.body;

  User_info.findOne({'id': req.body.id}, function(err, user){
    if (err) {
      res.send(err)
    } 
    else User_info.update(NewInfo, function() {res.send({success: true, type: "회원정보 수정"})});
  })
  
})

module.exports = router;
