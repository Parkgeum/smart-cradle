var express = require('express');
var router = express.Router();
var formidable = require('formidable');
var fs= require('fs-extra');
var form = new formidable.IncomingForm();
var bodyParser = require('body-parser'); // json 형태로 파싱할꺼니까 모듈 추가
var mongoose = require('mongoose');

router.post('/',function(req,res){
    var name ="";
    var filePath = "";
    form.parse(req, function(err, fields, files) {//name = 안드로이드 에서 RequestBody 넘긴 값
    name = fields.name;
	});


   form.on('end', function(fields, files) {
  for (var i = 0; i < this.openedFiles.length; i++) {//음성 파일 크기에 맞게 받아온다. 
    var temp_path = this.openedFiles[i].path;
    var file_name = this.openedFiles[i].name;
    var index = file_name.indexOf('/'); 
    var new_file_name = file_name.substring(index + 1);
     
    var new_location = 'uploadsound/';//저장되는 위치
    //'uploads/';

    fs.copy(temp_path, new_location+file_name, function(err) { // 이미지 파일 저장하는 부분임
      if (err) {
        console.error(err);

        console.log("upload error!");
      }
      else{      
        res.setHeader('Content-Type', 'application/json');
        res.send(JSON.stringify({ result : "success", url : file_name }, null, 3));

        console.log("upload success!");
      }
    });
  }

});

});
module.exports = router;
