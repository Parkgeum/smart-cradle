var express = require('express');
var router = express.Router();
//var formidable = require('formidable'); // form 태그 데이터들을 가져오는 모듈
//var fs = require('fs-extra'); // 파일을 복사하거나 디렉토리 복사하는 모듈
//var multer = require('multer');
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
            res.send({success:true, data:user.history.reverse()})
        }
      })
});

//사용자 기본 정보 로드 (history 제외) 
router.post('/getinfo', function(req, res, next) {
  var login_id = req.body.id;
  var findLocalUser = {
    id: login_id,
  }
  User_info.findOne(findLocalUser,{'history':0}, function(err, user){
    if (err) {
      res.send(err);
    } 
    else { 
      res.send({success:true, data:user});
    }
  })
});

router.post('/sleep',function(req,res){
  var history = req.body.sleep;
  var localuser = req.body.local;
  var date = req.body.date;
  var new_data = {
    date: date,
    sleep: history,
    img_path: null
  }
  User_info.findOne({'id': localuser}, function(err, user){
    
    var History = user.history;
    History.push(new_data);
    
    if (err) {
      console.err(err);
      throw err;
    }      
    user.update({'history':History}, function() {res.send(History)});
    
    })
});

module.exports = router;
