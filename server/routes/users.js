var express = require('express');
var router = express.Router();
var User_info = require('./../models/User_info.js');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/Smart', {useNewUrlParser: true});


/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

//회원가입
router.post('/signup', function(req,res){
  var signup = new User_info();
  signup.id = req.body.id;
  signup.password = req.body.password;
  signup.username = req.body.username;
  signup.email = req.body.email;

  User_info.findOne({'id': req.body.id}, function(err, user){
    if (err) {
      console.err(err);
      throw err;
    } 
    else if (user===null) User_info.create(signup, function() {res.send({success: true, type: "signup"})});
    else res.send('이미 존재하는 id입니다.');
  })
})



module.exports = router;
