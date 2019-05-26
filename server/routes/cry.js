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


router.post('/',function(req,res,next){
    var {PythonShell} = require('python-shell');

var options = {
    mode:'text',
    pythonPath:'',
    pythonOption:['-u'],
    scriptPath:'/home/pi/Downloads/smart-cradle-master/server/',
    //args:['python']
    };



PythonShell.run('test.py',options,function(err,results){
    if(err) throw err;
    res.send();
    //console.log('hi %j',results);});
});

module.exports = router;
