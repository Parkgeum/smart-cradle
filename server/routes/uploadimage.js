var express = require('express');
var router = express.Router();
var multer = require('multer');
var User_info = require('./../models/User_info.js');

//var uploads = multer({dest:'uploads/'})

//var Imageupload = require('./../models/Imageupload.js');
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;
mongoose.connect(mongo, {useNewUrlParser: true});



var http = require('http').Server(express);
var fs = require('fs');
var exec = require('child_process').exec;
var cmd='';
//var moment = require('moment');
//require('moment-timezone');
//moment.tz.setDefault("Asia/Seoul");
//date = moment().format("MM.DD.HH.mm")

  let storage = multer.diskStorage({
  destination: function(req, file ,callback){
    callback(null, "uploads/")
  },
  filename: function(req, file, callback){
    callback(null,file.originalname)
  }
});

let uploads = multer({
  storage: storage
})




//uploads라는 파일 미리 만들어야한다
router.post('/',uploads.single('file'),function(req,res,next){
	//res.send('uploads:'+req.file.originalname);


 res.send();
  //console.log('hi');
});
//저장 이름및 경로 설정


router.post('/path',function(req,res){
  var localuser = req.body.local;
  var date = req.body.date;
  var path = req.body.path;
  var new_data = {
    date: date,
    img_path: path,
  }

  User_info.findOne({'id': localuser}, function(err, user){
    
    var History = user.imgpath;
    History.push(new_data);
    
    if (err) {
      console.err(err);
      throw err;
    }      
    user.update({'imgpath':History}, function() {res.send(new_data)});
    
  })
});


//module.exports = mongoose.model('Image',imageSchema);
module.exports = router;
