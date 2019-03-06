var express = require('express');
var router = express.Router();
var User_info = require('./../models/User_info.js');
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;

mongoose.connect(mongo, {useNewUrlParser: true});


//임시 데이터 입력 >> 나중에 서버에서 불러오도록 수정해야 함
router.post('/insert', function(req,res){
    var history = req.body.history;
  
    User_info.findOne({'id': req.body.id}, function(err, user){
        var History = user.history;
        History.push(history);
        if (err) {
          console.err(err);
          throw err;
        }       
        user.update({'history':History}, function() {res.send(user.history)});
    })
  })


  //특정 id의 history데이터 시간역순으로 불러옴
router.post('/', function(req, res, next) {
    User_info.findOne({'id': req.body.id}, function(err, user){
        if (err) {
          res.send(err);
        } 
        else { 
            res.send(user.history.reverse())
        }
      })
});

module.exports = router;
